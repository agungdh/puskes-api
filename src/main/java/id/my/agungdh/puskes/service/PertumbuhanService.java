package id.my.agungdh.puskes.service;

import id.my.agungdh.puskes.dto.PertumbuhanRequest;
import id.my.agungdh.puskes.dto.PertumbuhanResponse;
import id.my.agungdh.puskes.entity.Pertumbuhan;
import id.my.agungdh.puskes.mapper.PertumbuhanMapper;
import id.my.agungdh.puskes.repository.KehamilanRepository;
import id.my.agungdh.puskes.repository.PertumbuhanRepository;
import id.my.agungdh.puskes.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PertumbuhanService {

    private final PertumbuhanRepository pertumbuhanRepository;
    private final KehamilanRepository kehamilanRepository;
    private final PertumbuhanMapper pertumbuhanMapper;

    public List<PertumbuhanResponse> findByKehamilan(UUID kehamilanUuid) {
        var kehamilan = kehamilanRepository.findByUuidAndDeletedAtIsNull(kehamilanUuid)
                .orElseThrow(() -> new RuntimeException("Kehamilan not found"));

        return pertumbuhanRepository
                .findAllByKehamilanIdAndDeletedAtIsNullOrderByTglPengukuranDesc(kehamilan.getId())
                .stream()
                .map(p -> {
                    var response = pertumbuhanMapper.toResponse(p);
                    return new PertumbuhanResponse(
                            response.uuid(),
                            response.kehamilanUuid(),
                            response.beratBadan(),
                            response.tinggiBadan(),
                            response.imt(),
                            getStatusImt(response.imt()),
                            response.tglPengukuran(),
                            response.keterangan(),
                            response.createdAt(),
                            response.updatedAt()
                    );
                })
                .toList();
    }

    public PertumbuhanResponse findById(UUID uuid) {
        var pertumbuhan = pertumbuhanRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("Pertumbuhan not found"));
        var response = pertumbuhanMapper.toResponse(pertumbuhan);
        return new PertumbuhanResponse(
                response.uuid(),
                response.kehamilanUuid(),
                response.beratBadan(),
                response.tinggiBadan(),
                response.imt(),
                getStatusImt(response.imt()),
                response.tglPengukuran(),
                response.keterangan(),
                response.createdAt(),
                response.updatedAt()
        );
    }

    public PertumbuhanResponse create(UUID kehamilanUuid, PertumbuhanRequest request) {
        var kehamilan = kehamilanRepository.findByUuidAndDeletedAtIsNull(kehamilanUuid)
                .orElseThrow(() -> new RuntimeException("Kehamilan not found"));

        double imt = hitungImt(request.beratBadan(), request.tinggiBadan());

        Pertumbuhan pertumbuhan = pertumbuhanMapper.fromRequest(request);
        pertumbuhan.setUuid(UUID.randomUUID());
        pertumbuhan.setKehamilan(kehamilan);
        pertumbuhan.setImt(imt);
        pertumbuhan.setCreatedAt(Instant.now().getEpochSecond());
        pertumbuhan.setCreatedBy(getCurrentUsername());
        pertumbuhan = pertumbuhanRepository.save(pertumbuhan);

        var response = pertumbuhanMapper.toResponse(pertumbuhan);
        return new PertumbuhanResponse(
                response.uuid(),
                response.kehamilanUuid(),
                response.beratBadan(),
                response.tinggiBadan(),
                response.imt(),
                getStatusImt(response.imt()),
                response.tglPengukuran(),
                response.keterangan(),
                response.createdAt(),
                response.updatedAt()
        );
    }

    public PertumbuhanResponse update(UUID uuid, PertumbuhanRequest request) {
        var existing = pertumbuhanRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("Pertumbuhan not found"));

        double imt = hitungImt(request.beratBadan(), request.tinggiBadan());

        existing.setBeratBadan(request.beratBadan());
        existing.setTinggiBadan(request.tinggiBadan());
        existing.setImt(imt);
        existing.setTglPengukuran(request.tglPengukuran());
        existing.setKeterangan(request.keterangan());
        existing.setUpdatedAt(Instant.now().getEpochSecond());
        existing.setUpdatedBy(getCurrentUsername());
        existing = pertumbuhanRepository.save(existing);

        var response = pertumbuhanMapper.toResponse(existing);
        return new PertumbuhanResponse(
                response.uuid(),
                response.kehamilanUuid(),
                response.beratBadan(),
                response.tinggiBadan(),
                response.imt(),
                getStatusImt(response.imt()),
                response.tglPengukuran(),
                response.keterangan(),
                response.createdAt(),
                response.updatedAt()
        );
    }

    public void delete(UUID uuid) {
        var pertumbuhan = pertumbuhanRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("Pertumbuhan not found"));

        pertumbuhan.setDeletedAt(Instant.now().getEpochSecond());
        pertumbuhan.setDeletedBy(getCurrentUsername());
        pertumbuhanRepository.save(pertumbuhan);
    }

    private double hitungImt(double beratBadan, double tinggiBadanCm) {
        double tinggiM = tinggiBadanCm / 100.0;
        return beratBadan / (tinggiM * tinggiM);
    }

    private String getStatusImt(double imt) {
        if (imt < 18.5) return "Kurus";
        if (imt < 25.0) return "Normal";
        if (imt < 30.0) return "Gemuk";
        return "Obesitas";
    }

    private String getCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof TokenInfo tokenInfo) {
            return tokenInfo.username();
        }
        return "system";
    }
}
