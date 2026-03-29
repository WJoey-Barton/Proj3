//Joey Barton

/*
Implements the interface PerformanceComponent
Holds the rating value for the Engine component
*/

public class Engine implements PerformanceComponent{

    private final double rating;

    public Engine(double rating) {
        
        //NEEDS A PROPER THROWS
        if(rating < MIN_RATING || rating > MAX_RATING) {
            System.out.println("ERROR");
        }
        this.rating = rating;
    }

    public double getRating() { return this.rating;}
    
}
