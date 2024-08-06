package qa.guru.rococo.test.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.api.artist.ArtistApiClient;
import qa.guru.rococo.data.entity.ArtistEntity;
import qa.guru.rococo.jupiter.annotation.GenerateArtist;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.utils.RandomGenerator;

import java.io.IOException;
import java.util.List;

public class ArtistRestTest {

    private final ArtistApiClient artistApiClient = new ArtistApiClient();

    @Test
    @DisplayName("REST Amount of artists should be the same or more than added")
    @GenerateArtist(count = 3)
    void getAllArtistsTest(List<ArtistJson> artistJsonList) throws IOException {
        List<ArtistJson> list = artistApiClient.getAllArtists();
        Assertions.assertTrue(list.size() >= artistJsonList.size());
    }

    @Test
    @DisplayName("REST Artist should be available by Id")
    @GenerateArtist
    void getByIdTest(List<ArtistJson> artistJsonList) throws IOException {
        ArtistJson artistExpected = artistJsonList.getFirst();
        ArtistJson artistFact = artistApiClient.getArtist(String.valueOf(artistExpected.id()));
        Assertions.assertEquals(artistExpected, artistFact);
    }

    @Test
    @DisplayName("REST Creation of Artist")
    void createArtistTest() throws IOException {
        ArtistJson artistExpected = RandomGenerator.generateArtist();
        ArtistJson artistFact = artistApiClient.addArtist(artistExpected);
        Assertions.assertNotNull(artistFact.id());
        Assertions.assertEquals(artistExpected.name(), artistFact.name());
        Assertions.assertEquals(artistExpected.biography(), artistFact.biography());
        Assertions.assertEquals(artistExpected.photo(), artistFact.photo());
    }

    @Test
    @DisplayName("REST Editing of Artist")
    @GenerateArtist
    void editArtistTest(List<ArtistJson> artistList) throws IOException {
        ArtistEntity artistEntityInit = ArtistEntity.fromJson(artistList.getFirst());
        final String nameEdited = artistEntityInit.getName() + "edited";
        final String bioEdited = artistEntityInit.getBiography() + "edited";
        artistEntityInit.setName(nameEdited);
        artistEntityInit.setBiography(bioEdited);
        ArtistJson editedArtist = artistApiClient.editArtist(ArtistJson.fromEntity(artistEntityInit));
        Assertions.assertEquals(nameEdited, editedArtist.name());
        Assertions.assertEquals(bioEdited, editedArtist.biography());
    }
}
