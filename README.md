# Car Sharing

The application was developed as part of the Java Backend Developer track on Hyperskill: https://hyperskill.org/projects/140

This project is a simple car sharing management system implemented in Java. It allows users to manage companies, cars, and customers for a car sharing service. Users can log in as managers to manage companies and cars or as customers to rent and return cars.

Features:
    
    - Manager Functions:
- View company list
- Create a new company
- View cars of a specific company
- Add a new car to a company

----

    - Customer Functions:
- Rent a car
- Return a rented car
- View the rented car

## Design Pattern

The project utilizes the Model-View-Controller (MVC) design pattern to separate concerns and improve maintainability. Additionally, it employs the Data Access Object (DAO) pattern to abstract and encapsulate database operations.

Data Access Object (DAO): Provides an interface to interact with a database. In this project, DAO classes such as CompanyDAO, CarDAO, and CustomerDAO abstract database operations related to their respective entities. These classes encapsulate database queries, hiding the details of data retrieval and manipulation from the rest of the application.
## Configuration

You can configure the database file name by passing the -databaseFileName argument followed by the desired file name when running the program. By default, the database file name is set to carsharing.

## Dependencies

- Gradle: Gradle is used as the build automation tool for the project. It manages project dependencies and builds the project.
- H2 Database: The project uses the H2 in-memory database to store company, car, and customer information. H2 is included as a dependency in the project configuration.
