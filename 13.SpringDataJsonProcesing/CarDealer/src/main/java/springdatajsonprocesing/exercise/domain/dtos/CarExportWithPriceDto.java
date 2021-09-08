package springdatajsonprocesing.exercise.domain.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CarExportWithPriceDto {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private long travelledDistance;

    private BigDecimal price;

    public CarExportWithPriceDto(){
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
