package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.xmls.OfferImportDto;
import softuni.exam.models.dtos.xmls.OfferRootImportDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String OFFERS_PATH = "src/main/resources/files/xml/offers.xml";
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil, CarRepository carRepository, SellerRepository sellerRepository, OfferRepository offerRepository) {

        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(OFFERS_PATH)));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        OfferRootImportDto offerRootImportDto = this.xmlParser.parseXml(OfferRootImportDto.class, OFFERS_PATH);
        for (OfferImportDto offerImportDto : offerRootImportDto.getOffers()) {
            if(this.validationUtil.isValid(offerImportDto)){
                Offer offer = this.modelMapper.map(offerImportDto, Offer.class);
                Car car = this.carRepository.findById(offer.getCar().getId()).get();
                Seller seller = this.sellerRepository.findById(offer.getSeller().getId()).get();

                //Here is the problem with duplicating reference on car pictures.
                offer.setPictures(new HashSet<>(car.getPictures()));
                offer.setCar(car);
                offer.setSeller(seller);

                sb.append(String.format("Successfully import offer %s - %s\n", offer.getAddedOn(), offer.isHasGoldStatus()));
                this.offerRepository.saveAndFlush(offer);
            } else {
                sb.append("Invalid offer").append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
