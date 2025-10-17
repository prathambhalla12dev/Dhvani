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
import pratham.dhvani.enums.Country;
import pratham.dhvani.enums.Gender;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    @Column(name="creation_time")
    private Long creationTime;

    // 3
    @Column(name="updation_time")
    private Long updationTime;

    // 4
    private String username;

    // 5
    @Column(name = "phone_number")
    private String phoneNumber;

    // 6
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    // 7
    private Country country;

    // 8
    private Gender gender;

    // 9
    private String password;
}
