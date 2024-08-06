package qa.guru.rococo.api.museum;

import qa.guru.rococo.api.ApiClient;
import qa.guru.rococo.model.MuseumJson;

import java.io.IOException;
import java.util.List;

public class MuseumApiClient extends ApiClient {

    private MuseumApi museumApi;

    public MuseumApiClient() {
        super(CFG.museumUrl());
        this.museumApi = retrofit.create(MuseumApi.class);
    }

    public List<MuseumJson> getAllMuseums() throws IOException {
        return museumApi.getAllMuseums(100, 0).execute().body().getContent();
    }

    public MuseumJson getMuseum(String id) throws IOException {
        return museumApi.getMuseum(id).execute().body();
    }

    public MuseumJson addMuseum(MuseumJson museum) throws IOException {
        return museumApi.addMuseum(museum).execute().body();
    }

    public MuseumJson editMuseum(MuseumJson museum) throws IOException {
        return museumApi.editMuseum(museum).execute().body();
    }
}
