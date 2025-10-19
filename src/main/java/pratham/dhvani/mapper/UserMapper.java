package pratham.dhvani.mapper;

import org.springframework.stereotype.Component;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.model.User;

import java.time.LocalDate;

/**
 * Mapper for converting between User DTOs and Entities
 * Separation of Concerns: Mapping logic separated from service logic
 */
@Component
public class UserMapper {

    /**
     * Maps UserSignupRequestDto to User entity
     */
    public User toEntity(UserSignupRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("UserSignupRequestDto cannot be null");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword());
        user.setDateOfBirth(parseDate(dto.getDateOfBirth()));
        user.setCountry(dto.getCountry());
        user.setGender(dto.getGender());
        user.setFullName(dto.getFullName());

        long now = System.currentTimeMillis();
        user.setCreationTime(now);
        user.setUpdationTime(now);

        return user;
    }

    /**
     * Parses date string to LocalDate
     */
    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
        }
    }
}