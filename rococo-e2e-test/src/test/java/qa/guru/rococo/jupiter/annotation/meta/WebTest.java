package qa.guru.rococo.jupiter.annotation.meta;

import org.junit.jupiter.api.extension.ExtendWith;
import qa.guru.rococo.jupiter.extension.ApiLoginExtension;
import qa.guru.rococo.jupiter.extension.BrowserExtension;
import qa.guru.rococo.jupiter.extension.ContextHolderExtension;
import qa.guru.rococo.jupiter.extension.GenerateDBUserExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({ContextHolderExtension.class, GenerateDBUserExtension.class, ApiLoginExtension.class, BrowserExtension.class})
public @interface WebTest {
}
