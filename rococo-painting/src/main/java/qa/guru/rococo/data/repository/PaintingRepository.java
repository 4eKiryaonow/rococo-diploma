package qa.guru.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import qa.guru.rococo.data.PaintingEntity;

import java.util.UUID;

public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID> {
    @Nonnull
    Page<PaintingEntity> findAllByTitleContainsIgnoreCase(
            @Nonnull String title,
            @Nonnull Pageable pageable
    );

    @Nonnull
    Page<PaintingEntity> findByArtistId(
            @Nonnull UUID id,
            @Nonnull Pageable pageable
    );
}
