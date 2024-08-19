package qa.guru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import qa.guru.rococo.data.entity.MuseumEntity;
import qa.guru.rococo.data.repository.museum.MuseumRepository;
import qa.guru.rococo.data.repository.museum.MuseumRepositoryImpl;
import qa.guru.rococo.jupiter.annotation.GenerateMuseum;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.model.GeoJson;
import qa.guru.rococo.model.MuseumJson;
import qa.guru.rococo.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class GenerateMuseumExtension implements BeforeEachCallback, ParameterResolver {

    private final MuseumRepository museumRepository = new MuseumRepositoryImpl();

    public static final ExtensionContext.Namespace NAMESPACE_MUSEUM
            = ExtensionContext.Namespace.create(GenerateMuseumExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        List<MuseumJson> museumJsons = new ArrayList<>();

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(), GenerateMuseum.class
        ).ifPresent(generateMuseum -> {
            int count = generateMuseum.count();
            for (int i = 0; i < count; i++) {
                MuseumJson museumJson = RandomGenerator.generateMuseum();
                MuseumEntity museumEntity = museumRepository.addMuseum(MuseumEntity.fromJson(museumJson));
                museumJson = MuseumJson.fromEntity(museumEntity, new GeoJson(null, museumJson.geo().name(), new CountryJson(null, museumEntity.getCountry())));
                museumJsons.add(museumJson);
            }

            extensionContext.getStore(NAMESPACE_MUSEUM)
                    .put(extensionContext.getUniqueId(), museumJsons);
        });


    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.
                getParameter().
                getType().
                isAssignableFrom(List.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE_MUSEUM).get(extensionContext.getUniqueId());
    }

}
