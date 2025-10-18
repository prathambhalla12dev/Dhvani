package pratham.dhvani.model;

import jakarta.persistence.*;
import lombok.*;
import pratham.dhvani.enums.Country;
import pratham.dhvani.enums.Gender;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="creation_time", nullable=false)
    private long creationTime;

    @Column(name="updation_time", nullable=false)
    private long updationTime;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="phone_number", unique = true)
    private String phoneNumber;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name="full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name="country")
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;
}
