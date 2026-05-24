package id.my.agungdh.puskes.dto;

import id.my.agungdh.puskes.entity.JenisKelamin;
import id.my.agungdh.puskes.entity.StatusKehamilan;

import java.time.LocalDate;
import java.util.UUID;

public record KehamilanResponse(
        UUID uuid,
        UUID userId,
        String namaIbu,
        LocalDate tglHaidTerakhir,
        LocalDate tglPerkiraanLahir,
        String namaBayi,
        JenisKelamin jenisKelamin,
        StatusKehamilan status
) {}
