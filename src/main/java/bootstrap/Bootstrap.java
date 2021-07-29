package bootstrap;

import java.sql.SQLException;


public class Bootstrap {
    public static void init() {
        establishDatabaseConnection();
        initCommandLineInstructions();
    }

    private static void establishDatabaseConnection() {
        try {
            JDBCConnector.connect("", "root", "1111");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void initCommandLineInstructions() {
        CommandLineControl.init(System.in);
    }
}
