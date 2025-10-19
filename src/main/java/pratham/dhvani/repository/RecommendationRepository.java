package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pratham.dhvani.model.Recommendation;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<@NonNull Recommendation, @NonNull Long> {
    List<Recommendation> findByUserId(long userId);
    void deleteByUserId(long userId);
}