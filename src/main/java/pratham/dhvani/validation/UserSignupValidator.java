package pratham.dhvani.validation;

import org.springframework.stereotype.Component;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.exception.ValidationException;
import java.util.regex.Pattern;

@Component
public class UserSignupValidator {

    private static final String PHONE_NUMBER_REGEX = "^\\+91\\s?\\d{10}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    public void validate(UserSignupRequestDto dto) {

        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        String phoneNumber = dto.getPhoneNumber();

        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Passwords do not match");
        }

        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new ValidationException("Phone number format is invalid. Expected format: +91 XXXXXXXXXX");
        }
    }
}