package id.my.agungdh.puskes.repository;

import id.my.agungdh.puskes.entity.Kehamilan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface KehamilanRepository extends JpaRepository<Kehamilan, Long> {

    Optional<Kehamilan> findByUuidAndDeletedAtIsNull(UUID uuid);

    Page<Kehamilan> findAllByDeletedAtIsNull(Pageable pageable);
}
