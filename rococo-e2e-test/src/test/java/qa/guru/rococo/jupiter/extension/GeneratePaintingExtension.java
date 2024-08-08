package qa.guru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import qa.guru.rococo.data.entity.PaintingEntity;
import qa.guru.rococo.data.repository.painting.PaintingRepository;
import qa.guru.rococo.data.repository.painting.PaintingRepositoryImpl;
import qa.guru.rococo.jupiter.annotation.GeneratePainting;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class GeneratePaintingExtension implements BeforeEachCallback, ParameterResolver {

    private final PaintingRepository paintingRepository = new PaintingRepositoryImpl();


    public static final ExtensionContext.Namespace NAMESPACE_PAINTING
            = ExtensionContext.Namespace.create(GeneratePaintingExtension.class);


    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        List<PaintingJson> paintingJsons = new ArrayList<>();

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(), GeneratePainting.class
        ).ifPresent(generatePainting -> {
            int count = generatePainting.count();
            for (int i = 0; i < count; i++) {
                PaintingJson paintingJson = RandomGenerator.generatePainting();
                PaintingEntity paintingEntity = paintingRepository.addPainting(PaintingEntity.fromJson(paintingJson));
                paintingJson = PaintingJson.fromEntity(paintingEntity);
                paintingJsons.add(paintingJson);
            }
            extensionContext.getStore(NAMESPACE_PAINTING)
                    .put(extensionContext.getUniqueId(), paintingJsons);
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
        return extensionContext.getStore(NAMESPACE_PAINTING).get(extensionContext.getUniqueId());
    }
}
