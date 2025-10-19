package pratham.dhvani.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.service.UserService;

@RestController
@RequestMapping("/dhvani/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {
        ApiResponseDto response = userService.signup(userSignupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}