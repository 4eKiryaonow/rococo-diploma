package qa.guru.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import qa.guru.rococo.data.MuseumEntity;

import java.util.UUID;

public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID> {

    @Nonnull
    Page<MuseumEntity> findAllByTitleContainsIgnoreCase(
            @Nonnull String title,
            @Nonnull Pageable pageable
    );
}
