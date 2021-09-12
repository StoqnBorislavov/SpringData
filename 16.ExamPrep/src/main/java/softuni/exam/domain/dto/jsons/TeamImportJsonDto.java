package softuni.exam.domain.dto.jsons;

import com.google.gson.annotations.Expose;

public class TeamImportJsonDto {
    @Expose
    private String name;

    public TeamImportJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
