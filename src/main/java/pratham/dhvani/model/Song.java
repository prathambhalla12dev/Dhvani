package pratham.dhvani.model;

import jakarta.persistence.*;
import lombok.*;
import pratham.dhvani.enums.*;

@Entity
@Table(name="song")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="creation_time", nullable=false)
    private long creationTime;

    @Column(name="updation_time", nullable=false)
    private long updationTime;

    @Column(name="title", unique=true)
    private String title;

    @Column(name="genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name="energy")
    @Enumerated(EnumType.STRING)
    private Energy energy;

    @Column(name="mood")
    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Column(name="artist_id")
    private long artistId;

    @Column(name="album_id")
    private long albumId;

    @Column(name="language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name="tempo")
    private double tempo;

    @Lob
    @Column(name = "mp3_file", columnDefinition = "bytea")
    private byte[] mp3File;
}