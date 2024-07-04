package qa.guru.rococo_auth.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import qa.guru.rococo_auth.data.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Nonnull
    Optional<UserEntity> findByUsername(@Nonnull String username);
}
