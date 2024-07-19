package qa.guru.rococo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import qa.guru.rococo.data.repository.MuseumRepository;
import qa.guru.rococo.model.MuseumJson;
import qa.guru.rococo.service.api.RestGeoClient;
import qa.guru.rococo.data.MuseumEntity;
import qa.guru.rococo.ex.MuseumNotFoundException;
import qa.guru.rococo.model.GeoJson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Component
public class MuseumService {

    private final MuseumRepository museumRepository;
    private final RestGeoClient restGeoClient;

    public MuseumService(MuseumRepository museumRepository, RestGeoClient restGeoClient) {
        this.museumRepository = museumRepository;
        this.restGeoClient = restGeoClient;
    }

    @Transactional
    public Page<MuseumJson> getAllMuseums(@Nullable String title, @Nonnull Pageable pageable) {
        Page<MuseumEntity> museums;
        if (Objects.isNull(title)) {
            museums = museumRepository.findAll(pageable);
        } else {
            museums = museumRepository.findAllByTitleContainsIgnoreCase(title, pageable);
        }
        return museums.map(museumEntity ->
                MuseumJson.fromEntity(
                        museumEntity,
                        getGeo(museumEntity.getCity(), museumEntity.getCountry()
                        )
                )
        );
    }

    @Transactional(readOnly = true)
    public MuseumJson getMuseumById(@Nonnull String id) {
        MuseumEntity museumEntity = museumRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new MuseumNotFoundException("Can`t find country by given id: " + id)
                );
        return MuseumJson.fromEntity(museumEntity, getGeo(museumEntity.getCity(), museumEntity.getCountry()));
    }

    @Transactional
    public MuseumJson addMuseum(MuseumJson museumJson) {
        MuseumEntity museumEntity = new MuseumEntity();
        museumEntity.setTitle(museumJson.title());
        museumEntity.setDescription(museumJson.description());
        museumEntity.setPhoto(museumJson.photo().getBytes(StandardCharsets.UTF_8));
        museumEntity.setCountry(museumJson.geo().country().name());
        museumEntity.setCity(museumJson.geo().name());
        museumEntity = museumRepository.save(museumEntity);
        GeoJson geoJson = addGeo(museumJson.geo());
        return MuseumJson.fromEntity(museumEntity, geoJson);
    }

    @Transactional
    public MuseumJson editMuseum(MuseumJson museumJson) {
        return museumRepository.findById(museumJson.id()).map(
                        museumEntity -> {
                            museumEntity.setTitle(museumJson.title());
                            museumEntity.setDescription(museumJson.description());
                            museumEntity.setPhoto(museumJson.photo().getBytes(StandardCharsets.UTF_8));
                            museumEntity.setCountry(museumJson.geo().country().name());
                            museumEntity.setCity(museumJson.geo().name());
                            GeoJson geoJson = addGeo(museumJson.geo());
                            return MuseumJson.fromEntity(museumEntity,geoJson);
                        }
                )
                .orElseThrow(() -> new MuseumNotFoundException("Can`t find country by given id: " + museumJson.id())
                );
    }

    private @Nonnull GeoJson getGeo(@Nonnull String nameCity, @Nonnull String nameCountry) {
        return restGeoClient.getGeo(nameCity, nameCountry);
    }

    private @Nonnull GeoJson addGeo(GeoJson geoJson) {
        return restGeoClient.addGeo(geoJson);
    }
}
