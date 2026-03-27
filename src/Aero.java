public class Aero {

    public static final double MIN_RATING = 6.0;
    public static final double MAX_RATING = 8.0;

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
