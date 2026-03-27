public class Tire {

    public static final double MIN_RATING = 6.0;
    public static final double MAX_RATING = 8.0;

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
