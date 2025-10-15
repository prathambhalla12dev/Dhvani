package pratham.dhvani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.exception.ConflictException;
import pratham.dhvani.repository.UserRepository;

@Service
public class UserExistenceService {

    @Autowired
    private UserRepository userRepository;

    public void checkExistence(UserSignupRequestDto dto) {
        String username = dto.getUsername();
        String email = dto.getEmail();
        String phoneNumber = dto.getPhoneNumber();

        if (userRepository.existsByUsername(username)) {
            throw new ConflictException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ConflictException("Phone number already exists");
        }
    }
}