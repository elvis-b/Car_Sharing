package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public void createCustomer(String name) {
        String sql = "INSERT INTO CUSTOMER(NAME) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
            System.out.println("The customer was created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM CUSTOMER";
        List<Customer> customers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Integer rentedCarId = resultSet.getInt("RENTED_CAR_ID");
                customers.add(new Customer(id, name, rentedCarId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

}
