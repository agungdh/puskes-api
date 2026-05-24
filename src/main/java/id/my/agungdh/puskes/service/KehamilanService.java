package id.my.agungdh.puskes.service;

import id.my.agungdh.puskes.dto.KehamilanRequest;
import id.my.agungdh.puskes.dto.KehamilanResponse;
import id.my.agungdh.puskes.dto.PageResponse;
import id.my.agungdh.puskes.entity.Kehamilan;
import id.my.agungdh.puskes.mapper.KehamilanMapper;
import id.my.agungdh.puskes.repository.KehamilanRepository;
import id.my.agungdh.puskes.repository.UserRepository;
import id.my.agungdh.puskes.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KehamilanService {

    private final KehamilanRepository kehamilanRepository;
    private final UserRepository userRepository;
    private final KehamilanMapper kehamilanMapper;

    public PageResponse<KehamilanResponse> findAll(Pageable pageable) {
        Page<Kehamilan> page = kehamilanRepository.findAllByDeletedAtIsNull(pageable);
        return new PageResponse<>(
                page.map(kehamilanMapper::toResponse).getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    public KehamilanResponse findById(UUID uuid) {
        var kehamilan = kehamilanRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("Kehamilan not found"));
        return kehamilanMapper.toResponse(kehamilan);
    }

    public KehamilanResponse create(KehamilanRequest request) {
        var user = userRepository.findByUuidAndDeletedAtIsNull(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Kehamilan kehamilan = kehamilanMapper.fromRequest(request);
        kehamilan.setUuid(UUID.randomUUID());
        kehamilan.setUser(user);
        kehamilan.setCreatedAt(Instant.now().getEpochSecond());
        kehamilan.setCreatedBy(getCurrentUsername());
        return kehamilanMapper.toResponse(kehamilanRepository.save(kehamilan));
    }

    public KehamilanResponse update(UUID uuid, KehamilanRequest request) {
        var existing = kehamilanRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("Kehamilan not found"));

        var user = userRepository.findByUuidAndDeletedAtIsNull(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setUser(user);
        existing.setTglHaidTerakhir(request.tglHaidTerakhir());
        existing.setTglPerkiraanLahir(request.tglPerkiraanLahir());
        existing.setNamaBayi(request.namaBayi());
        existing.setJenisKelamin(request.jenisKelamin());
        existing.setStatus(request.status());
        existing.setUpdatedAt(Instant.now().getEpochSecond());
        existing.setUpdatedBy(getCurrentUsername());

        return kehamilanMapper.toResponse(kehamilanRepository.save(existing));
    }

    public void delete(UUID uuid) {
        var kehamilan = kehamilanRepository.findByUuidAndDeletedAtIsNull(uuid)
                .orElseThrow(() -> new RuntimeException("Kehamilan not found"));

        kehamilan.setDeletedAt(Instant.now().getEpochSecond());
        kehamilan.setDeletedBy(getCurrentUsername());
        kehamilanRepository.save(kehamilan);
    }

    private String getCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof TokenInfo tokenInfo) {
            return tokenInfo.username();
        }
        return "system";
    }
}
