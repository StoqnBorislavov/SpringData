import entities.*;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run() {
        //Ex2
        //changeCasingEx2();

        //Ex3
//        try {
//            containsEmployeeEx3();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex4
        //employeesWithSalaryOver50000Ex4();

        //Ex5
        //employeesFromDepartmentsEx5();

        //Ex6
//        try {
//            addingNewAddressAndUpdatingEmployeeEx6();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex7
        //addressesWithEmployeeTownEx7();

       //Ex8
//        try {
//            employeeWithProjectsEx8();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex9
        //findLatest10ProjectsEx9();
        //Ex10
        //increaseSalariesEx10();

        //Ex11
        //findEmployeesByFirstNameEx11();

        //Ex12
        //employeesMaximumSalaryByDepartmentsEx12();



    }

    private void findLatest10ProjectsEx9() {
        entityManager.createQuery("SELECT p FROM Project p " +
                        "ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(project -> {
                    System.out.printf("Project name: %s%n\tProject Description: %s%n\t" +
                            "Project Start Date: %s%n\t" +
                            "Project End Date: %s%n", project.getName(),
                            project.getDescription(),
                            project.getStartDate(),
                            project.getEndDate());
                });


    }

    private void employeesMaximumSalaryByDepartmentsEx12() {
        entityManager.
                createQuery("SELECT d.name, MAX(e.salary) " +
                                "FROM Employee e " +
                                "JOIN e.department d " +
                                "GROUP BY d.id " +
                                "HAVING MAX(e.salary) NOT BETWEEN 30000 " +
                                "AND 50000",
                        Object[].class)
                .getResultStream()
                .forEach(objects -> System.out.printf("%s %.2f%n",
                        objects[0],
                        objects[1]));



    }

    private void findEmployeesByFirstNameEx11() {
        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.firstName LIKE 'SA%'", Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s - %s - ($%.2f)%n",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getJobTitle(),
                            employee.getSalary());
                });

    }

    private void increaseSalariesEx10() {
        entityManager.getTransaction().begin();
        int affectedRows = entityManager.createQuery("UPDATE Employee  e " +
                        "SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department.id IN (1, 2, 4, 11)")
                .executeUpdate();


        entityManager.getTransaction().commit();
        System.out.println("Affected rows " + affectedRows);

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.id IN (1, 2, 4, 11)", Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s ($%.2f)%n",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getSalary());
                });
    }

    private void employeeWithProjectsEx8() throws IOException {
        System.out.println("Enter valid employee id:");
        int id = Integer.parseInt(reader.readLine());
        Employee employee = entityManager.find(Employee.class, id);
        System.out.printf("%s %s - %s%n",employee.getFirstName(),
                employee.getLastName(), employee.getJobTitle());
        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("\t%s%n", project.getName());
                });
    }

    private void addressesWithEmployeeTownEx7() {
        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address  a " +
                        "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();
        addresses.forEach(address -> System.out.printf("%s, %s - %d%n",
                address.getText(), address.getTown().getName(), address.getEmployees().size()));


    }

    private void addingNewAddressAndUpdatingEmployeeEx6() throws IOException {
        Address address = createNewAddress("Vitoshka 15");
        System.out.println("Enter employee last name:");
        String lastName = reader.readLine();
        Employee employee = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.lastName = :name", Employee.class)
                .setParameter("name", lastName)
                .getSingleResult();
        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
    }

    private Address createNewAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
        return address;
    }

    private void employeesFromDepartmentsEx5() {
        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.name = 'Research and Development' " +
                        "ORDER BY e.salary, e.id", Employee.class)
                .getResultStream()
                .forEach(employee -> System.out.printf("%s %s from %s - $%.2f%n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDepartment(),
                        employee.getSalary()));
        //Diane Margheim from Research and Development - $40900.00
    }

    private void employeesWithSalaryOver50000Ex4() {
        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.salary > 50000", Employee.class)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);
    }

    private void containsEmployeeEx3() throws IOException {
        System.out.println("Enter employee full name:");
        String fullName = reader.readLine();
        List<Employee> employees = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE concat(e.firstName, ' ',e.lastName) = :name", Employee.class)
                .setParameter("name", fullName)
                .getResultList();
        System.out.println(employees.size() == 0 ? "No" : "Yes");
    }

    private void changeCasingEx2() {
        List<Town> towns = entityManager
                .createQuery("SELECT t FROM Town t " +
                        "WHERE length(t.name) <= 5 ", Town.class)
                .getResultList();
        entityManager.getTransaction().begin();
        towns.forEach(entityManager::detach);
        for (Town town : towns) {
            town.setName(town.getName().toLowerCase());
        }
        towns.forEach(entityManager::merge);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
