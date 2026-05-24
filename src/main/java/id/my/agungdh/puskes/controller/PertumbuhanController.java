package id.my.agungdh.puskes.controller;

import id.my.agungdh.puskes.dto.PertumbuhanRequest;
import id.my.agungdh.puskes.dto.PertumbuhanResponse;
import id.my.agungdh.puskes.service.PertumbuhanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'PETUGAS', 'BUMIL')")
public class PertumbuhanController {

    private final PertumbuhanService pertumbuhanService;

    @GetMapping("/kehamilan/{kehamilanUuid}/pertumbuhan")
    public ResponseEntity<List<PertumbuhanResponse>> findByKehamilan(@PathVariable UUID kehamilanUuid) {
        return ResponseEntity.ok(pertumbuhanService.findByKehamilan(kehamilanUuid));
    }

    @PostMapping("/kehamilan/{kehamilanUuid}/pertumbuhan")
    public ResponseEntity<PertumbuhanResponse> create(@PathVariable UUID kehamilanUuid,
                                                       @Valid @RequestBody PertumbuhanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pertumbuhanService.create(kehamilanUuid, request));
    }

    @GetMapping("/pertumbuhan/{uuid}")
    public ResponseEntity<PertumbuhanResponse> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(pertumbuhanService.findById(uuid));
    }

    @PutMapping("/pertumbuhan/{uuid}")
    public ResponseEntity<PertumbuhanResponse> update(@PathVariable UUID uuid,
                                                       @Valid @RequestBody PertumbuhanRequest request) {
        return ResponseEntity.ok(pertumbuhanService.update(uuid, request));
    }

    @DeleteMapping("/pertumbuhan/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        pertumbuhanService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
