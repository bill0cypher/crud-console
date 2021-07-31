package bootstrap;

import java.sql.SQLException;


public class Bootstrap {
    public static void init() {
        buildSessionFactory();
        establishDatabaseConnection();
        initCommandLineInstructions();
    }

    private static void buildSessionFactory() {
        SessionRunner.initSessionFactory();
    }
    private static void establishDatabaseConnection() {
        try {
            JDBCConnector.connect("jdbc:postgresql://localhost:5432/library", "root", "1111");
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
