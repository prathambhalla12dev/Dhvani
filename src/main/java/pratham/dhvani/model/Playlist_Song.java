package pratham.dhvani.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="playlist_song")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist_Song {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="creation_time", nullable=false)
    private long creationTime;

    @Column(name="updation_time", nullable=false)
    private long updationTime;
}
