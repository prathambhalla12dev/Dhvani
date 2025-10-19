package pratham.dhvani.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="playlist", uniqueConstraints = @UniqueConstraint(columnNames = {"name","user_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="creation_time", nullable=false)
    private long creationTime;

    @Column(name="updation_time", nullable=false)
    private long updationTime;

    @Column(name="name")
    private String name;

    @Column(name="user_id")
    private long userId;
}