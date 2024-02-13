package carsharing;


public class Customer {
    private int id;
    private String name;

    private Integer rentedCarId;

    public Customer(int id, String name, Integer rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(Integer rentedCarId) {
        this.rentedCarId = rentedCarId;
    }
}
