package id.my.agungdh.puskes.dto;

import id.my.agungdh.puskes.entity.Role;

import java.util.UUID;

public record UserResponse(
        UUID uuid,
        String username,
        String email,
        String name,
        Role role
) {}
