package qa.guru.rococo.data.repository.user;

import qa.guru.rococo.data.entity.UserAuthEntity;
import qa.guru.rococo.data.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    UserAuthEntity createUserInAuth(UserAuthEntity userAuth);


    UserEntity createUserInUserData(UserEntity user);


    Optional<UserEntity> findUserInUserdataById(UUID id);

}
