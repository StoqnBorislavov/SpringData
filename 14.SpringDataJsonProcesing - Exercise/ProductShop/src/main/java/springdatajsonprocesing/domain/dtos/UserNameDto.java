package springdatajsonprocesing.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.persistence.ElementCollection;

public class UserNameDto {

    @Expose
    private String lastName;

    public UserNameDto() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
