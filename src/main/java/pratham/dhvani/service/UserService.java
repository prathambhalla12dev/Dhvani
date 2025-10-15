package pratham.dhvani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pratham.dhvani.dto.ApiResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponseDto signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {

        String username = userSignupRequestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(userSignupRequestDto.getPassword());
        String email = userSignupRequestDto.getEmail();
        String encodedConfirmPassword = passwordEncoder.encode(userSignupRequestDto.getConfirmPassword());
        String phoneNumber =  userSignupRequestDto.getPhoneNumber();
        String country = userSignupRequestDto.getCountry();

        ApiResponseDto apiResponseDto = new ApiResponseDto();

        assert encodedPassword != null;
        if(!encodedPassword.equals(encodedConfirmPassword)) {
            apiResponseDto.setMessage("Passwords do not match");
            apiResponseDto.setStatus("401");
            return apiResponseDto;
        }

       if(userRepository.existsByUsername(username)) {
           apiResponseDto.setMessage("Username already exists");
           apiResponseDto.setStatus("409");
           return apiResponseDto;
       }

       if(userRepository.existsByEmail(email)) {
           apiResponseDto.setMessage("Email already exists");
           apiResponseDto.setStatus("409");
           return apiResponseDto;
       }

       if(userRepository.existsByPhoneNumber(phoneNumber)) {
           apiResponseDto.setMessage("Phone number already exists");
           apiResponseDto.setStatus("409");
           return apiResponseDto;
       }

        return apiResponseDto;
    }
}
