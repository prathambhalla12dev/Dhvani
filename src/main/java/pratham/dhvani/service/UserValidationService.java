package pratham.dhvani.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.exception.UserAlreadyExistsException;
import pratham.dhvani.repository.UserRepository;

/**
 * Service responsible for user-related validations
 * Separation of Concerns: Validation logic separated from business logic
 */
@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;

    /**
     * Validates if username is unique
     * @throws UserAlreadyExistsException if username exists
     */
    public void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Username already exists");
        }
    }

    /**
     * Validates if phone number is unique
     * @throws UserAlreadyExistsException if phone number exists
     */
    public void validatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistsException("Phone number already registered");
        }
    }

    /**
     * Validates if passwords match
     * @throws IllegalArgumentException if passwords don't match
     */
    public void validatePasswordMatch(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) {
            throw new IllegalArgumentException("Password fields cannot be null");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    /**
     * Validates all signup requirements
     */
    public void validateSignupRequest(UserSignupRequestDto dto) {
        validateUsername(dto.getUsername());
        validatePhoneNumber(dto.getPhoneNumber());
        validatePasswordMatch(dto.getPassword(), dto.getConfirmPassword());
    }
}