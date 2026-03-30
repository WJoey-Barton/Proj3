//Joey Barton

/*
Implements the interface PerformanceComponent
Holds the rating value for the Tire component.
*/

public class Tire implements PerformanceComponent{

    private final double rating;

    public Tire(double rating) {

        //NEEDS A PROPER THROWS
        if(rating < MIN_RATING || rating > MAX_RATING) {
            System.out.println("ERROR");
        }
        this.rating = rating;
    }

    public double getRating() { return this.rating;}
    
}
