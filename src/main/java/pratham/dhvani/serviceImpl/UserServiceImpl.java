package pratham.dhvani.serviceImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.mapper.UserMapper;
import pratham.dhvani.model.User;
import pratham.dhvani.repository.UserRepository;
import pratham.dhvani.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidationService validationService;
    private final UserMapper userMapper;

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
}