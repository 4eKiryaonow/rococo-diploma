package qa.guru.rococo.service;

import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import qa.guru.rococo.data.ArtistEntity;
import qa.guru.rococo.data.ArtistRepository;
import qa.guru.rococo.ex.ArtistNotFoundException;
import qa.guru.rococo.model.ArtistJson;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Component
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public Page<ArtistJson> getAllArtists(@Nullable String name, Pageable pageable) {
        Page<ArtistEntity> artists;
        if (Objects.isNull(name)) {
            artists = artistRepository.findAll(pageable);
        } else {
            artists = artistRepository.findAllByNameContainsIgnoreCase(name, pageable);
        }
        return artists.map(ArtistJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public ArtistJson getById(UUID id) {
        ArtistEntity artistEntity = artistRepository.findById(id).orElseThrow(() ->
                new ArtistNotFoundException("Can`t find artist by given id: " + id));
        return ArtistJson.fromEntity(artistEntity);
    }

    @Transactional
    public ArtistJson addArtist(ArtistJson artist) {
        ArtistEntity artistEntity = artistRepository.save(ArtistEntity.fromJson(artist));
        return ArtistJson.fromEntity(artistEntity);
    }

    @Transactional
    public ArtistJson editArtist(ArtistJson artist) {
        ArtistEntity artistEntity = artistRepository.findById(artist.id()).orElseThrow(() ->
                new ArtistNotFoundException("Can`t find country by given code: " + artist.id()));
        artistEntity.setName(artist.name());
        artistEntity.setBiography(artist.biography());
        artistEntity.setPhoto(artist.photo().getBytes(StandardCharsets.UTF_8));
        return ArtistJson.fromEntity(artistRepository.save(artistEntity));
    }
}
