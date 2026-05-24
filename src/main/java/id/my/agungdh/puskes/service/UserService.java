package id.my.agungdh.puskes.service;

import id.my.agungdh.puskes.dto.PageResponse;
import id.my.agungdh.puskes.dto.UserRequest;
import id.my.agungdh.puskes.dto.UserResponse;
import id.my.agungdh.puskes.entity.User;
import id.my.agungdh.puskes.mapper.UserMapper;
import id.my.agungdh.puskes.repository.UserRepository;
import id.my.agungdh.puskes.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public PageResponse<UserResponse> findAll(Pageable pageable) {
        Page<User> page = userRepository.findAllByDeletedAtIsNull(pageable);
        return new PageResponse<>(
                page.map(userMapper::toResponse).getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    public UserResponse findById(UUID uuid) {
        var user = userRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponse(user);
    }

    public UserResponse create(UserRequest request) {
        User user = userMapper.fromRequest(request);
        user.setUuid(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setCreatedAt(Instant.now().getEpochSecond());
        user.setCreatedBy(getCurrentUsername());
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse update(UUID uuid, UserRequest request) {
        var existing = userRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setUsername(request.username());
        if (request.password() != null && !request.password().isBlank()) {
            existing.setPassword(passwordEncoder.encode(request.password()));
        }
        existing.setEmail(request.email());
        existing.setName(request.name());
        existing.setRole(request.role());
        existing.setUpdatedAt(Instant.now().getEpochSecond());
        existing.setUpdatedBy(getCurrentUsername());

        return userMapper.toResponse(userRepository.save(existing));
    }

    public void delete(UUID uuid) {
        var user = userRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeletedAt(Instant.now().getEpochSecond());
        user.setDeletedBy(getCurrentUsername());
        userRepository.save(user);
    }

    private String getCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof TokenInfo tokenInfo) {
            return tokenInfo.username();
        }
        return "system";
    }
}
