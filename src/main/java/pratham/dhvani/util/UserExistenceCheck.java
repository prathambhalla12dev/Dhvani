package pratham.dhvani.util;

import pratham.dhvani.dto.UserSignupRequestDto;

public class UserExistenceCheck {
    public String UserExistence(UserSignupRequestDto  userSignupRequestDto) {
        String username = userSignupRequestDto.getUsername();
        String phoneNumber = userSignupRequestDto.getPhoneNumber();

    }
}
