import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class Homework {

    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    public static final String MINIONS_TABLE = "minions_db";
    private Connection connection;
    private BufferedReader reader;

    public Homework() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void setConnection(String user, String password) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        connection = DriverManager.getConnection(CONNECTION_STRING + MINIONS_TABLE, properties);
    }

    public void getVillainsNameEx2() throws SQLException {
        String query = "SELECT v.name, COUNT(mv.minion_id) AS 'count'\n" +
                "FROM villains AS v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY v.id\n" +
                "HAVING count > 15\n" +
                "ORDER BY count DESC ;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString(1)
                    , resultSet.getInt(2));

        }

    }

    public void getMinionNamesByVillainIdEx3() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter villain id:");
        int villainId = Integer.parseInt(reader.readLine());

        String villainName = getEntityNameById(villainId, "villains");
        if (villainName == null) {
            System.out.printf("No villain with id: %d", villainId);
            return;
        }
        System.out.printf("Villain: %s%n", villainName);

        String query = "SELECT m.name, m.age FROM minions AS m\n" +
                "JOIN minions_villains mv\n" +
                "ON m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);
        ResultSet resultSet = statement.executeQuery();
        int counter = 1;
        while (resultSet.next()) {
            System.out.printf("%d. %s %d%n",
                    counter++, resultSet.getString("name")
                    , resultSet.getInt("age"));
        }

    }

    private String getEntityNameById(int entityId, String tableName) throws SQLException {

        String query = String.format("SELECT name FROM %s WHERE id = ?", tableName);
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, entityId);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;
    }

    public void addMinionEx4() throws IOException, SQLException {
        System.out.println("Enter minions info: name, age, town name:");
        String[] minionInfo = reader.readLine().split("\\s+");
        String minionName = minionInfo[0];
        int age = Integer.parseInt(minionInfo[1]);
        String townName = minionInfo[2];
        System.out.println("Enter villain name:");
        String villainName = reader.readLine();

        int townId = getEntityIdByName(townName, "towns");

        if (townId < 0) {
            insertEntityInTowns(townName);
            System.out.printf("Town %s was added to the database.%n", townName);
        }
        townId = getEntityIdByName(townName, "towns");

        int villainId = getEntityIdByName(villainName, "villains");
        if (villainId < 0) {
            insertEntityInVillains(villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }
        villainId = getEntityIdByName(villainName, "villains");

        insertEntityToMinions(minionName, age, townId);

        int minionId = getEntityIdByName(minionName, "minions");

        insertEntityToMinionsVillains(villainName, minionName);

        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);

    }

    private void insertEntityToMinionsVillains(String villainName, String minionName) throws SQLException {
        String query = "INSERT INTO minions_villains(minion_id, villain_id) VALUE(?, ?)";
        int villainId = getEntityIdByName(villainName, "villains");
        int minionId = getEntityIdByName(minionName, "minions");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        preparedStatement.executeUpdate();

    }

    private void insertEntityToMinions(String minionName, int age, int townId) throws SQLException {
        String query = "INSERT INTO minions(name, age, town_id) VALUE(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, age);
        preparedStatement.setInt(3, townId);
        preparedStatement.executeUpdate();
    }

    private void insertEntityInVillains(String villainName) throws SQLException {
        String query = "INSERT INTO villains(name, evilness_factor) VALUE(?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, villainName);
        statement.setString(2, "evil");
        statement.executeUpdate();
    }

    private void insertEntityInTowns(String townName) throws SQLException {
        String query = "INSERT INTO towns(name) VALUE(?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, townName);
        statement.executeUpdate();
    }

    private int getEntityIdByName(String entityName, String tableName) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = ?", tableName);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entityName);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    public void changeTownNameCasingEx5() throws IOException, SQLException {
        System.out.println("Enter country name:");

        String countryName = reader.readLine();

        String query = "UPDATE towns SET name = UPPER(name) WHERE country = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        String selectQuery = "SELECT name FROM towns WHERE country = ?";

        PreparedStatement statementForSelect = connection.prepareStatement(selectQuery);
        statementForSelect.setString(1, countryName);
        ResultSet resultSet = statementForSelect.executeQuery();
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString("name").toUpperCase(Locale.ROOT));
        }
        statement.setString(1, countryName);
        int townsAffected = statement.executeUpdate();
        if (townsAffected > 0) {
            System.out.printf("%d town names were affected.%n",
                    townsAffected);
            System.out.println(list);
        } else {
            System.out.println("No town names were affected.");
        }


    }

    public void increaseAgeWithStoreProcedureEx9() throws IOException, SQLException {
        System.out.println("Enter minion id:");
        int minionId = Integer.parseInt(reader.readLine());
        String query = "CALL usp_get_older(?)";
        CallableStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, minionId);
        callableStatement.execute();
    }

    public void increaseMinionsAgeEx8() throws IOException, SQLException {
        System.out.println("Enter minions id's separated with space:");
        String minions = reader.readLine();
        String[] minionsIds = minions.split("\\s+");
        String condition = "id IN(" + String.join(", ", minionsIds) + ")";
        String query = String.format("UPDATE minions\n" +
                "SET age = age + 1, name = CONCAT(LOWER(LEFT(name, 1)), SUBSTR(name, 2))\n" +
                "WHERE %s", condition);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        String result = "SELECT name, age FROM minions";
        PreparedStatement statementForResult = connection.prepareStatement(result);
        ResultSet resultSet = statementForResult.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        }
    }

    public void printAllMinionNamesEx7() throws SQLException {
        List<String> names = new ArrayList<>();
        String query = "SELECT name FROM minions";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            names.add(resultSet.getString("name"));
        }
        for (int i = 0; i < names.size() / 2; i++) {
            System.out.println(names.get(i));
            System.out.println(names.get(names.size() - 1 - i));
        }

    }
}
