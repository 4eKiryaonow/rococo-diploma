package qa.guru.rococo.data.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import qa.guru.rococo.data.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum EmProvider {
    INSTANCE;

    private final Map<DataBase, EntityManagerFactory> store = new ConcurrentHashMap<>();

    private EntityManagerFactory computeEmf(DataBase db) {
        return store.computeIfAbsent(db, key -> {
            Map<String, String> props = new HashMap<>();

            props.put("hibernate.connection.url", db.getJdbcURL());
            props.put("hibernate.connection.username", "postgres");
            props.put("hibernate.connection.password", "secret");
            props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

            return Persistence.createEntityManagerFactory(
                    "rococo-diploma",
                    props
            );
        });
    }

    public static EntityManager entityManager(DataBase db) {
        return new TransactionalEntityManager(
                new ThreadSafeEntityManager(
                        EmProvider.INSTANCE.computeEmf(db).createEntityManager()
                )
        );
    }
}
