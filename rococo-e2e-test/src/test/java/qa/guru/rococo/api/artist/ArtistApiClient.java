package qa.guru.rococo.api.artist;

import qa.guru.rococo.api.ApiClient;
import qa.guru.rococo.model.ArtistJson;

import java.io.IOException;
import java.util.List;

public class ArtistApiClient extends ApiClient {

    private ArtistApi artistApi;

    public ArtistApiClient() {
        super(CFG.artistUrl());
        this.artistApi = retrofit.create(ArtistApi.class);
    }

    public List<ArtistJson> getAllArtists() throws IOException {
        return artistApi.getAllArtists(100, 0).execute().body().getContent();
    }

    public ArtistJson getArtist(String id) throws IOException {
        return artistApi.getArtist(id).execute().body();
    }

    public ArtistJson addArtist(ArtistJson artist) throws IOException {
        return artistApi.addArtist(artist).execute().body();
    }

    public ArtistJson editArtist(ArtistJson artist) throws IOException {
        return artistApi.editArtist(artist).execute().body();
    }
}
