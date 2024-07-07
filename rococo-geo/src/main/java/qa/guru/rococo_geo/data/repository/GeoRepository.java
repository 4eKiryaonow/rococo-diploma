package qa.guru.rococo_geo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import qa.guru.rococo_geo.data.GeoEntity;

import java.util.Optional;
import java.util.UUID;

public interface GeoRepository extends JpaRepository<GeoEntity, UUID> {

    Optional<GeoEntity> getGeoByIdAndName(@Nonnull UUID countryId, @Nonnull String name);
}
