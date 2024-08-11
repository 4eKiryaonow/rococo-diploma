package qa.guru.rococo.utils;

import com.github.javafaker.Faker;
import qa.guru.rococo.data.entity.ArtistEntity;
import qa.guru.rococo.data.entity.CountryEntity;
import qa.guru.rococo.data.entity.GeoEntity;
import qa.guru.rococo.data.entity.MuseumEntity;
import qa.guru.rococo.data.repository.artist.ArtistRepositoryImpl;
import qa.guru.rococo.data.repository.geo.GeoRepositoryImpl;
import qa.guru.rococo.data.repository.museum.MuseumRepositoryImpl;
import qa.guru.rococo.model.*;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RandomGenerator {

    private static final Faker faker = new Faker();

    private static File IMAGE_ARTIST = new File("src/test/resources/testdata/artist.png");
    private static File IMAGE_MUSEUM = new File("src/test/resources/testdata/museum.jpeg");
    private static File IMAGE_PAINTING = new File("src/test/resources/testdata/painting.jpg");
    private static File IMAGE_PROFILE = new File("src/test/resources/testdata/profile.png");

    public static ArtistJson generateArtist() {
        return new ArtistJson(
                null,
                faker.name().nameWithMiddle(),
                faker.shakespeare().romeoAndJulietQuote(),
                ImageEncoder.encode(IMAGE_ARTIST)
        );
    }

    public static MuseumJson generateMuseum() {
        return new MuseumJson(
                null,
                faker.company().name(),
                faker.shakespeare().romeoAndJulietQuote(),
                ImageEncoder.encode(IMAGE_MUSEUM),
                generateGson()
        );
    }

    public static PaintingJson generatePainting() {
        ArtistEntity artistEntity = new ArtistRepositoryImpl().addArtist(ArtistEntity.fromJson(generateArtist()));
        MuseumEntity museumEntity = new MuseumRepositoryImpl().addMuseum(MuseumEntity.fromJson(generateMuseum()));
        return new PaintingJson(
                null,
                faker.pokemon().name(),
                ImageEncoder.encode(IMAGE_PAINTING),
                faker.shakespeare().asYouLikeItQuote(),
                ArtistJson.fromEntity(artistEntity),
                MuseumJson.fromEntity(museumEntity, generateGson())
        );
    }

    public static GeoJson generateGson() {
        CountryJson countryJson = getRandomCountry();

        final UUID countryId = countryJson.id();
        final String name = getRandomCity();
        Optional<GeoEntity> geoEntity = new GeoRepositoryImpl().getGeoByCountryIdAndName(countryId, name);
        if (geoEntity.isPresent()) {
            return GeoJson.fromEntity(geoEntity.get());
        } else {
            GeoEntity entity = new GeoEntity();
            entity.setCountry(CountryEntity.fromJson(countryJson));
            entity.setName(name);
            entity = new GeoRepositoryImpl().addGeo(entity);
            return GeoJson.fromEntity(entity);

        }
    }

    public static GeoJson generateUncreatedGeo() {
        GeoRepositoryImpl geoRepository = new GeoRepositoryImpl();
        Optional<GeoEntity> geoEntity;
        String nameCity;
        CountryJson countryJson;
        do {
            countryJson = getRandomCountry();
            UUID countryId = countryJson.id();
            nameCity = getRandomCity();
            geoEntity = geoRepository.getGeoByCountryIdAndName(countryId, nameCity);
        } while (geoEntity.isEmpty());

        return new GeoJson(null, nameCity, countryJson);
    }

    public static CountryJson getRandomCountry() {
        List<CountryJson> countryJsons = new GeoRepositoryImpl()
                .getAllCountries()
                .stream()
                .map(CountryJson::fromEntity)
                .toList();
        return countryJsons.get(getRandomIntFromList(countryJsons.size()));
    }

    public static File getProfileImage() {
        return IMAGE_PROFILE;
    }
    public static String generateRandomUsername() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static UserJson generateFakeUser() {
        return new UserJson(
                null,
                generateRandomUsername(),
                null,
                null,
                null,
                new TestData(generateRandomPassword() + "fake")
        );
    }

    public static String getRandomCity() {
        return faker.country().capital();
    }

    public static int getRandomIntFromList(int listSize) {
        return generateRandomInt(0, listSize - 1);
    }

    public static int generateRandomInt(int min, int max) {
        return faker.random().nextInt(min, max);
    }
}
