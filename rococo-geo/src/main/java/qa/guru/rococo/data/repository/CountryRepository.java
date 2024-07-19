package qa.guru.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import qa.guru.rococo.data.CountryEntity;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {
    @Nonnull
    Optional<CountryEntity> getCountryByName(@Nonnull String countryName);
}
