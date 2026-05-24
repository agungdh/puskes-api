package id.my.agungdh.puskes.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PertumbuhanResponse(
        UUID uuid,
        UUID kehamilanUuid,
        Double beratBadan,
        Double tinggiBadan,
        Double imt,
        String statusImt,
        LocalDate tglPengukuran,
        String keterangan,
        Long createdAt,
        Long updatedAt
) {}
