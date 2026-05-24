package id.my.agungdh.puskes.security;

public record TokenInfo(
        Long userId,
        String role,
        String username
) {}
