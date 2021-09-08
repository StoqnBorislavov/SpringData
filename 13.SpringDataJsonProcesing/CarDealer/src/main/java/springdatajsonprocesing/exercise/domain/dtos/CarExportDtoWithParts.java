package springdatajsonprocesing.exercise.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarExportDtoWithParts {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private long travelledDistance;
    @Expose
    private List<PartsExportDto> parts;

    public CarExportDtoWithParts() {
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

    public List<PartsExportDto> getParts() {
        return parts;
    }

    public void setParts(List<PartsExportDto> parts) {
        this.parts = parts;
    }
}
