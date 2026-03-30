//Joey Barton

/*
Represents a single Driver in the racing simulation
Serves as a data container
*/

public class Driver {

    private final String driverName;
    private final int carNumber;

    public Driver(String name, int carNumber) {
        this.driverName = name;
        this.carNumber = carNumber;
    }

    //Getters
    public String getName() { return this.driverName;}
    public int getCarNumber() { return this.carNumber;}

    @Override
    public String toString() {
        return "Driver: " + driverName + " #" + carNumber;
    }
    
}
