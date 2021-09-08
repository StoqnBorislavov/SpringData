package springdatajsonprocesing.exercise.services.impl;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.exercise.domain.dtos.CustomerExportDto;
import springdatajsonprocesing.exercise.domain.dtos.CustomerSeedDto;
import springdatajsonprocesing.exercise.domain.entities.Customer;
import springdatajsonprocesing.exercise.domain.repositories.CustomerRepository;
import springdatajsonprocesing.exercise.services.CustomerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_PATH = "src/main/resources/jsons/customers.json";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public void seedCustomers() throws IOException {
        String content =
                String.join("", Files.readAllLines(Path.of(CUSTOMERS_PATH)));

        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(content, CustomerSeedDto[].class);
        TypeMap<CustomerSeedDto, Customer> typeMap =
                this.modelMapper.createTypeMap(CustomerSeedDto.class, Customer.class);

        Converter<String, LocalDateTime> dateTimeConverter = context -> {
            LocalDateTime localDateTime = LocalDateTime.parse(context.getSource());
            return localDateTime;
        };
        typeMap.addMappings(mapper -> mapper.using(dateTimeConverter).map(CustomerSeedDto::getBirthday, Customer::setBirthday));
        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            Customer customer = typeMap.map(customerSeedDto);
            this.customerRepository.saveAndFlush(customer);
        }
//        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(customersJson, CustomerSeedDto[].class);
//
//        TypeMap<CustomerSeedDto, Customer> typeMap =
//                this.modelMapper.createTypeMap(CustomerSeedDto.class, Customer.class);
//
//        Converter<String, LocalDateTime> dateTimeConverter = context -> {
//            LocalDateTime localDateTime = LocalDateTime.parse(context.getSource());
//            return localDateTime;
//        };
//
//        typeMap.addMappings(mapper -> {
//            mapper.skip(Customer::setId);
//            mapper.skip(Customer::setCarsBought);
//            mapper.using(dateTimeConverter).map(CustomerSeedDto::getBirthDate, Customer::setBirthDate);
//        });
//        typeMap.validate();
//
//        List<Customer> validCustomers = new ArrayList<>();
//
//        StringBuilder resultFromSeed = new StringBuilder();
//
//        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
//            resultFromSeed.append(customerSeedDto.toString());
//
//            if (!this.validatorUtil.isValid(customerSeedDto)) {
//                List<String> errors = this.validatorUtil.getErrorMessages(customerSeedDto);
//
//                resultFromSeed.append(" | Not added to database. Reasons: ")
//                        .append(String.join(" | ", errors))
//                        .append(System.lineSeparator());
//            } else {
//                validCustomers.add(typeMap.map(customerSeedDto));
//
//                resultFromSeed.append(" | Added to database!")
//                        .append(System.lineSeparator());
//            }
//        }
//
//        this.customerRepository.saveAll(validCustomers);
//
//        return resultFromSeed.toString().trim();
    }

    @Override
    public String orderedCustomers() {

        Set<Customer> allOrdered = this.customerRepository.getAllByOrderByBirthdayAscYoungDriverAsc();
        List<CustomerExportDto> toExport = new ArrayList<>();
        for (Customer customer : allOrdered) {
            CustomerExportDto customerExportDto = this.modelMapper.map(customer, CustomerExportDto.class);
            toExport.add(customerExportDto);
        }
        return this.gson.toJson(toExport);
    }
}
