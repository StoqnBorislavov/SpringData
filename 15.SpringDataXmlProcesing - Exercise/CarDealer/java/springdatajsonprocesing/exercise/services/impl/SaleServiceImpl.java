package springdatajsonprocesing.exercise.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.exercise.domain.dtos.exportdtojson.SaleInfoExportDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.SaleExportDto;
import springdatajsonprocesing.exercise.domain.dtos.exportdtoxml.SaleExportRootDto;
import springdatajsonprocesing.exercise.domain.entities.Car;
import springdatajsonprocesing.exercise.domain.entities.Customer;
import springdatajsonprocesing.exercise.domain.entities.Part;
import springdatajsonprocesing.exercise.domain.entities.Sale;
import springdatajsonprocesing.exercise.domain.repositories.CarRepository;
import springdatajsonprocesing.exercise.domain.repositories.CustomerRepository;
import springdatajsonprocesing.exercise.domain.repositories.SaleRepository;
import springdatajsonprocesing.exercise.services.SaleService;
import springdatajsonprocesing.exercise.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private static final String SALE_DISCOUNT_PATH = "src/main/resources/xmls/exported/sales-discount.xml";
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final XmlParser xmlParser;


    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;

        this.xmlParser = xmlParser;
    }


    @Override
    public void seedSales() {

        Sale sale = new Sale();
        sale.setCar(getRandomCar());
        sale.setCustomer(getRandomCustomer());
        sale.setDiscount(5);

        Sale sale1 = new Sale();
        sale1.setCar(getRandomCar());
        sale1.setCustomer(getRandomCustomer());
        sale1.setDiscount(10);

        Sale sale2 = new Sale();
        sale2.setCar(getRandomCar());
        sale2.setCustomer(getRandomCustomer());
        sale2.setDiscount(30);

        this.saleRepository.saveAndFlush(sale);
        this.saleRepository.saveAndFlush(sale1);
        this.saleRepository.saveAndFlush(sale2);
    }

    @Override
    public String getAllSales() {
        List<Sale> all = this.saleRepository.findAll();
        List<SaleInfoExportDto> saleInfoExportDtos =new ArrayList<>();
        for (Sale sale : all) {
            SaleInfoExportDto saleInfo = this.modelMapper.map(sale, SaleInfoExportDto.class);
            saleInfo.setDiscount(sale.getDiscount() * 1.0 / 100);
            BigDecimal price = BigDecimal.ZERO;
            for (Part part : sale.getCar().getParts()) {
                price = price.add(part.getPrice());
            }
            saleInfo.setPrice(price);
            BigDecimal priceWithDiscount = price.subtract(price.multiply(BigDecimal.valueOf(sale.getDiscount()* 1.0/ 100)));
            saleInfo.setPriceWithDiscount(priceWithDiscount);

            saleInfoExportDtos.add(saleInfo);
        }
        return this.gson.toJson(saleInfoExportDtos);
    }

    @Override
    public void salesDiscount() throws JAXBException {
        SaleExportRootDto exportRootDto = new SaleExportRootDto();
        List<SaleExportDto> collect = new ArrayList<>();
        for (Sale sale : this.saleRepository.findAll()) {
            SaleExportDto saleExportDto = this.modelMapper.map(sale, SaleExportDto.class);
            saleExportDto.setDiscount(saleExportDto.getDiscount() / 100);
            double totalPrice = sale.getCar().getParts()
                    .stream()
                    .mapToDouble(p -> Double.parseDouble(p.getPrice().toString()))
                    .sum();
            saleExportDto.setPrice(BigDecimal.valueOf(totalPrice));
            double priceWithDiscount = totalPrice - (totalPrice * sale.getDiscount() * 1.0 / 100);

            saleExportDto.setPriceWithDiscount(BigDecimal.valueOf(priceWithDiscount));
            collect.add(saleExportDto);
        }
        exportRootDto.setSales(collect);
        this.xmlParser.exportXml(exportRootDto, SaleExportRootDto.class, SALE_DISCOUNT_PATH);

    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long id = (long)random.nextInt((int)this.customerRepository.count()) + 1;

        Customer customer = this.customerRepository.findById(id).get();

        return customer;
    }

    private Car getRandomCar() {
        Random random = new Random();
        long id = (long)random.nextInt((int)this.carRepository.count()) + 1;

        Car car = this.carRepository.findById(id).get();

        return car;
    }
}
