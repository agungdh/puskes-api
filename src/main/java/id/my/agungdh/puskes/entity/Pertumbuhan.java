package id.my.agungdh.puskes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pertumbuhan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pertumbuhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kehamilan_id", nullable = false)
    private Kehamilan kehamilan;

    @Column(name = "berat_badan", nullable = false)
    private Double beratBadan;

    @Column(name = "tinggi_badan", nullable = false)
    private Double tinggiBadan;

    @Column(nullable = false)
    private Double imt;

    @Column(name = "tgl_pengukuran", nullable = false)
    private LocalDate tglPengukuran;

    private String keterangan;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "deleted_at")
    private Long deletedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_by")
    private String deletedBy;
}
