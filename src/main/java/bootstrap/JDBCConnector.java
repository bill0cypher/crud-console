package bootstrap;

import java.sql.*;
import java.util.Optional;

public class JDBCConnector {
    private String url;
    private String username;
    private String password;
    private static Connection connection;

    private JDBCConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static Connection connect(String url, String username, String password) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        Optional.of(connection).orElseThrow(() -> new SQLException("Connection is broken or doesn't exist")).close();
    }

    public static Statement createStatement() throws SQLException {
        return Optional.of(JDBCConnector.getConnection().createStatement()).orElseThrow(() -> new SQLException(""));
    }

    public static PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return Optional.of(JDBCConnector.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).orElseThrow(SQLClientInfoException::new);
    }
}
