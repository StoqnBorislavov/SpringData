package springdatajsonprocesing.exercise.domain.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SaleInfoExportDto {
    @Expose
    private CarExportWithPriceDto car;
    @Expose
    private CustomerNameExportDto customer;
    @Expose
    private double discount;
    @Expose
    private BigDecimal price;
    @Expose
    private BigDecimal priceWithDiscount;

    public SaleInfoExportDto() {
    }

    public CarExportWithPriceDto getCar() {
        return car;
    }

    public void setCar(CarExportWithPriceDto car) {
        this.car = car;
    }

    public CustomerNameExportDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerNameExportDto customer) {
        this.customer = customer;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
