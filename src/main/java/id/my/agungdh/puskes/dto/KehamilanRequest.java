package id.my.agungdh.puskes.dto;

import id.my.agungdh.puskes.entity.JenisKelamin;
import id.my.agungdh.puskes.entity.StatusKehamilan;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record KehamilanRequest(
        @NotNull UUID userId,
        LocalDate tglHaidTerakhir,
        LocalDate tglPerkiraanLahir,
        String namaBayi,
        JenisKelamin jenisKelamin,
        @NotNull StatusKehamilan status
) {}
