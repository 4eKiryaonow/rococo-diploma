package qa.guru.rococo.data.repository.geo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import qa.guru.rococo.data.DataBase;
import qa.guru.rococo.data.entity.CountryEntity;
import qa.guru.rococo.data.entity.GeoEntity;
import qa.guru.rococo.data.jpa.EmProvider;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GeoRepositoryImpl implements GeoRepository {

    private final EntityManager entityManager = EmProvider.entityManager(DataBase.GEO);

    @Override
    public List<CountryEntity> getAllCountries() {
        String hql = "SELECT c FROM CountryEntity c";
        return entityManager.createQuery(hql, CountryEntity.class)
                .getResultList();
    }

    @Override
    public Optional<GeoEntity> getGeoByCountryIdAndName(@Nonnull UUID country_id, @Nonnull String name) {
        try {
            String hql = "SELECT g FROM GeoEntity g WHERE g.country.id = :country_id AND g.name = :name";
            GeoEntity geoEntity = entityManager.createQuery(hql, GeoEntity.class)
                    .setParameter("country_id", country_id)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(geoEntity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public GeoEntity addGeo(GeoEntity geoEntity) {
        entityManager.persist(geoEntity);
        return geoEntity;
    }
}
