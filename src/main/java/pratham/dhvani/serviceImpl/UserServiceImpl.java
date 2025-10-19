package pratham.dhvani.serviceImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.dto.UserLoginRequestDto;
import pratham.dhvani.mapper.UserMapper;
import pratham.dhvani.model.User;
import pratham.dhvani.repository.UserRepository;
import pratham.dhvani.service.UserService;
import pratham.dhvani.util.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidationService validationService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public @NonNull ApiResponseDto signup(@NonNull UserSignupRequestDto dto) {
        log.info("Processing signup request for username: {}", dto.getUsername());
        try {
            validationService.validateSignupRequest(dto);
            User user = userMapper.toEntity(dto);
            User savedUser = userRepository.save(user);

            log.info("User registered successfully with ID: {}", savedUser.getId());

            return new ApiResponseDto("User registered successfully", "SUCCESS");

        } catch (Exception e) {
            log.error("Unexpected error during signup for username: {}", dto.getUsername(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull ApiResponseDto login(@NonNull UserLoginRequestDto dto) {
        log.info("Processing login request for username: {}", dto.getUsername());

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> {
                    log.warn("Login failed: username not found - {}", dto.getUsername());
                    return new IllegalArgumentException("Invalid username or password");
                });

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.warn("Login failed: incorrect password for username - {}", dto.getUsername());
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtTokenProvider.generateToken(user.getUsername());
        long expiresIn = 86400000;

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", token);
        responseData.put("expiresIn", expiresIn);
        responseData.put("username", user.getUsername());
        responseData.put("userId", user.getId());

        log.info("User logged in successfully: {}", dto.getUsername());

        return new ApiResponseDto("User logged in successfully", "SUCCESS", responseData);
    }
}