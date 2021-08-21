import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        Homework homework = new Homework();

        homework.setConnection("root", "Adamanta5@");

        //homework.getVillainsNameEx2();
        //homework.getMinionNamesByVillainIdEx3();
        //homework.addMinionEx4();
        //homework.changeTownNameCasingEx5();
        //homework.printAllMinionNamesEx7();
        //homework.increaseMinionsAgeEx8();
        //homework.increaseAgeWithStoreProcedureEx9();
    }
}
