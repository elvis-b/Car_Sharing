package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {
    private String databaseUrl;

    public DatabaseController(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            connection.setAutoCommit(true);

            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS COMPANY (" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR(255) UNIQUE NOT NULL)");
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS CAR (" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR(255) UNIQUE NOT NULL," +
                        "COMPANY_ID INT NOT NULL," +
                        "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))");
            }

            try(Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR(255) UNIQUE NOT NULL," +
                        "RENTED_CAR_ID INT," +
                        "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))");
            }
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
