package pratham.dhvani.model;

import jakarta.persistence.*;
import lombok.*;
import pratham.dhvani.enums.Country;
import pratham.dhvani.enums.Gender;

import java.time.LocalDate;

@Entity
@Table(name="artist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
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

    @Column(name="country")
    private Country country;

    @Column(name="gender")
    private Gender gender;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
}