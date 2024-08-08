package qa.guru.rococo.test.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.api.painting.PaintingApiClient;
import qa.guru.rococo.data.entity.PaintingEntity;
import qa.guru.rococo.jupiter.annotation.GeneratePainting;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.utils.RandomGenerator;

import java.io.IOException;
import java.util.List;

public class PaintingRestTest {

    private final PaintingApiClient paintingApiClient = new PaintingApiClient();

    @Test
    @DisplayName("REST Amount of paintings should be the same or more than added")
    @GeneratePainting(count = 3)
    void getAllPaintingsTest(List<PaintingJson> paintingJsonList) throws IOException {
        List<PaintingJson> list = paintingApiClient.getAllPaintings();
        Assertions.assertTrue(list.size() >= paintingJsonList.size());
    }

    @Test
    @DisplayName("REST Painting should be available by Id")
    @GeneratePainting
    void getByIdTest(List<PaintingJson> paintingJsonList) throws IOException {
        PaintingJson paintingExpected = paintingJsonList.getFirst();
        PaintingJson paintingFact = paintingApiClient.getPainting(String.valueOf(paintingExpected.id()));
        Assertions.assertEquals(paintingExpected, paintingFact);
    }

    @Test
    @DisplayName("REST Creation of Painting")
    void createPaintingTest() throws IOException {
        PaintingJson paintingExpected = RandomGenerator.generatePainting();
        PaintingJson paintingFact = paintingApiClient.addPainting(paintingExpected);
        Assertions.assertNotNull(paintingFact.id());
        Assertions.assertEquals(paintingExpected.title(), paintingFact.title());
        Assertions.assertEquals(paintingExpected.description(), paintingFact.description());
        Assertions.assertEquals(paintingExpected.content(), paintingFact.content());
    }

    @Test
    @DisplayName("REST Editing of Painting")
    @GeneratePainting
    void editPaintingTest(List<PaintingJson> paintingJsonList) throws IOException {
        PaintingEntity paintingEntityInit = PaintingEntity.fromJson(paintingJsonList.getFirst());
        final String titleEdited = paintingEntityInit.getTitle() + "edited";
        final String descEdited = paintingEntityInit.getDescription() + "edited";
        paintingEntityInit.setTitle(titleEdited);
        paintingEntityInit.setDescription(descEdited);
        PaintingJson editedPainting = paintingApiClient.editPainting(PaintingJson.fromEntity(paintingEntityInit));
        Assertions.assertEquals(titleEdited, editedPainting.title());
        Assertions.assertEquals(descEdited, editedPainting.description());
    }
}
