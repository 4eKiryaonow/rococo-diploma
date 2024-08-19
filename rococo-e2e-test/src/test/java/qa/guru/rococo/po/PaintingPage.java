package qa.guru.rococo.po;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.po.component.AddPaintingModal;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaintingPage extends BasePage<PaintingPage> {

    public static final String URL = CFG.frontUrl() + "painting";

    private final SelenideElement pageTitle = $$("h2").find(Condition.text("Картины"));

    private final SelenideElement addPaintingBtn = $$("button").find(Condition.text("Добавить картину"));

    private final AddPaintingModal addPaintingModal = new AddPaintingModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));

    private final SelenideElement paintingsGrid = $(".grid.grid-cols-1.gap-4");
    private final ElementsCollection paintingsCards = $$("a[href*='/painting/']");

    @Override
    public PaintingPage waitForPageLoaded() {
        progressRadialShouldNotBeVisible();
        pageTitle.should(visible);
        searchInput.should(visible);
        searchSubmitBtn.should(visible);
        return this;
    }

    public PaintingPage addPainting(PaintingJson paintingJson, File image) {
        addPaintingBtn.click();
        addPaintingModal.fillPaintingInfo(paintingJson, image);
        addPaintingModal.submit(new PaintingPage());
        return this;
    }

    public PaintingPage checkAddPaintingButton(boolean isAvailable) {
        if (isAvailable) {
            addPaintingBtn.should(visible);
        } else {
            addPaintingBtn.shouldNotBe(visible);
        }
        return this;
    }


    public PaintingPage checkPaintings(List<PaintingJson> paintingJsons) {
        paintingsCards.should(sizeGreaterThanOrEqual(paintingJsons.size()));
        for (var painting : paintingJsons) {
            String title = painting.title();
            searchInput.setValue(title);
            searchSubmitBtn.click();
            paintingsCards.find(text(title)).should(visible);
        }
        return this;
    }

    public PaintingPage checkPainting(PaintingJson paintingJson) {
        String title = paintingJson.title();
        searchInput.setValue(title);
        searchSubmitBtn.click();
        paintingsCards.find(text(title)).should(visible);
        return this;
    }
}
