package qa.guru.rococo.test.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.api.museum.MuseumApiClient;
import qa.guru.rococo.data.entity.MuseumEntity;
import qa.guru.rococo.jupiter.annotation.GenerateMuseum;
import qa.guru.rococo.model.GeoJson;
import qa.guru.rococo.model.MuseumJson;
import qa.guru.rococo.utils.RandomGenerator;

import java.io.IOException;
import java.util.List;

public class MuseumRestTest {

    private final MuseumApiClient museumApiClient = new MuseumApiClient();

    @Test
    @DisplayName("REST Amount of museums should be the same or more than added")
    @GenerateMuseum(count = 3)
    void getAllMuseumsTest(List<MuseumJson> museumJsonList) throws IOException {
        List<MuseumJson> listFact = museumApiClient.getAllMuseums();
        Assertions.assertTrue(listFact.size() >= museumJsonList.size());

    }

    @Test
    @DisplayName("REST Museum should be available by Id")
    @GenerateMuseum
    void getMuseumByIdTest(List<MuseumJson> museumJsonList) throws IOException {
        MuseumJson museumExpected = museumJsonList.getFirst();
        MuseumJson museumFact = museumApiClient.getMuseum(String.valueOf(museumExpected.id()));

        Assertions.assertEquals(museumExpected.id(), museumFact.id());
        Assertions.assertEquals(museumExpected.title(), museumFact.title());
        Assertions.assertEquals(museumExpected.description(), museumFact.description());
        Assertions.assertEquals(museumExpected.photo(), museumFact.photo());
        Assertions.assertEquals(museumExpected.geo().name(), museumFact.geo().name());
        Assertions.assertEquals(museumExpected.geo().country().name(), museumFact.geo().country().name());
    }

    @Test
    @DisplayName("REST Creation of Museum")
    void createMuseumTest() throws IOException {
        MuseumJson museumExpected = RandomGenerator.generateMuseum();
        MuseumJson museumFact = museumApiClient.addMuseum(museumExpected);
        Assertions.assertNotNull(museumFact.id());
        Assertions.assertEquals(museumExpected.title(), museumFact.title());
        Assertions.assertEquals(museumExpected.description(), museumFact.description());
        Assertions.assertEquals(museumExpected.photo(), museumFact.photo());

        Assertions.assertNotNull(museumFact.geo().id());
        Assertions.assertEquals(museumExpected.geo().name(), museumFact.geo().name());
        Assertions.assertEquals(museumExpected.geo().country().name(), museumFact.geo().country().name());
    }

    @Test
    @DisplayName("REST Editing of Museum")
    @GenerateMuseum
    void editMuseumTest(List<MuseumJson> museumJsonList) throws IOException {
        MuseumEntity museumEntityInit = MuseumEntity.fromJson(museumJsonList.getFirst());
        final String titleEdited = museumEntityInit.getTitle() + "edited";
        final String descEdited = museumEntityInit.getDescription() + "edited";
        GeoJson newGeoJson = RandomGenerator.generateGeoJson();
        museumEntityInit.setTitle(titleEdited);
        museumEntityInit.setDescription(descEdited);
        MuseumJson editedMuseum = museumApiClient.editMuseum(MuseumJson.fromEntity(museumEntityInit, newGeoJson));
        Assertions.assertEquals(titleEdited, editedMuseum.title());
        Assertions.assertEquals(descEdited, editedMuseum.description());
        Assertions.assertEquals(newGeoJson.name(), editedMuseum.geo().name());
        Assertions.assertEquals(newGeoJson.country().name(), editedMuseum.geo().country().name());
    }
}
