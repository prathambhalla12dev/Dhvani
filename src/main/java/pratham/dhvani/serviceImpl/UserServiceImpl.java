package pratham.dhvani.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.exception.UserAlreadyExistsException;
import pratham.dhvani.model.User;
import pratham.dhvani.repository.UserRepository;
import pratham.dhvani.service.UserService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ApiResponseDto signup(UserSignupRequestDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Phone number already registered");
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword());
        user.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        user.setCountry(dto.getCountry());
        user.setGender(dto.getGender());

        long now = System.currentTimeMillis();
        user.setCreationTime(now);
        user.setUpdationTime(now);

        userRepository.save(user);

        return new ApiResponseDto("User registered successfully", "SUCCESS");
    }
}