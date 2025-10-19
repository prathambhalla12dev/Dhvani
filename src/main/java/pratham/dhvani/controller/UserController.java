package pratham.dhvani.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.service.UserService;

@Slf4j
@RestController
@RequestMapping("/dhvani/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> signup(
            @Valid @RequestBody @NonNull UserSignupRequestDto userSignupRequestDto) {
        log.info("Received signup request for username: {}", userSignupRequestDto.getUsername());

        ApiResponseDto response = userService.signup(userSignupRequestDto);

        if (response == null) {
            log.error("Service returned null response - this should never happen");
            response = new ApiResponseDto("An unexpected error occurred", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}