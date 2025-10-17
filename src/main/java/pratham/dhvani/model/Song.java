package pratham.dhvani.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pratham.dhvani.enums.Genre;
import pratham.dhvani.enums.Mood;
import pratham.dhvani.enums.Energy;
import pratham.dhvani.enums.Language;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songs")
public class Song {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    private String title;

    // 3
    private Genre genre;

    // 4
    private Mood mood;

    // 5
    private Energy energy;

    // 6
    private Language language;

    // 7
    private Double tempo;

    // 8
    @Column(name = "artist_id")
    private Long artistId;

    // 9
    @Column(name = "album_id")
    private Long albumId;

    // 10
    @Column(name = "creation_time")
    private Long creationTime;

    // 11
    @Column(name = "updation_time")
    private Long updationTime;

    // 12
    @Lob
    @Column(name = "mp3_file", columnDefinition = "LONGBLOB")
    private byte[] mp3File;
}
