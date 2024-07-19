package qa.guru.rococo.service;

import jakarta.annotation.Nonnull;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import qa.guru.rococo.ex.UserNotFoundException;
import qa.guru.rococo.data.UserEntity;
import qa.guru.rococo.data.repository.UserdataRepository;
import qa.guru.rococo.model.UserJson;

import java.nio.charset.StandardCharsets;

@Component
public class UserdataService {

    private final UserdataRepository userdataRepository;
    private static final Logger LOG = LoggerFactory.getLogger(UserdataService.class);

    @Autowired
    public UserdataService(UserdataRepository userdataRepository) {
        this.userdataRepository = userdataRepository;
    }

    @KafkaListener(topics = "users", groupId = "userdata")
    public void listener(@Payload UserJson user, ConsumerRecord<String, UserJson> cr) {
        LOG.info("### Kafka topic [users] received message: " + user.username());
        LOG.info("### Kafka consumer record: " + cr.toString());
        UserEntity userDataEntity = new UserEntity();
        userDataEntity.setUsername(user.username());
        UserEntity userEntity = userdataRepository.save(userDataEntity);
        LOG.info(String.format(
                "### User '%s' successfully saved to database with id: %s",
                user.username(),
                userEntity.getId()));
    }

    public @Nonnull
    UserJson update(@Nonnull UserJson user) {
        UserEntity userEntity = getByUsername(user.username());
        userEntity.setFirstname(user.firstname());
        userEntity.setSurname(user.surname());
        userEntity.setPhoto(user.avatar() != null ? user.avatar().getBytes(StandardCharsets.UTF_8) : null);
        return UserJson.fromEntity(userdataRepository.save(userEntity));
    }

    public @Nonnull
    UserJson getCurrentUser(@Nonnull String username) {
        return UserJson.fromEntity(getByUsername(username));
    }

    @Nonnull
    private UserEntity getByUsername(@Nonnull String username) {
        UserEntity user = userdataRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User with username " + username + " not found");
        }
    }
}
