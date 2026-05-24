package id.my.agungdh.puskes.controller;

import id.my.agungdh.puskes.dto.KehamilanRequest;
import id.my.agungdh.puskes.dto.KehamilanResponse;
import id.my.agungdh.puskes.dto.PageResponse;
import id.my.agungdh.puskes.service.KehamilanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/kehamilan")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'PETUGAS', 'BUMIL')")
public class KehamilanController {

    private final KehamilanService kehamilanService;

    @GetMapping
    public ResponseEntity<PageResponse<KehamilanResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(kehamilanService.findAll(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<KehamilanResponse> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(kehamilanService.findById(uuid));
    }

    @PostMapping
    public ResponseEntity<KehamilanResponse> create(@Valid @RequestBody KehamilanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(kehamilanService.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<KehamilanResponse> update(@PathVariable UUID uuid, @Valid @RequestBody KehamilanRequest request) {
        return ResponseEntity.ok(kehamilanService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        kehamilanService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
