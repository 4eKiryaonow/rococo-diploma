package qa.guru.rococo_geo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import qa.guru.rococo_geo.data.GeoEntity;

import java.util.Optional;
import java.util.UUID;

public interface GeoRepository extends JpaRepository<GeoEntity, UUID> {

    Optional<GeoEntity> getGeoByCountryIdAndName(@Nonnull UUID country_id, @Nonnull String name);
}
