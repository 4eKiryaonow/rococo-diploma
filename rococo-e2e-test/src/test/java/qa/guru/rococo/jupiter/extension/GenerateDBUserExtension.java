package qa.guru.rococo.jupiter.extension;

import qa.guru.rococo.data.entity.Authority;
import qa.guru.rococo.data.entity.AuthorityEntity;
import qa.guru.rococo.data.entity.UserAuthEntity;
import qa.guru.rococo.data.entity.UserEntity;
import qa.guru.rococo.data.repository.user.UserRepository;
import qa.guru.rococo.data.repository.user.UserRepositoryImpl;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.model.TestData;
import qa.guru.rococo.model.UserJson;
import qa.guru.rococo.utils.RandomGenerator;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GenerateDBUserExtension extends GenerateUserExtension {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public UserJson createUser(TestUser user) {
        String username = user.username().isEmpty()
                ? RandomGenerator.generateRandomUsername()
                : user.username();
        String password = user.password().isEmpty()
                ? RandomGenerator.generateRandomPassword()
                : user.password();

        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setUsername(username);
        userAuth.setPassword(password);
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);
        AuthorityEntity[] authorities = Arrays.stream(Authority.values()).map(
                a -> {
                    AuthorityEntity ae = new AuthorityEntity();
                    ae.setAuthority(a);
                    return ae;
                }
        ).toArray(AuthorityEntity[]::new);

        userAuth.addAuthorities(authorities);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        userRepository.createUserInAuth(userAuth);
        userRepository.createUserInUserData(userEntity);
        byte[] photo = userEntity.getPhoto();
        return new UserJson(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getFirstname(),
                userEntity.getSurname(),
                photo != null && photo.length > 0 ? new String(userEntity.getPhoto(), StandardCharsets.UTF_8) : null,

                new TestData(
                        password
                )
        );
    }
}
