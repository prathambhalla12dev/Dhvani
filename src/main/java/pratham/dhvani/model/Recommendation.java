package pratham.dhvani.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="recommendation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="creation_time", nullable=false)
    private long creationTime;

    @Column(name="updation_time", nullable=false)
    private long updationTime;

    @Column(name="user_id")
    private long userId;

    @Column(name="song_id")
    private long songId;
}