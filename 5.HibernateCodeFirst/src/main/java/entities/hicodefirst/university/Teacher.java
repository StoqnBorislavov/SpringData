package entities.hicodefirst.university;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person{
    private String email;
    private double salaryPerHour;

    private Set<Course> courses;

    public Teacher() {
    }

    @OneToMany(mappedBy = "teacher",targetEntity = Course.class)
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String speciality) {
        this.email = speciality;
    }


    @Column(name = "salary_per_hour")
    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

}
