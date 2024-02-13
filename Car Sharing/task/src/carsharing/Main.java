package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Main {

        static Scanner scanner = new Scanner(System.in);

        public static void main(String[] args) {
            String databaseFileName = "carsharing";
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-databaseFileName")) {
                    databaseFileName = args[i + 1];
                    break;
                }
            }

            String databaseUrl = "jdbc:h2:./src/carsharing/db/" + databaseFileName;
            DatabaseController dbController = new DatabaseController(databaseUrl);

            dbController.initializeDatabase();

            try (Connection connection = DriverManager.getConnection(databaseUrl)) {
                CompanyDAO companyDAO = new CompanyDAO(connection);
                CarDAO carDAO = new CarDAO(connection);
                CustomerDAO customerDAO = new CustomerDAO(connection);

                int choice;
                do {
                    choice = MenuController.mainMenu();
                    switch (choice) {
                        case 1:
                            handleManagerMenu(companyDAO, carDAO);
                            break;
                        case 2:
                            handleCustomerMainMenu(customerDAO, carDAO, companyDAO);
                            break;
                        case 3:
                            createCustomer(customerDAO);
                            break;
                        case 0:
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } while (choice != 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private static void handleManagerMenu(CompanyDAO companyDAO, CarDAO carDAO) {
            int choice;
            do {
                choice = MenuController.managerMenu();
                switch (choice) {
                    case 1:
                        showCompanyList(companyDAO, carDAO);
                        break;
                    case 2:
                        createCompany(companyDAO);
                        break;
                    case 0:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);
        }

    private static void showCompanyList(CompanyDAO companyDAO, CarDAO carDAO) {
        List<Company> companies = companyDAO.getAllCompanies();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }

        // Display the company list
        System.out.println();
        System.out.println("Choose a company:");
        Map<Integer, Company> companyMap = new HashMap<>();
        int index = 1;
        for (Company company : companies) {
            System.out.println(index + ". " + company.getName());
            companyMap.put(index, company);
            index++;
        }
        System.out.println("0. Back");

        // Prompt user for choice
        int choice = scanner.nextInt();
        if (choice == 0) {
            return;
        }

        // Check if the choice is valid
        if (companyMap.containsKey(choice)) {
            Company chosenCompany = companyMap.get(choice);
            System.out.println();
            System.out.println("'" + chosenCompany.getName() + "' company:");
            handleCompanyMenu(chosenCompany, carDAO);
        } else {
            System.out.println("Invalid choice. Please choose a valid company.");
        }
    }

    private static void handleCompanyMenu(Company company, CarDAO carDAO) {
        int choice;
        do {
            choice = MenuController.companyMenu();
            switch (choice) {
                case 1:
                    displayCarList(company, carDAO);
                    break;
                case 2:
                    createCar(company, carDAO);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    public static void displayCarList(Company company, CarDAO carDAO) {
        List<Car> cars = carDAO.getCarsByCompany(company.getId());
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            System.out.println();
        } else {
            System.out.println("Car list:");
            int index = 1;
            for (Car car : cars) {
                System.out.println(index + ". " + car.getName());
                index++;
            }
        }
    }


    private static void createCar(Company company, CarDAO carDAO) {
        System.out.println();
        String name;
        do {
            System.out.println("Enter the car name:");
            name = scanner.nextLine().trim(); // Trim leading and trailing whitespace
            if (name.isEmpty()) {
                System.out.println("Car name cannot be empty. Please try again.");
            }
        } while (name.isEmpty());

        carDAO.createCar(name, company.getId());
        System.out.println("The car was added!");
        System.out.println();
    }

    private static void createCompany(CompanyDAO companyDAO) {
            String name = MenuController.readCompanyName();
            companyDAO.createCompany(name);
        }

    private static void createCustomer(CustomerDAO customerDAO) {
        System.out.println();
        String name;
        do {
            System.out.println("Enter the customer name:");
            name = scanner.nextLine().trim(); // Trim leading and trailing whitespace
            if (name.isEmpty()) {
                System.out.println("The customer name cannot be empty. Please try again.");
            }
        } while (name.isEmpty());

        customerDAO.createCustomer(name);
        System.out.println("The customer was added!");
        System.out.println();
    }

    private static void handleCustomerMainMenu(CustomerDAO customerDAO, CarDAO carDAO, CompanyDAO companyDAO) {
        // Display the list of customers
        List<Customer> customers = customerDAO.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }
        System.out.println();
        System.out.println("Customer list:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).getName());
        }
        System.out.println("0. Back");

        // Prompt user to choose a customer
        int customerChoice = scanner.nextInt();
        if (customerChoice == 0) {
            return;
        }
        // Validate customer choice
        if (customerChoice < 0 || customerChoice > customers.size()) {
            System.out.println("Invalid choice. Please choose a valid customer.");
            return;
        }

        // Get the selected customer
        Customer selectedCustomer = customers.get(customerChoice-1);

        // Call handleCustomerMenu with the selected customer
        handleCustomerMenu(selectedCustomer, customerDAO, carDAO, companyDAO);
    }


    private static void handleCustomerMenu(Customer selectedCustomer, CustomerDAO customerDAO, CarDAO carDAO, CompanyDAO companyDAO) {
        int choice;
        do {
            choice = MenuController.customerMenu();
            switch (choice) {
                case 1:
                    rentCar(selectedCustomer, customerDAO, carDAO, companyDAO);
                    break;
                case 2:
                    returnRentedCar(selectedCustomer, carDAO);
                    break;
                case 3:
                    displayRentedCar(selectedCustomer, carDAO, companyDAO);
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void rentCar(Customer selectedCustomer, CustomerDAO customerDAO, CarDAO carDAO, CompanyDAO companyDAO) {
        List<Company> companies = companyDAO.getAllCompanies();
        if (selectedCustomer.getRentedCarId() != null && selectedCustomer.getRentedCarId() != 0) {
            System.out.println("You've already rented a car!");
            return;
        }

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }

        // Display the list of companies
        System.out.println();
        System.out.println("Choose a company:");
        for (int i = 0; i < companies.size(); i++) {
            System.out.println((i + 1) + ". " + companies.get(i).getName());
        }
        System.out.println("0. Back");

        // Prompt user to choose a company
        int companyChoice = scanner.nextInt();
        if (companyChoice == 0) {
            return;
        }

        if (companyChoice < 0 || companyChoice > companies.size()) {
            System.out.println("Invalid choice. Please choose a valid company.");
            return;
        }

        Company selectedCompany = companies.get(companyChoice - 1);

        // Get cars available for the selected company
        List<Car> availableCars = carDAO.getAvailableCarsByCompany(selectedCompany.getId());
        if (availableCars.isEmpty()) {
            System.out.println("No available cars in the '" + selectedCompany.getName() + "' company.");
            return;
        }

        // Display available cars
        System.out.println();
        System.out.println("Choose a car:");
        for (int i = 0; i < availableCars.size(); i++) {
            System.out.println((i + 1) + ". " + availableCars.get(i).getName());
        }
        System.out.println("0. Back");

        // Prompt user to choose a car
        int carChoice = scanner.nextInt();
        if (carChoice == 0) {
            return;
        }

        // Validate car choice
        if (carChoice < 0 || carChoice > availableCars.size()) {
            System.out.println("Invalid choice. Please choose a valid car.");
            return;
        }

        Car selectedCar = availableCars.get(carChoice - 1);
        // Rent the selected car to the customer
        carDAO.rentCarToCustomer(selectedCar.getId(), selectedCustomer.getId());
        selectedCustomer.setRentedCarId(selectedCar.getId());
        System.out.println();
        // After attempting to rent a new car
        System.out.println("Selected customer rented car ID after renting: " + selectedCustomer.getRentedCarId());
        System.out.println("You rented '" + selectedCar.getName() + "'");
    }


    private static void returnRentedCar(Customer selectedCustomer, CarDAO carDAO) {
        if (selectedCustomer.getRentedCarId() == null) {
            System.out.println();
            System.out.println("You didn't rent a car!");
            return;
        }

        // Check if the customer has actually rented a car
        Car rentedCar = carDAO.getCarById(selectedCustomer.getRentedCarId());
        if (rentedCar == null) {
            System.out.println();
            System.out.println("You didn't rent a car!");
            return;
        }

        // Return the rented car
        carDAO.returnCarFromCustomer(selectedCustomer.getId());
        System.out.println();
        System.out.println("You've returned a rented car!");
    }


    private static void displayRentedCar(Customer selectedCustomer, CarDAO carDAO, CompanyDAO companyDAO) {
        Integer rentedCarId = selectedCustomer.getRentedCarId();

        if (rentedCarId == null) {
            System.out.println("You didn't rent a car!");
            return;
        }

        Car rentedCar = carDAO.getCarById(rentedCarId);
        if (rentedCar == null) {
            System.out.println("You didn't rent a car!");
            return;
        }

        Company company = companyDAO.getCompanyByCarId(rentedCarId);
        System.out.println();
        System.out.println("Your rented car:");
        System.out.println(rentedCar.getName());
        System.out.println("Company:");
        System.out.println(company.getName());
    }
}