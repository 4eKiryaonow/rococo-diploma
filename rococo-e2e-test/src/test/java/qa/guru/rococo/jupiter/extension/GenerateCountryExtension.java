package qa.guru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import qa.guru.rococo.jupiter.annotation.GenerateCountry;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.utils.RandomGenerator;

public class GenerateCountryExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE_COUNTRY
            = ExtensionContext.Namespace.create(GenerateCountryExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(), GenerateCountry.class
        ).ifPresent(country ->
                extensionContext
                        .getStore(NAMESPACE_COUNTRY)
                        .put(extensionContext.getUniqueId(), RandomGenerator.getRandomCountry())

        );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext
                .getParameter()
                .getType()
                .isAssignableFrom(CountryJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE_COUNTRY).get(extensionContext.getUniqueId());
    }
}
