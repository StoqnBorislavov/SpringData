package springdatajsonprocesing.domain.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CategorySeedDto {

    @NotNull
    @Expose
    @Length(min = 3, max = 15, message = "Name must be between 2 and 15 characters.")
    private String name;

    public CategorySeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
