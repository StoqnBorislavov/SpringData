package springdata.gamestore.service;

import springdata.gamestore.domain.dtos.UserLogInDto;
import springdata.gamestore.domain.dtos.UserRegisterDto;

public interface UserService {

    String registerUser(UserRegisterDto userRegisterDto);

    String logInUser(UserLogInDto logInDto);

    String logOut();
}
