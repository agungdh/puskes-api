package id.my.agungdh.puskes.repository;

import id.my.agungdh.puskes.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuidAndDeletedAtIsNull(UUID uuid);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    Page<User> findAllByDeletedAtIsNull(Pageable pageable);
}
