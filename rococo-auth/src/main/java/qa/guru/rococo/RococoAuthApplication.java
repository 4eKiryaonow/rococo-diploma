package qa.guru.rococo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import qa.guru.rococo.service.PropertiesLogger;

@SpringBootApplication
public class RococoAuthApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(RococoAuthApplication.class);
        springApplication.addListeners(new PropertiesLogger());
        springApplication.run(args);
    }

}
