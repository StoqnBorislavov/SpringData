package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.xmls.PictureImportDto;
import softuni.exam.domain.dto.xmls.PictureImportRootDto;
import softuni.exam.domain.entity.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURES_PATH = "src/main/resources/files/xml/pictures.xml";
    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public String importPictures() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        PictureImportRootDto pictureImportRootDto = this.xmlParser.parseXml(PictureImportRootDto.class, PICTURES_PATH);
        for (PictureImportDto pictureDto : pictureImportRootDto.getPictures()) {
            if(this.validatorUtil.isValid(pictureDto)){
                Picture picture = this.modelMapper.map(pictureDto, Picture.class);
                builder.append(String.format("Successfully imported picture - %s", picture.getUrl())).append(System.lineSeparator());
                this.pictureRepository.saveAndFlush(picture);
            } else {
                builder.append("Invalid picture").append(System.lineSeparator());
            }
        }
       return builder.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return String.join("\n", Files.readAllLines(Path.of(PICTURES_PATH)));
    }


}
