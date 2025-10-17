package pratham.dhvani.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "albums")
public class Album {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    @Column(name = "creation_time")
    private Long creationTime;

    // 3
    @Column(name = "updation_time")
    private Long updationTime;

    // 4
    private String title;

    // 5
    @Column(name = "release_date")
    private Long releaseDate;

    // 6
    @Column(name = "artist_id")
    private Long artistId;
}
