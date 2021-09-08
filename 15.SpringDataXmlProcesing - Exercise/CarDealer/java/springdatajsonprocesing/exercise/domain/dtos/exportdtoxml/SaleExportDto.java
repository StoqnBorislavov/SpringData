package springdatajsonprocesing.exercise.domain.dtos.exportdtoxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleExportDto {

    @XmlElement(name = "car")
    private CarForSaleExportDto car;
    @XmlElement(name = "customer-name")
    private String customerName;
    @XmlElement
    private double discount;
    @XmlElement
    private BigDecimal price;
    @XmlElement(name = "price-with-discount")
    private BigDecimal priceWithDiscount;

    public SaleExportDto() {
    }

    public CarForSaleExportDto getCar() {
        return car;
    }

    public void setCar(CarForSaleExportDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
