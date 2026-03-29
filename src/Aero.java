//Joey Barton

/*
Implements the interface PerformanceComponent
Holds the rating value for the Aero component.
*/

public class Aero implements PerformanceComponent{

    private final double rating;

    public Aero(double rating) {

        //NEEDS A PROPER THROWS
        if(rating < MIN_RATING || rating > MAX_RATING) {
            System.out.println("ERROR");
        }
        this.rating = rating;
    }
    
    public double getRating() { return this.rating;}
}
