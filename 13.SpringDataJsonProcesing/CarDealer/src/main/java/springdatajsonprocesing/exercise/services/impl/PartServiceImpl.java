package springdatajsonprocesing.exercise.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.exercise.domain.dtos.PartSeedDto;
import springdatajsonprocesing.exercise.domain.entities.Part;
import springdatajsonprocesing.exercise.domain.entities.Supplier;
import springdatajsonprocesing.exercise.domain.repositories.PartRepository;
import springdatajsonprocesing.exercise.domain.repositories.SupplierRepository;
import springdatajsonprocesing.exercise.services.PartService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private static final String PARTS_PATH = "src/main/resources/jsons/parts.json";
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public void seedParts() throws Exception {
        String content =
                String.join("", Files.readAllLines(Path.of(PARTS_PATH)));
        PartSeedDto[] partSeedDtos = this.gson.fromJson(content, PartSeedDto[].class);
        for (PartSeedDto partSeedDto : partSeedDtos) {
            Part part = this.modelMapper.map(partSeedDto, Part.class);
            part.setSupplier(getRandomSupplier());

            this.partRepository.saveAndFlush(part);
        }

    }
    private Supplier getRandomSupplier() throws Exception {
        Random random = new Random();
        long index = random.nextInt((int) this.supplierRepository.count()) + 1;
        Optional<Supplier> supplier = this.supplierRepository.findById(index);

        if(supplier.isPresent()){
            return supplier.get();
        } else {
            throw new Exception("Supplier dont exist");
        }
    }
}

