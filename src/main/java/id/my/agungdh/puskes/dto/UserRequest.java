package id.my.agungdh.puskes.dto;

import id.my.agungdh.puskes.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank String username,
        String password,
        @NotBlank @Email String email,
        @NotBlank String name,
        @NotNull Role role
) {}
