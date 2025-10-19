package pratham.dhvani.service;

import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.UserLoginRequestDto;
import pratham.dhvani.dto.UserLoginResponseDto;
import pratham.dhvani.dto.UserSignupRequestDto;

public interface UserService {
    ApiResponseDto signup(UserSignupRequestDto userSignupRequestDto);
    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);
}