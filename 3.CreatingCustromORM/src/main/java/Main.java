
import entity.User;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/fsd";
    private static final String USER = "root";
    private static final String PASSWORD = "Adamanta5@";

    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Connection connection = getConnection();
        EntityManager<User> entityManager = new EntityManager<>(connection);

        System.out.println("Connected to database.");

        User user = new User();
//        user.setId(6);
        user.setUsername("8888");
        user.setPassword("MyPass34");
        user.setAge(22);
        user.setRegistrationDate(LocalDate.of(2000,11,1));

        entityManager.persist(user);

    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
    }

    private static <T> String listToString(List<T> list) {
        return list.stream()
                .map(T::toString).collect(Collectors.joining("\n"));
    }
}