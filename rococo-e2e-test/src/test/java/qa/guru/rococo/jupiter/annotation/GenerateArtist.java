package qa.guru.rococo.jupiter.annotation;

import qa.guru.rococo.jupiter.extension.GenerateArtistExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@ExtendWith(GenerateArtistExtension.class)
public @interface GenerateArtist {

    int count() default 1;
    String name() default "";
    String biography() default "";
    String photo() default "";
}
