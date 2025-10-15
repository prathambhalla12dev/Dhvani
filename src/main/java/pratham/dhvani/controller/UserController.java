package pratham.dhvani.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.UserSignupRequestDto;
import pratham.dhvani.model.User;
import pratham.dhvani.service.UserService;

@RestController
@RequestMapping("api/dhvani/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ApiResponseDto signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {

    }
}
