package qa.guru.rococo_artist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import qa.guru.rococo_artist.data.ArtistEntity;
import qa.guru.rococo_artist.data.ArtistRepository;
import qa.guru.rococo_artist.ex.ArtistNotFoundException;
import qa.guru.rococo_artist.model.JsonArtist;

import java.util.UUID;

@Component
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public Page<JsonArtist> getAll(Pageable pageable) {
        return artistRepository
                .findAll(pageable)
                .map(JsonArtist::fromEntity);
    }

    @Transactional(readOnly = true)
    public JsonArtist getById(UUID id) {
        ArtistEntity artistEntity = artistRepository.findById(id).orElseThrow(() ->
                new ArtistNotFoundException("Can`t find country by given code: " + id));
        return JsonArtist.fromEntity(artistEntity);
    }
//зачем?
    @Transactional(readOnly = true)
    public Page<JsonArtist> getByName(Pageable pageable, String name) {
        return artistRepository
                .findAllByNameContainsIgnoreCase(name, pageable)
                .map(JsonArtist::fromEntity);
    }

    @Transactional
    public JsonArtist addArtist(JsonArtist artist) {
        ArtistEntity artistEntity = artistRepository.save(ArtistEntity.fromJson(artist));
        return JsonArtist.fromEntity(artistEntity);
    }

    @Transactional
    public JsonArtist editArtist(JsonArtist artist) {
        ArtistEntity artistEntity = artistRepository.findById(artist.id()).orElseThrow(() ->
                new ArtistNotFoundException("Can`t find country by given code: " + artist.id()));
        artistEntity.setName(artist.name());
        artistEntity.setBiography(artist.biography());
        artistEntity.setPhoto(artist.photo());
        return JsonArtist.fromEntity(artistRepository.save(artistEntity));
    }
}
