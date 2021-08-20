package models;

public class Employee {
    public long id;
    private String firstName;
    private String lastName;

    public Employee(){
    }
    public Employee(String firstName, String lastName){
        this(0, firstName, lastName);
    }

    public Employee(long id, String firstName, String lastName) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
