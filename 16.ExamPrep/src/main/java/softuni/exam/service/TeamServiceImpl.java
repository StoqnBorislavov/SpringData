package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.xmls.TeamImportDto;
import softuni.exam.domain.dto.xmls.TeamImportRootDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.domain.entity.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private static final String TEAMS_PATH = "src/main/resources/files/xml/teams.xml";
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder builder = new StringBuilder();
        TeamImportRootDto teamImportRootDto = this.xmlParser.parseXml(TeamImportRootDto.class, TEAMS_PATH);
        for (TeamImportDto teamDto : teamImportRootDto.getTeams()) {
            if(this.validatorUtil.isValid(teamDto) && this.pictureRepository.findByUrl(teamDto.getPicture().getUrl()) != null){
                Team team = this.modelMapper.map(teamDto, Team.class);
                Picture picture = this.pictureRepository.findByUrl(team.getPicture().getUrl());
                team.setPicture(picture);
                builder.append(String.format("Successfully imported - %s", team.getName())).append(System.lineSeparator());
                this.teamRepository.saveAndFlush(team);
            } else {
                builder.append("Invalid team").append(System.lineSeparator());
            }
        }
       return builder.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return String.join("\n", Files.readAllLines(Path.of(TEAMS_PATH)));
    }

}
