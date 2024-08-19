package qa.guru.rococo.data.repository.geo;

import qa.guru.rococo.data.entity.CountryEntity;
import qa.guru.rococo.data.entity.GeoEntity;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeoRepository {

    List<CountryEntity> getAllCountries();

    Optional<GeoEntity> getGeoByCountryIdAndName(@Nonnull UUID country_id, @Nonnull String name);

    GeoEntity addGeo(GeoEntity geoEntity);
}
