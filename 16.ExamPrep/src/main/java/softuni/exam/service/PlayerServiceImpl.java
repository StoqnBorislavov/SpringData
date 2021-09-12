package softuni.exam.service;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.jsons.PlayerImportDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.domain.entity.Player;
import softuni.exam.domain.entity.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;


import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private static final String PLAYERS_PATH = "src/main/resources/files/json/players.json";
    private final TeamRepository teamRepository;
    private final PlayerRepository  playerRepository;
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PlayerServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository, PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder builder = new StringBuilder();
        PlayerImportDto[] playerImportDtos = this.gson.fromJson(this.readPlayersJsonFile(), PlayerImportDto[].class);
        for (PlayerImportDto playerImportDto : playerImportDtos) {
            Picture byUrl = this.pictureRepository.findByUrl(playerImportDto.getPicture().getUrl());
            Team byName = this.teamRepository.findByName(playerImportDto.getTeam().getName());
            if(this.validatorUtil.isValid(playerImportDto) &&
                    byUrl != null && byName != null){
                Player player = this.modelMapper.map(playerImportDto, Player.class);
                Picture picture = this.pictureRepository.findByUrl(player.getPicture().getUrl());
                Team team = this.teamRepository.findByName(player.getTeam().getName());
                player.setPicture(picture);
                player.setTeam(team);
                builder.append(String.format("Successfully imported player: %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator());
                this.playerRepository.saveAndFlush(player);
            } else {
                builder.append("Invalid player").append(System.lineSeparator());
            }
        }
       return builder.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return String.join("\n", Files.readAllLines(Path.of(PLAYERS_PATH)));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder builder = new StringBuilder();
        Set<Player> players = this.playerRepository.findBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000));
//        "Player name: {firstName} {lastName}
//        Number: {player number}
//        Salary: {player salary}
//        Team: {team name}

        for (Player player : players) {

            builder.append(String.format("Player name: %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator());
            builder.append(String.format("\tNumber: %d", player.getNumber())).append(System.lineSeparator());
            builder.append(String.format("\tSalary: %s", player.getSalary().toString())).append(System.lineSeparator());
            builder.append(String.format("\tTeam: %s", player.getTeam().getName())).append(System.lineSeparator());
        }
        return builder.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder builder = new StringBuilder();
        String teamName = "North Hub";
        Set<Player> players = this.playerRepository.findByTeamNameOrderById(teamName);
        builder.append("Team: ").append(teamName).append(System.lineSeparator());
//        Player name: {playerOne firstName} {playerOne lastName} - {playerOne position}
//        Number: {playerOne number}

        for (Player player : players) {
            builder.append(String.format("\tPlayer name: %s %s - %s",
                    player.getFirstName(), player.getLastName(), player.getPosition())).append(System.lineSeparator());
            builder.append(String.format("\tNumber: %d", player.getNumber())).append(System.lineSeparator());
        }
        return builder.toString();
    }


}
