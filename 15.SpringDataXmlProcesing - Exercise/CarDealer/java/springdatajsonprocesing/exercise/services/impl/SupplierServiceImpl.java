package springdatajsonprocesing.exercise.services.impl;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.exercise.domain.dtos.exportdtojson.LocalSupplierDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.CarRootExportDtoForToyota;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.SupplierExportDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.SupplierExportRootDto;
import springdatajsonprocesing.exercise.domain.dtos.importdtoxml.SupplierImportDto;
import springdatajsonprocesing.exercise.domain.dtos.importdtoxml.SupplierImportRootDto;
import springdatajsonprocesing.exercise.domain.entities.Supplier;
import springdatajsonprocesing.exercise.domain.repositories.SupplierRepository;
import springdatajsonprocesing.exercise.services.SupplierService;
import springdatajsonprocesing.exercise.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String SUPPLIER_PATH = "src/main/resources/xmls/suppliers.xml";
    private static final String LOCAL_SUPPLIERS_PATH = "src/main/resources/xmls/exported/local-suppliers.xml";
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;

    }

    @Override
    public void seedSupplier() throws IOException, JAXBException {
        SupplierImportRootDto supplierImportRootDto = this.xmlParser.parseXml(SupplierImportRootDto.class, SUPPLIER_PATH);

        for (SupplierImportDto supplier : supplierImportRootDto.getSuppliers()) {
            this.supplierRepository.saveAndFlush(this.modelMapper.map(supplier, Supplier.class));
        }
    }

    @Override
    public String getAllLocalSuppliers() {
//        List<Supplier> all = this.supplierRepository.findAll();
//        List<Supplier> localSupplier = all.stream().filter(s -> !s.isImporter()).collect(Collectors.toList());
//
//        List<LocalSupplierDto> localSupplierDtos = new ArrayList<>();
//        for (Supplier supplier : localSupplier) {
//            LocalSupplierDto supplierDto = this.modelMapper.map(supplier, LocalSupplierDto.class);
//            supplierDto.setPartsCount(supplier.getParts().size());
//            localSupplierDtos.add(supplierDto);
//        }
//        return this.gson.toJson(localSupplierDtos);
        return null;
    }

    @Override
    public void findAllLocalSuppliers() throws JAXBException {
        List<Supplier> all = this.supplierRepository.findAll();
        List<Supplier> localSuppliers = all.stream().filter(s -> !s.isImporter()).collect(Collectors.toList());
        SupplierExportRootDto supplierExportRootDto = new SupplierExportRootDto();
        List<SupplierExportDto> supplierExportDtos = new ArrayList<>();
        for (Supplier supplierDto : localSuppliers) {
            SupplierExportDto supplier = this.modelMapper.map(supplierDto, SupplierExportDto.class);
            int partsCount = supplierDto.getParts().size();
            supplier.setPartsCount(partsCount);
            supplierExportDtos.add(supplier);
        }
        supplierExportRootDto.setSuppliers(supplierExportDtos);
        this.xmlParser.exportXml(supplierExportRootDto, SupplierExportRootDto.class, LOCAL_SUPPLIERS_PATH);
    }
}
