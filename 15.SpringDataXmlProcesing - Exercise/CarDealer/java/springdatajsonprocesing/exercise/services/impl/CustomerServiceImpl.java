package springdatajsonprocesing.exercise.services.impl;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.exercise.domain.dtos.exportdtojson.CustomerExportDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.CustomerExportToSalesDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.CustomerExportToSalesRootDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.CustomerOrderExportDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.CustomerOrderRootExportDto;
import springdatajsonprocesing.exercise.domain.dtos.importdtoxml.CustomerImportDto;
import springdatajsonprocesing.exercise.domain.dtos.importdtoxml.CustomerImportRootDto;
import springdatajsonprocesing.exercise.domain.entities.Customer;
import springdatajsonprocesing.exercise.domain.entities.Part;
import springdatajsonprocesing.exercise.domain.repositories.CustomerRepository;
import springdatajsonprocesing.exercise.services.CustomerService;
import springdatajsonprocesing.exercise.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_PATH = "src/main/resources/xmls/customers.xml";
    private static final String ORDERED_CUSTOMERS_PATH = "src/main/resources/xmls/exported/ordered-customers.xml";
    private static final String CUSTOMERS_TOTAL_SALES_PATH = "src/main/resources/xmls/exported/customers-total-sales.xml";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final XmlParser xmlParser;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedCustomers() throws IOException, JAXBException {
        CustomerImportRootDto customerImportRootDto = this.xmlParser.parseXml(CustomerImportRootDto.class, CUSTOMERS_PATH);
        for (CustomerImportDto customerDto : customerImportRootDto.getCustomers()) {
            Customer customer = this.modelMapper.map(customerDto, Customer.class);
            this.customerRepository.saveAndFlush(customer);
        }
    }


    @Override
    public String orderedCustomers() {

        Set<Customer> allOrdered = this.customerRepository.getAllByOrderByBirthDateAscYoungDriverAsc();
        List<CustomerExportDto> toExport = new ArrayList<>();
        for (Customer customer : allOrdered) {
            CustomerExportDto customerExportDto = this.modelMapper.map(customer, CustomerExportDto.class);
            toExport.add(customerExportDto);
        }
        return this.gson.toJson(toExport);
    }

    @Override
    public void exportOrdered() throws JAXBException {
        List<CustomerOrderExportDto> dtos = this.customerRepository.findAllAndSort()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderExportDto.class))
                .collect(Collectors.toList());
        CustomerOrderRootExportDto rootDto = new CustomerOrderRootExportDto();
        rootDto.setCustomers(dtos);
        this.xmlParser.exportXml(rootDto, CustomerOrderRootExportDto.class, ORDERED_CUSTOMERS_PATH);
    }

    @Override
    public void findAllCustomersWithSales() throws JAXBException {
        Set<Customer> all = this.customerRepository.findAllCustomersWithSales();

        CustomerExportToSalesRootDto customerExportToSalesRootDto = new CustomerExportToSalesRootDto();
        List<CustomerExportToSalesDto> customerExportToSalesDtos = new ArrayList<>();
        // This typeMap didn't work for some reason.
//        TypeMap<Customer, CustomerExportToSalesDto> map = this.modelMapper.createTypeMap(Customer.class, CustomerExportToSalesDto.class);
//        map.addMapping(Customer::getName, CustomerExportToSalesDto::setFullName);
        for (Customer customer : all) {
            double price = customer.getSales().stream().mapToDouble(sale -> sale.getCar().getParts().stream().mapToDouble(part -> Double.parseDouble(part.getPrice().toString())).sum()).sum();
            CustomerExportToSalesDto customerDto = this.modelMapper.map(customer, CustomerExportToSalesDto.class);
            customerDto.setFullName(customer.getName());
            customerDto.setBoughtCars(customer.getSales().size());
            customerDto.setSpentMoney(BigDecimal.valueOf(price));
            customerExportToSalesDtos.add(customerDto);
        }
        customerExportToSalesRootDto.setCustomers(customerExportToSalesDtos);
        this.xmlParser.exportXml(customerExportToSalesRootDto, CustomerExportToSalesRootDto.class, CUSTOMERS_TOTAL_SALES_PATH);
    }
}
