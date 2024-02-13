package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.*;

public class CompanyDAO {
    private Connection connection;

    public CompanyDAO(Connection connection) {
        this.connection = connection;
    }

    public void createCompany(String name) {
        String sql = "INSERT INTO COMPANY(NAME) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
            System.out.println("The company was created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Company> getAllCompanies() {
        String sql = "SELECT * FROM COMPANY";
        List<Company> companies = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                companies.add(new Company(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
        }

    public Company getCompanyByCarId(int carID) {
        String sql = "SELECT c.* FROM COMPANY c JOIN CAR ca ON c.ID = ca.COMPANY_ID WHERE ca.ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("NAME");
                    return new Company(id, name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
