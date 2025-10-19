package pratham.dhvani.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.model.User;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserSignupRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("UserSignupRequestDto cannot be null");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setDateOfBirth(parseDate(dto.getDateOfBirth()));
        user.setCountry(dto.getCountry());
        user.setGender(dto.getGender());
        user.setFullName(dto.getFullName());

        long now = System.currentTimeMillis();
        user.setCreationTime(now);
        user.setUpdationTime(now);

        return user;
    }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
        }
    }
}