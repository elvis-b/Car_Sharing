package carsharing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private Connection connection;

    public CarDAO(Connection connection) {
        this.connection = connection;
    }

    public void createCar(String name, int companyID) {
        String sql = "INSERT INTO CAR(NAME, COMPANY_ID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, companyID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getCarsByCompany(int companyID) {
        String sql = "SELECT * FROM CAR WHERE COMPANY_ID = ? ORDER BY ID";
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, companyID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("NAME");
                    cars.add(new Car(id, name, companyID));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getAvailableCarsByCompany(int companyID) {
        String sql = "SELECT * FROM CAR WHERE COMPANY_ID = ? AND ID NOT IN (SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL)";
        List<Car> availableCars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, companyID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("NAME");
                    availableCars.add(new Car(id, name, companyID));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableCars;
    }

    public void rentCarToCustomer(int carID, int customerID) {
        String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carID);
            statement.setInt(2, customerID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnCarFromCustomer(int customerID) {
        String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Car getCarById(int carID) {
        String sql = "SELECT * FROM CAR WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("NAME");
                    int companyId = resultSet.getInt("COMPANY_ID");
                    return new Car(carID, name, companyId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
