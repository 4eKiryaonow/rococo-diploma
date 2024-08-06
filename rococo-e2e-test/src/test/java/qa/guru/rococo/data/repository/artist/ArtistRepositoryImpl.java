package qa.guru.rococo.data.repository.artist;

import jakarta.persistence.EntityManager;
import qa.guru.rococo.data.DataBase;
import qa.guru.rococo.data.entity.ArtistEntity;
import qa.guru.rococo.data.jpa.EmProvider;

public class ArtistRepositoryImpl implements ArtistRepository {
    private final EntityManager entityManager = EmProvider.entityManager(DataBase.ARTIST);


    @Override
    public ArtistEntity addArtist(ArtistEntity artistEntity) {
        entityManager.persist(artistEntity);
        return artistEntity;
    }
}
