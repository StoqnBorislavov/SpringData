import base.DataRepository;
import models.Employee;
import repositories.EmployeesDataRepository;
import repositories.JdbcDataRepository;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
            String connectionString = "jdbc:mysql://localhost:3306/soft_uni_simple";
            Connection connection =
                    DriverManager.getConnection(connectionString, "root", "Adamanta5@");
        DataRepository<Employee> repository = new EmployeesDataRepository(connection);
        repository.getAll()
                .stream()
                .map(employee -> employee.getFirstName())
                .forEach(System.out::println);

        repository.insert(new Employee("Gosho", "Goshev"));

        repository.getAll()
                .stream()
                .map(employee -> employee.getFirstName())
                .forEach(System.out::println);
    }
}
