package qa.guru.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import qa.guru.rococo.data.UserEntity;

import java.util.UUID;

public interface UserdataRepository extends JpaRepository<UserEntity, UUID> {

    @Nonnull
    UserEntity findByUsername(@Nonnull String username);
}
