package qa.guru.rococo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import qa.guru.rococo.data.repository.GeoRepository;
import qa.guru.rococo.ex.CountryNotFoundException;
import qa.guru.rococo.ex.GeoNotFoundException;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.model.GeoJson;
import qa.guru.rococo.data.CountryEntity;
import qa.guru.rococo.data.GeoEntity;
import qa.guru.rococo.data.repository.CountryRepository;

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

    public Page<CountryJson> getAllCountries(Pageable pageable) {
        return countryRepository.findAllByOrderByNameAsc(pageable).map(CountryJson::fromEntity);
    }

    public CountryJson getCountryById(String id) {
        CountryEntity country = countryRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new CountryNotFoundException("Can`t find country by given id: " + id)
                );
        return CountryJson.fromEntity(country);
    }

    public GeoJson getGeo(String nameCity, String nameCountry) {
        CountryEntity country = countryRepository
                .getCountryByName(nameCountry)
                .orElseThrow(() -> new CountryNotFoundException("Can`t find country by given name: " + nameCountry)
                );
        final UUID countryId = country.getId();

        GeoEntity geoEntity = geoRepository
                .getGeoByCountryIdAndName(countryId, nameCity)
                .orElseThrow(() -> new GeoNotFoundException("Can`t find country by given name : "
                        + nameCity + " and countryId: " + countryId));
        return GeoJson.fromEntity(geoEntity);
    }

    public GeoJson addGeo(GeoJson geoJson) {
        final String nameCountry = geoJson.country().name();
        CountryEntity country = countryRepository
                .getCountryByName(nameCountry)
                .orElseThrow(() -> new CountryNotFoundException("Can`t find country by given name: " + nameCountry)
                );
        final UUID countryId = country.getId();
        final String name = geoJson.name();
        Optional<GeoEntity> geoEntity = geoRepository.getGeoByCountryIdAndName(countryId, name);

        if (geoEntity.isPresent()) {
            return GeoJson.fromEntity(geoEntity.get());
        } else {
            GeoEntity entity = new GeoEntity();
            entity.setCountry(CountryEntity.fromJson(CountryJson.fromEntity(country)));
            entity.setName(geoJson.name());
            entity = geoRepository.save(entity);
            return GeoJson.fromEntity(entity);
        }
    }
}
