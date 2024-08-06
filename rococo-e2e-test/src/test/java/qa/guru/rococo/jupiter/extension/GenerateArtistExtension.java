package qa.guru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import qa.guru.rococo.data.entity.ArtistEntity;
import qa.guru.rococo.data.repository.artist.ArtistRepository;
import qa.guru.rococo.data.repository.artist.ArtistRepositoryImpl;
import qa.guru.rococo.jupiter.annotation.GenerateArtist;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class GenerateArtistExtension implements BeforeEachCallback, ParameterResolver {

    private final ArtistRepository ArtistRepository = new ArtistRepositoryImpl();

    public static final ExtensionContext.Namespace NAMESPACE_ARTIST
            = ExtensionContext.Namespace.create(GenerateArtistExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        List<ArtistJson> artistJsons = new ArrayList<>();

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(), GenerateArtist.class
        ).ifPresent(generateArtist -> {
            int count = generateArtist.count();
            for (int i = 0; i < count; i++) {
                ArtistJson artistJson = RandomGenerator.generateArtist();
                ArtistEntity artistEntity = ArtistRepository.addArtist(ArtistEntity.fromJson(artistJson));
                artistJson = ArtistJson.fromEntity(artistEntity);
                artistJsons.add(artistJson);
            }

            extensionContext.getStore(NAMESPACE_ARTIST)
                    .put(extensionContext.getUniqueId(), artistJsons);
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
        return extensionContext.getStore(NAMESPACE_ARTIST).get(extensionContext.getUniqueId());
    }
}
