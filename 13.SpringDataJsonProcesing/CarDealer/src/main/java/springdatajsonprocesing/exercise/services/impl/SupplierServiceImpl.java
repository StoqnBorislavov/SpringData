package springdatajsonprocesing.exercise.services.impl;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.exercise.domain.dtos.LocalSupplierDto;
import springdatajsonprocesing.exercise.domain.dtos.SupplierSeedDto;
import springdatajsonprocesing.exercise.domain.entities.Supplier;
import springdatajsonprocesing.exercise.domain.repositories.SupplierRepository;
import springdatajsonprocesing.exercise.services.SupplierService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String SUPPLIER_PATH = "src/main/resources/jsons/suppliers.json";
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSupplier() throws IOException {
        String content =
                String.join("", Files.readAllLines(Path.of(SUPPLIER_PATH)));

        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content, SupplierSeedDto[].class);

        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            this.supplierRepository.saveAndFlush(
                    this.modelMapper.map(supplierSeedDto, Supplier.class));
        }
    }

    @Override
    public String getAllLocalSuppliers() {
        List<Supplier> all = this.supplierRepository.findAll();
        List<Supplier> localSupplier = all.stream().filter(s -> !s.isImporter()).collect(Collectors.toList());

        List<LocalSupplierDto> localSupplierDtos = new ArrayList<>();
        for (Supplier supplier : localSupplier) {
            LocalSupplierDto supplierDto = this.modelMapper.map(supplier, LocalSupplierDto.class);
            supplierDto.setPartsCount(supplier.getParts().size());
            localSupplierDtos.add(supplierDto);
        }
        return this.gson.toJson(localSupplierDtos);
    }
}
