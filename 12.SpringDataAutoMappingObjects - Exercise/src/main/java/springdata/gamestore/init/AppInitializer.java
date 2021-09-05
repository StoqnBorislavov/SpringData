package springdata.gamestore.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.gamestore.domain.dtos.*;
import springdata.gamestore.service.GameService;
import springdata.gamestore.service.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class AppInitializer implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;
    @Autowired
    public AppInitializer(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String[] tokens = reader.readLine().split("\\|");

            switch (tokens[0]) {
                case "RegisterUser":
                    UserRegisterDto registerDto = new UserRegisterDto(tokens[1], tokens[2], tokens[3], tokens[4]);
                    System.out.println(this.userService.registerUser(registerDto));
                    break;

                case "LoginUser":
                    UserLogInDto logInDto = new UserLogInDto(tokens[1], tokens[2]);
                    System.out.println(this.userService.logInUser(logInDto));
                    break;

                case"Logout":
                    this.userService.logOut();
                    break;

                case "AddGame":
                    LocalDate date = LocalDate.parse(tokens[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    AddGameDto addGameDto =new AddGameDto(tokens[1], new BigDecimal(tokens[2]), Double.parseDouble(tokens[3]),
                            tokens[4], tokens[5], tokens[6], date);
                    System.out.println(this.gameService.addGame(addGameDto));
                    break;

                case "DeleteGame":
                    DeleteGameDto deleteGameDto = new DeleteGameDto(Long.parseLong(tokens[1]));
                    System.out.println(this.gameService.deleteGame(deleteGameDto));
                    break;

                case "AllGames":
                    System.out.println(this.gameService.showAllGames());

                case "DetailGame":
                    String gameInfo = this.gameService.detailsForGameByTitle(tokens[1]);
                    System.out.println(gameInfo);
            }

        }
    }

}
