package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Singleton {
    private static Singleton instance;
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/chu_db?useSSL=false&serverTimezone=UTC";
    private final String username = "root"; 
    private final String password = "";    

    private Singleton() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC non trouvé.", e);
        }
    }

    public static Singleton getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            synchronized (Singleton.class) {
                if (instance == null || instance.getConnection().isClosed()) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            throw new IllegalStateException("La connexion est null.");
        }
        return connection;
    }
}
