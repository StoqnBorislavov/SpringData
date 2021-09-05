package springdata.gamestore.service;

import springdata.gamestore.domain.dtos.AddGameDto;
import springdata.gamestore.domain.dtos.AllGamesDto;
import springdata.gamestore.domain.dtos.DeleteGameDto;
import springdata.gamestore.domain.dtos.UserDto;

import java.util.List;

public interface GameService {

    String addGame(AddGameDto addGameDto);

    void setLoggedUser(UserDto userDto);

    String deleteGame(DeleteGameDto deleteGameDto);

    String showAllGames();

    String detailsForGameByTitle(String title);
}
