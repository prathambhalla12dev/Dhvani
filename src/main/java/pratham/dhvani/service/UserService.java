package pratham.dhvani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pratham.dhvani.dto.ApiResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.model.User;
import pratham.dhvani.repository.UserRepository;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String PHONE_NUMBER_REGEX = "^\\+91\\s?\\d{10}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    public ApiResponseDto signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {

        String username = userSignupRequestDto.getUsername();
        String password = userSignupRequestDto.getPassword();
        String confirmPassword = userSignupRequestDto.getConfirmPassword();
        String email = userSignupRequestDto.getEmail();
        String phoneNumber =  userSignupRequestDto.getPhoneNumber();
        String country = userSignupRequestDto.getCountry();

        ApiResponseDto apiResponseDto = new ApiResponseDto();

        if (!password.equals(confirmPassword)) {
            apiResponseDto.setMessage("Passwords do not match");
            apiResponseDto.setStatus("401");
            return apiResponseDto;
        }

        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            apiResponseDto.setMessage("Phone number format is invalid. Expected format: +91 XXXXXXXXXX");
            apiResponseDto.setStatus("400");
            return apiResponseDto;
        }

        if (userRepository.existsByUsername(username)) {
            apiResponseDto.setMessage("Username already exists");
            apiResponseDto.setStatus("409");
            return apiResponseDto;
        }

        if (userRepository.existsByEmail(email)) {
            apiResponseDto.setMessage("Email already exists");
            apiResponseDto.setStatus("409");
            return apiResponseDto;
        }

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            apiResponseDto.setMessage("Phone number already exists");
            apiResponseDto.setStatus("409");
            return apiResponseDto;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setCountry(country);
        userRepository.save(user);

        apiResponseDto.setMessage("User signed up successfully");
        apiResponseDto.setStatus("200");
        return apiResponseDto;
    }
}