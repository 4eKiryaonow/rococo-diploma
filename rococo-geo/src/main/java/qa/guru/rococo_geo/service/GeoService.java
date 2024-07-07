package qa.guru.rococo_geo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import qa.guru.rococo_geo.data.CountryEntity;
import qa.guru.rococo_geo.data.GeoEntity;
import qa.guru.rococo_geo.data.repository.CountryRepository;
import qa.guru.rococo_geo.data.repository.GeoRepository;
import qa.guru.rococo_geo.ex.CountryNotFoundException;
import qa.guru.rococo_geo.ex.GeoNotFoundException;
import qa.guru.rococo_geo.model.CountryJson;
import qa.guru.rococo_geo.model.GeoJson;

import java.util.Optional;
import java.util.UUID;

@Component
public class GeoService {

    private final CountryRepository countryRepository;
    private final GeoRepository geoRepository;

    public GeoService(CountryRepository countryRepository, GeoRepository geoRepository) {
        this.countryRepository = countryRepository;
        this.geoRepository = geoRepository;
    }

    @Transactional(readOnly = true)
    public Page<CountryJson> getAllCountries(Pageable pageable) {
        return countryRepository.findAll(pageable).map(CountryJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public CountryJson getCountryById(String id) {
        CountryEntity country = countryRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new CountryNotFoundException("Can`t find country by given id: " + id)
                );
        return CountryJson.fromEntity(country);
    }

    @Transactional(readOnly = true)
    public CountryJson getCountryByName(String name) {
        CountryEntity country = countryRepository
                .getCountryByName(name)
                .orElseThrow(() -> new CountryNotFoundException("Can`t find country by given name: " + name)
                );
        return CountryJson.fromEntity(country);
    }

    @Transactional
    public GeoJson getGeo(GeoJson geoJson) {
        final UUID countryId = UUID.fromString(geoJson.country());
        CountryEntity country = countryRepository
                .findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException("Can`t find country by given id: " + countryId)
                );

        GeoEntity geoEntity = geoRepository
                .getGeoByIdAndName(countryId, geoJson.name())
                .orElseThrow(() -> new GeoNotFoundException("Can`t find country by given name : "
                        + geoJson.name() + " and country_id: " + countryId));
        return GeoJson.fromEntity(geoEntity);
    }

    @Transactional
    public GeoJson addGeo(GeoJson geoJson) {
        CountryJson countryJson = getCountryByName(geoJson.country());
        Optional<GeoEntity> geoEntity = geoRepository.getGeoByIdAndName(countryJson.id(), countryJson.name());

        if (geoEntity.isPresent()) {
            return GeoJson.fromEntity(geoEntity.get());
        } else {
            GeoEntity entity = new GeoEntity();
            entity.setCountry(CountryEntity.fromJson(countryJson));
            entity.setName(geoJson.name());
            entity = geoRepository.save(entity);
            return GeoJson.fromEntity(entity);
        }
    }
}
