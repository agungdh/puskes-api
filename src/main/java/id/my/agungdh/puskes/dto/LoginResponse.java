package id.my.agungdh.puskes.dto;

public record LoginResponse(
        String token,
        Long expiresAt,
        UserResponse user
) {}
