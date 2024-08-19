package qa.guru.rococo.jupiter.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import qa.guru.rococo.jupiter.extension.GeneratePaintingExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@ExtendWith(GeneratePaintingExtension.class)
public @interface GeneratePainting {
    int count() default 1;

    String title() default "";

    String description() default "";

    String photo() default "";
}
