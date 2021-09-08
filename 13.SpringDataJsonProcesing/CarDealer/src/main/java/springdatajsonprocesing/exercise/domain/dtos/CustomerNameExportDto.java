package springdatajsonprocesing.exercise.domain.dtos;

import com.google.gson.annotations.Expose;

public class CustomerNameExportDto {

    @Expose
    private String name;

    public CustomerNameExportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
