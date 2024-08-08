package qa.guru.rococo.data.repository.painting;

import jakarta.persistence.EntityManager;
import qa.guru.rococo.data.DataBase;
import qa.guru.rococo.data.entity.PaintingEntity;
import qa.guru.rococo.data.jpa.EmProvider;

public class PaintingRepositoryImpl implements PaintingRepository {

    private final EntityManager entityManager = EmProvider.entityManager(DataBase.PAINTING);

    @Override
    public PaintingEntity addPainting(PaintingEntity paintingEntity) {
        entityManager.persist(paintingEntity);
        return paintingEntity;
    }
}
