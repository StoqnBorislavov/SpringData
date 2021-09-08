package springdatajsonprocesing.services;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdatajsonprocesing.domain.dtos.UserSeedDto;
import springdatajsonprocesing.domain.entities.User;
import springdatajsonprocesing.domain.repositories.UserRepository;
import springdatajsonprocesing.utils.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private static final String USERS_PATH = "src/main/resources/json/users.json";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
    }


    @Override
    @Transactional
    public String seedUsers() throws IOException {
        String content =
                String.join("", Files.readAllLines(Path.of(USERS_PATH)));
        UserSeedDto[] userSeedDtos = this.gson.fromJson(content, UserSeedDto[].class);
        StringBuilder sb = new StringBuilder();
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if (this.validatorUtil.isValid(userSeedDto)) {
                User user = this.modelMapper.map(userSeedDto, User.class);
                this.userRepository.saveAndFlush(user);
                sb.append(String.format("Correctly added user %s%n", user.getLastName()));
            } else {
                this.validatorUtil.violations(userSeedDto)
                        .forEach(e -> sb.append(e.getMessage()).append(System.lineSeparator()));
            }
        }
        setUsersRandomFriends();
        return sb.toString();

    }

    private void setUsersRandomFriends() {

        List<User> allUsers = this.userRepository.findAll();
        Random random = new Random();
        for (User user : allUsers) {
            User friend1 = null;
            User friend2 = null;
            do {
                friend1 = allUsers.get(random.nextInt(allUsers.size()));
                friend2 = allUsers.get(random.nextInt(allUsers.size()));
            } while (
                    !friend1.getId().equals(user.getId()) &&
                            !friend2.getId().equals(user.getId()) &&
                            !friend1.getId().equals(friend2.getId())
            );
            Set<User> friends = new HashSet<>();
            friends.add(friend1);
            friends.add(friend2);
            this.userRepository.getById(user.getId()).setFriends(friends);
        }
    }
}
