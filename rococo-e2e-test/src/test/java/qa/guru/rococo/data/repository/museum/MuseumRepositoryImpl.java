package qa.guru.rococo.data.repository.museum;

import jakarta.persistence.EntityManager;
import qa.guru.rococo.data.DataBase;
import qa.guru.rococo.data.entity.MuseumEntity;
import qa.guru.rococo.data.jpa.EmProvider;

public class MuseumRepositoryImpl implements MuseumRepository {

    private final EntityManager entityManager = EmProvider.entityManager(DataBase.MUSEUM);

    @Override
    public MuseumEntity addMuseum(MuseumEntity museumEntity) {
        entityManager.persist(museumEntity);
        return museumEntity;
    }
}
