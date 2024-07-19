package qa.guru.rococo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import qa.guru.rococo.data.PaintingEntity;
import qa.guru.rococo.data.repository.PaintingRepository;
import qa.guru.rococo.ex.PaintingNotFoundException;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.service.api.RestArtistClient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Component
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final RestArtistClient restArtistClient;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository,
                           RestArtistClient restArtistClient) {
        this.paintingRepository = paintingRepository;
        this.restArtistClient = restArtistClient;
    }

    @Transactional(readOnly = true)
    public Page<PaintingJson> getAllPaintings(@Nullable String title, @Nonnull Pageable pageable) {
        Page<PaintingEntity> paintings;
        if (Objects.isNull(title)) {
            paintings = paintingRepository.findAll(pageable);
        } else {
            paintings = paintingRepository.findAllByTitleContainsIgnoreCase(title, pageable);
        }
        return paintings.map(PaintingJson::fromEntity);
    }

    @Transactional(readOnly = true)
    public PaintingJson getPaintingById(@Nonnull String id) {
        PaintingEntity paintingEntity = paintingRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new PaintingNotFoundException("Can`t find painting by given id: " + id)
                );
        return PaintingJson.fromEntity(paintingEntity);
    }

    @Transactional(readOnly = true)
    public Page<PaintingJson> getPaintingByAuthorId(@Nonnull String authorId, Pageable pageable) {
        ArtistJson artistJson = getArtist(authorId);
        Page<PaintingEntity> paintingEntities = paintingRepository.findByArtistId(artistJson.id(), pageable);
        return paintingEntities.map(PaintingJson::fromEntity);

    }

    @Transactional
    public PaintingJson addPainting(@Nonnull PaintingJson paintingJson) {
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setDescription(paintingJson.description());
        paintingEntity.setTitle(paintingJson.title());
        paintingEntity.setContent(paintingJson.content().getBytes(StandardCharsets.UTF_8));
        paintingEntity.setArtistId(paintingJson.artistId());
        paintingEntity.setMuseumId(paintingJson.museumId());
        return PaintingJson.fromEntity(paintingRepository.save(paintingEntity));
    }

    @Transactional
    public PaintingJson editPainting(@Nonnull PaintingJson paintingJson) {
        return paintingRepository.findById(paintingJson.id()).map(
                paintingEntity -> {
                    paintingEntity.setDescription(paintingJson.description());
                    paintingEntity.setTitle(paintingJson.title());
                    paintingEntity.setContent(paintingJson.content().getBytes(StandardCharsets.UTF_8));
                    paintingEntity.setArtistId(paintingJson.artistId());
                    paintingEntity.setMuseumId(paintingJson.museumId());
                    return PaintingJson.fromEntity(paintingEntity);
                }
        ).orElseThrow(() -> new PaintingNotFoundException("Can`t find painting by given id: " + paintingJson.id())
        );
    }

    private @Nonnull ArtistJson getArtist(@Nonnull String id) {
        return restArtistClient.getArtist(id);
    }
}
