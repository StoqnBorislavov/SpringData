package springdata.gamestore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.gamestore.domain.dtos.UserDto;
import springdata.gamestore.domain.dtos.UserLogInDto;
import springdata.gamestore.domain.dtos.UserRegisterDto;
import springdata.gamestore.domain.entity.Role;
import springdata.gamestore.domain.entity.User;
import springdata.gamestore.repository.UserRepository;
import springdata.gamestore.utils.ValidatorUtil;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private UserDto loggedUser;
    private final GameService gameService;

    @Autowired
    public UserServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository, GameService gameService) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.gameService = gameService;
    }


    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        StringBuilder sb = new StringBuilder();
        User user = this.modelMapper.map(userRegisterDto, User.class);
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            sb.append("Password don't match");
        }
        if (validatorUtil.isValid(userRegisterDto)) {

            if (this.userRepository.count() == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }

            sb.append(String.format("%s was registered", userRegisterDto.getFullName()));

            this.userRepository.saveAndFlush(user);
        } else {
            this.validatorUtil.violations(userRegisterDto)
                    .forEach(e -> sb.append(String.format("%s%n", e.getMessage())));
        }

        return sb.toString().trim();
    }

    @Override
    public String logInUser(UserLogInDto logInDto) {
        StringBuilder sb = new StringBuilder();
        Optional<User> user = this.userRepository.findByEmailAndPassword(logInDto.getEmail(), logInDto.getPassword());

        if (user.isPresent()) {
            if (this.loggedUser != null) {
                sb.append("User is already logged in");
            } else {
                this.loggedUser = this.modelMapper.map(user.get(), UserDto.class);
                this.gameService.setLoggedUser(this.loggedUser);
                sb.append(String.format("Successfully logged in %s", user.get().getFullName()));
            }
        } else {
            sb.append("Incorrect email / password");
        }

        return sb.toString();
    }

    @Override
    public String logOut() {
        if (this.loggedUser == null) {
            return "Cannot log out. No user was logged in.";
        } else {
            String message = String.format("User %s successfully logged out", this.loggedUser.getFullName());
            this.loggedUser = null;
            return message;
        }
    }
}
