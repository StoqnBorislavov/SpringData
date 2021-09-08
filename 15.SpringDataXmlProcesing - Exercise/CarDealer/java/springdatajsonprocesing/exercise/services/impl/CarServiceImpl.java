package springdatajsonprocesing.exercise.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdatajsonprocesing.exercise.domain.dtos.exportdtojson.CarExportDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtojson.CarExportDtoWithParts;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.*;
import springdatajsonprocesing.exercise.domain.dtos.importdtoxml.CarImportDto;
import springdatajsonprocesing.exercise.domain.dtos.importdtoxml.CarImportRootDto;
import springdatajsonprocesing.exercise.domain.entities.Car;
import springdatajsonprocesing.exercise.domain.entities.Part;
import springdatajsonprocesing.exercise.domain.repositories.CarRepository;
import springdatajsonprocesing.exercise.domain.repositories.PartRepository;
import springdatajsonprocesing.exercise.services.CarService;
import springdatajsonprocesing.exercise.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    private static final String CARS_PATH = "src/main/resources/xmls/cars.xml";
    private static final String CARS_AND_PARTS_PATH = "src/main/resources/xmls/exported/cars-and-parts.xml";
    private static final String TOYOTA_CARS_PATH = "src/main/resources/xmls/exported/toyota-cars.xml";
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final PartRepository partRepository;
    private final XmlParser xmlParser;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, PartRepository partRepository, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;

        this.xmlParser = xmlParser;
    }

    @Override
    @Transactional
    public void seedCars() throws Exception {
        CarImportRootDto carImportRootDto = this.xmlParser.parseXml(CarImportRootDto.class, CARS_PATH);
        for (CarImportDto carDto : carImportRootDto.getCars()) {
            Car car = this.modelMapper.map(carDto, Car.class);
            car.setParts(getRandomParts());
            this.carRepository.saveAndFlush(car);
        }
    }

    @Override
    public String findByToyota() {
        Set<Car> toyota = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");
        List<CarExportDto> carExportDtos =new ArrayList<>();
        for (Car car : toyota) {
            CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);
            carExportDtos.add(carExportDto);
        }
        return this.gson.toJson(carExportDtos);
    }

    @Override
    public String getAllCarsWithParts() {
        List<Car> all = this.carRepository.findAll();
        List<CarExportDtoWithParts> carWithParts = new ArrayList<>();
        for (Car car : all) {
            CarExportDtoWithParts carWith = this.modelMapper.map(car, CarExportDtoWithParts.class);
            carWithParts.add(carWith);
        }
        return this.gson.toJson(carWithParts);
    }

    @Override
    public void findCarsAndParts() throws JAXBException {
        List<Car> all = this.carRepository.findAll();
        CarExportRootDto carExportRootDto = new CarExportRootDto();
        List<CarExportDtoXml> carExportDtos = new ArrayList<>();

        for (Car car : all) {
            CarExportDtoXml carExportDtoXml = this.modelMapper.map(car, CarExportDtoXml.class);
            // This code is not needed, because the modelMapper do it itself.
//            PartExportRootDto partExportRootDto = new PartExportRootDto();
//            List<PartExportDto> partExportDtos = new ArrayList<>();
//            for (Part part : car.getParts()) {
//                PartExportDto partDto = this.modelMapper.map(part, PartExportDto.class);
//                partExportDtos.add(partDto);
//            }
//            partExportRootDto.setParts(partExportDtos);
//            carExportDtoXml.setParts(partExportRootDto);
            carExportDtos.add(carExportDtoXml);
        }
        carExportRootDto.setCars(carExportDtos);
        this.xmlParser.exportXml(carExportRootDto, CarExportRootDto.class, CARS_AND_PARTS_PATH);

    }

    @Override
    public void getByMake() throws JAXBException {
        Set<Car> toyotaCars = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");
        CarRootExportDtoForToyota carExportRootDto = new CarRootExportDtoForToyota();
        List<CarExportDtoForToyota> carExportDto = new ArrayList<>();

        for (Car carDto : toyotaCars) {
            CarExportDtoForToyota car = this.modelMapper.map(carDto, CarExportDtoForToyota.class);
            carExportDto.add(car);
        }
        carExportRootDto.setCars(carExportDto);
        this.xmlParser.exportXml(carExportRootDto, CarRootExportDtoForToyota.class, TOYOTA_CARS_PATH);

    }

    private Set<Part> getRandomParts() throws Exception {
        Set<Part> parts = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Part part = getRandomPart();
            parts.add(part);
        }
        return parts;
    }
    private Part getRandomPart() throws Exception {
        Random random = new Random();
        long index = random.nextInt((int) this.partRepository.count()) + 1;
        Optional<Part> part = this.partRepository.findById(index);

        if(part.isPresent()){
            return part.get();
        } else {
            throw new Exception("Part dont exist");
        }
    }
}
