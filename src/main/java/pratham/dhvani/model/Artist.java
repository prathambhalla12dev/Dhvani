package pratham.dhvani.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pratham.dhvani.enums.Country;
import pratham.dhvani.enums.Gender;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artists")
public class Artist {

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
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    // 5
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 6
    @Enumerated(EnumType.STRING)
    private Country country;

    // 7
    private String name;
}
