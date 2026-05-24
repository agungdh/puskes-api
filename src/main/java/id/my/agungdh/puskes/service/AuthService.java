package id.my.agungdh.puskes.service;

import id.my.agungdh.puskes.dto.LoginRequest;
import id.my.agungdh.puskes.dto.LoginResponse;
import id.my.agungdh.puskes.dto.UserResponse;
import id.my.agungdh.puskes.mapper.UserMapper;
import id.my.agungdh.puskes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String TOKEN_PREFIX = "token:";
    private static final Duration TOKEN_TTL = Duration.ofHours(24);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;

    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findByUsernameAndDeletedAtIsNull(request.username())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = UUID.randomUUID().toString();
        String tokenValue = user.getId() + ":" + user.getRole().name() + ":" + user.getUsername();
        stringRedisTemplate.opsForValue().set(TOKEN_PREFIX + token, tokenValue, TOKEN_TTL);

        long expiresAt = System.currentTimeMillis() / 1000 + TOKEN_TTL.toSeconds();
        UserResponse userResponse = userMapper.toResponse(user);

        return new LoginResponse(token, expiresAt, userResponse);
    }

    public void logout(String token) {
        stringRedisTemplate.delete(TOKEN_PREFIX + token);
        SecurityContextHolder.clearContext();
    }
}
