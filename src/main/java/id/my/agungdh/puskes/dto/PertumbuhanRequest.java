package id.my.agungdh.puskes.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PertumbuhanRequest(
        @NotNull Double beratBadan,
        @NotNull Double tinggiBadan,
        @NotNull LocalDate tglPengukuran,
        String keterangan
) {}
