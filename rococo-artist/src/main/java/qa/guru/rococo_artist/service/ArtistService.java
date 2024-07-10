package qa.guru.rococo_artist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import qa.guru.rococo_artist.data.ArtistEntity;
import qa.guru.rococo_artist.data.ArtistRepository;
import qa.guru.rococo_artist.ex.ArtistNotFoundException;
import qa.guru.rococo_artist.model.ArtistJson;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public Page<ArtistJson> getAllArtists(Pageable pageable) {
        return artistRepository
                .findAll(pageable)
                .map(ArtistJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public ArtistJson getById(UUID id) {
        ArtistEntity artistEntity = artistRepository.findById(id).orElseThrow(() ->
                new ArtistNotFoundException("Can`t find artist by given id: " + id));
        return ArtistJson.fromEntity(artistEntity);
    }
//зачем?
    @Transactional(readOnly = true)
    public Page<ArtistJson> getByName(Pageable pageable, String name) {
        return artistRepository
                .findAllByNameContainsIgnoreCase(name, pageable)
                .map(ArtistJson::fromEntity);
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
