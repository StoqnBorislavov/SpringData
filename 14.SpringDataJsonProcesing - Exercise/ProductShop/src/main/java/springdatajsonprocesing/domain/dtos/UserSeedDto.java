package springdatajsonprocesing.domain.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public class UserSeedDto {

    @Nullable
    @Expose
    private String firstName;
    @Length(min = 3, message = "Min length for last name is 3 chars.")
    @Expose
    private String lastName;
    @Nullable
    @Expose
    private int age;

    public UserSeedDto() {
    }

    public UserSeedDto(@Nullable String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
