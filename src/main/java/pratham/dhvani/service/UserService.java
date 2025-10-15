package pratham.dhvani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pratham.dhvani.dto.ApiResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.model.User;
import pratham.dhvani.repository.UserRepository;
import pratham.dhvani.validation.UserSignupValidator;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSignupValidator userSignupValidator;

    @Autowired
    private UserExistenceService userExistenceService;

    public ApiResponseDto signup(UserSignupRequestDto userSignupRequestDto) {

        userSignupValidator.validate(userSignupRequestDto);

        String username = userSignupRequestDto.getUsername();
        String password = userSignupRequestDto.getPassword();
        String email = userSignupRequestDto.getEmail();
        String phoneNumber =  userSignupRequestDto.getPhoneNumber();
        String country = userSignupRequestDto.getCountry();

        userExistenceService.checkExistence(userSignupRequestDto);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setCountry(country);
        userRepository.save(user);

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setMessage("User signed up successfully");
        apiResponseDto.setStatus("200");
        return apiResponseDto;
    }
}