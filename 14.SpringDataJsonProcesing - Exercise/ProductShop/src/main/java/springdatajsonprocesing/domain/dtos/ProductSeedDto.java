package springdatajsonprocesing.domain.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import springdatajsonprocesing.domain.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProductSeedDto {

    @Expose
    @NotNull(message = "Product name can't be null.")
    @Length(min = 3, message = "Product name must be at least 3 chars.")
    private String name;
    @Expose
    @NotNull(message = "Product price can't be null.")
    @Positive(message = "Product price must be positive number")
    private BigDecimal price;
    @Expose
    private User buyer;
    @Expose
    @NotNull(message = "Product must have seller")
    private User seller;

    public ProductSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
