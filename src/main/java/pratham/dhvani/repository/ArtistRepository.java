package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pratham.dhvani.enums.Country;
import pratham.dhvani.model.Artist;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<@NonNull Artist, @NonNull Long> {
    List<Artist> findByCountry(Country country);
    List<Artist> findByNameContainingIgnoreCase(String name);
    Optional<Artist> findByName(String name);
    boolean existsByName(String name);
}