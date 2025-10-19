package pratham.dhvani.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.exception.UserAlreadyExistsException;
import pratham.dhvani.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;

    public void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("username already exists");
        }
    }
    public void validatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistsException("phone number already registered");
        }
    }

    public void validatePasswordMatch(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) {
            throw new IllegalArgumentException("password fields cannot be null");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    public void validateSignupRequest(UserSignupRequestDto dto) {
        validateUsername(dto.getUsername());
        validatePhoneNumber(dto.getPhoneNumber());
        validatePasswordMatch(dto.getPassword(), dto.getConfirmPassword());
    }
}