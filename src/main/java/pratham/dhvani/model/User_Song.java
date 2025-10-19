package pratham.dhvani.model;

import jakarta.persistence.*;
import lombok.*;
import pratham.dhvani.enums.UserPreference;

@Entity
@Table(name="user_song", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id, song_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_Song {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="creation_time", nullable=false)
    private long creationTime;

    @Column(name="updation_time", nullable=false)
    private long updationTime;

    @Column(name="user_id")
    private long user_id;

    @Column(name="song_id")
    private long song_id;

    @Enumerated(EnumType.STRING)
    @Column(name="user_preference")
    private UserPreference userPreference;
}