package springdata.gamestore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.gamestore.domain.dtos.*;
import springdata.gamestore.domain.entity.Game;
import springdata.gamestore.domain.entity.Role;
import springdata.gamestore.repository.GameRepository;
import springdata.gamestore.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final GameRepository gameRepository;
    private UserDto userDto;

    @Autowired
    public GameServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, GameRepository gameRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.gameRepository = gameRepository;
    }

    @Override
    public String addGame(AddGameDto addGameDto) {
        StringBuilder sb = new StringBuilder();
        if (this.userDto == null || this.userDto.getRole().equals(Role.USER)) {
            sb.append("Invalid logged in user");
        } else if (this.validatorUtil.isValid(addGameDto)) {
            Game game = this.modelMapper.map(addGameDto, Game.class);
            this.gameRepository.saveAndFlush(game);
            sb.append(String.format("Added %s", game.getTitle()));
        } else {
            this.validatorUtil.violations(addGameDto)
                    .forEach(e -> sb.append(e.getMessage()).append(System.lineSeparator()));

        }
        return sb.toString();
    }

    @Override
    public void setLoggedUser(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public String deleteGame(DeleteGameDto deleteGameDto) {
        StringBuilder sb = new StringBuilder();
        if (this.userDto == null || this.userDto.getRole().equals(Role.USER)) {
            sb.append("Invalid logged in user");
        } else {
            Optional<Game> game = this.gameRepository.findById(deleteGameDto.getId());
            if (game.isPresent()) {
                this.gameRepository.delete(game.get());
                sb.append(String.format("Game %s was delete", game.get().getTitle()));
            } else {
                sb.append("Cannot find game");
            }
        }
        return sb.toString();
    }

    @Override
    public String showAllGames() {
        StringBuilder sb = new StringBuilder();
        List<Game> all = this.gameRepository.findAll();
        List<AllGamesDto> allGamesDtos = new ArrayList<>();
        for (Game game : all) {
            AllGamesDto gamesDto = modelMapper.map(game, AllGamesDto.class);
            allGamesDtos.add(gamesDto);
        }
        if (allGamesDtos.isEmpty()) {
            sb.append("There is no games in the shop.");
        } else {
            allGamesDtos.forEach(g -> sb.append(String.format("%s %s%n", g.getTitle(), g.getPrice())));
        }
        return sb.toString();
    }

    @Override
    public String detailsForGameByTitle(String title) {
        StringBuilder sb = new StringBuilder();
        Game game = this.gameRepository.findByTitle(title);
        if (game == null) {
            return "There is no game with present title.";
        }
        DetailsGameDto gameDto = modelMapper.map(game, DetailsGameDto.class);
        sb.append(gameDto.toString());
        return sb.toString();
    }
}
