package qa.guru.rococo.data.repository.user;

import jakarta.persistence.EntityManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import qa.guru.rococo.data.DataBase;
import qa.guru.rococo.data.entity.UserAuthEntity;
import qa.guru.rococo.data.entity.UserEntity;
import qa.guru.rococo.data.jpa.EmProvider;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {

    private final EntityManager authEm = EmProvider.entityManager(DataBase.AUTH);
    private final EntityManager udEm = EmProvider.entityManager(DataBase.USERDATA);
    private static final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity user) {
        user.setPassword(pe.encode(user.getPassword()));
        authEm.persist(user);
        return user;
    }

    @Override
    public UserEntity createUserInUserData(UserEntity user) {
        udEm.persist(user);
        return user;
    }

    @Override
    public Optional<UserEntity> findUserInUserdataById(UUID id) {
        return Optional.ofNullable(udEm.find(UserEntity.class, id));
    }
}
