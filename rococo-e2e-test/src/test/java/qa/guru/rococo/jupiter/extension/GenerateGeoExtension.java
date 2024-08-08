package qa.guru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import qa.guru.rococo.jupiter.annotation.GenerateGeo;
import qa.guru.rococo.model.GeoJson;
import qa.guru.rococo.utils.RandomGenerator;

public class GenerateGeoExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE_GEO
            = ExtensionContext.Namespace.create(GenerateGeoExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(), GenerateGeo.class
        ).ifPresent(geo ->
                extensionContext
                        .getStore(NAMESPACE_GEO)
                        .put(extensionContext.getUniqueId(), RandomGenerator.generateGson())

        );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext
                .getParameter()
                .getType()
                .isAssignableFrom(GeoJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE_GEO).get(extensionContext.getUniqueId());
    }
}
