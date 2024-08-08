package qa.guru.rococo.api.painting;

import qa.guru.rococo.api.ApiClient;
import qa.guru.rococo.model.PaintingJson;

import java.io.IOException;
import java.util.List;

public class PaintingApiClient extends ApiClient {

    private PaintingApi paintingApi;

    public PaintingApiClient() {
        super(CFG.paintingUrl());
        this.paintingApi = retrofit.create(PaintingApi.class);
    }

    public List<PaintingJson> getAllPaintings() throws IOException {
        return paintingApi.getAllPaintings(100, 0).execute().body().getContent();
    }

    public PaintingJson getPainting(String id) throws IOException {
        return paintingApi.getPainting(id).execute().body();
    }

    public PaintingJson addPainting(PaintingJson paintingJson) throws IOException {
        return paintingApi.addPainting(paintingJson).execute().body();
    }

    public PaintingJson editPainting(PaintingJson paintingJson) throws IOException {
        return paintingApi.editPainting(paintingJson).execute().body();
    }
}
