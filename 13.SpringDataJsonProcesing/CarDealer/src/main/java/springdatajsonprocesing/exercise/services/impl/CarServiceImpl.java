package springdatajsonprocesing.exercise.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdatajsonprocesing.exercise.domain.dtos.CarExportDto;
import springdatajsonprocesing.exercise.domain.dtos.CarExportDtoWithParts;
import springdatajsonprocesing.exercise.domain.dtos.CarsSeedDto;
import springdatajsonprocesing.exercise.domain.entities.Car;
import springdatajsonprocesing.exercise.domain.entities.Part;
import springdatajsonprocesing.exercise.domain.entities.Supplier;
import springdatajsonprocesing.exercise.domain.repositories.CarRepository;
import springdatajsonprocesing.exercise.domain.repositories.PartRepository;
import springdatajsonprocesing.exercise.services.CarService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    private static final String PARTS_PATH = "src/main/resources/jsons/cars.json";
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final PartRepository partRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, PartRepository partRepository) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
    }

    @Override
    @Transactional
    public void seedCars() throws Exception {
        String content =
                String.join("", Files.readAllLines(Path.of(PARTS_PATH)));
        CarsSeedDto[] carsSeedDtos = this.gson.fromJson(content, CarsSeedDto[].class);

        for (CarsSeedDto carsSeedDto : carsSeedDtos) {
             Car car = this.modelMapper.map(carsSeedDto, Car.class);
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
