package springdatajsonprocesing.exercise.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.exercise.domain.dtos.importdtoxml.PartImportDto;
import springdatajsonprocesing.exercise.domain.dtos.importdtoxml.PartImportRootDto;
import springdatajsonprocesing.exercise.domain.dtos.seeddtojson.PartSeedDto;
import springdatajsonprocesing.exercise.domain.entities.Part;
import springdatajsonprocesing.exercise.domain.entities.Supplier;
import springdatajsonprocesing.exercise.domain.repositories.PartRepository;
import springdatajsonprocesing.exercise.domain.repositories.SupplierRepository;
import springdatajsonprocesing.exercise.services.PartService;
import springdatajsonprocesing.exercise.utils.XmlParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private static final String PARTS_PATH = "src/main/resources/xmls/parts.xml";
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final XmlParser xmlParser;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedParts() throws Exception {
        PartImportRootDto partImportRootDto = this.xmlParser.parseXml(PartImportRootDto.class, PARTS_PATH);
        for (PartImportDto partDto : partImportRootDto.getParts()) {
            Part part = this.modelMapper.map(partDto, Part.class);
            part.setSupplier(this.getRandomSupplier());
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

