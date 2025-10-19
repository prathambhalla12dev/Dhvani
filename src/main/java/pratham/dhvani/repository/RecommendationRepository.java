package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pratham.dhvani.model.Recommendation;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<@NonNull Recommendation, @NonNull Long> {
    List<Recommendation> findByUserId(long userId);

    @Modifying
    @Query("DELETE FROM Recommendation r WHERE r.userId = :userId")
    void deleteByUserId(@Param("userId") long userId);
}