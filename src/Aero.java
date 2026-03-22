public class Aero {

    public static final int MIN_RATING = 60;
    public static final int MAX_RATING = 80;

    private final int rating;

    public Aero(int rating) {

        //NEEDS A PROPER THROWS
        if(rating < MIN_RATING || rating > MAX_RATING) {
            System.out.println("ERROR");
        }
        this.rating = rating;
    }
    
    public int getRating() { return this.rating;}
}
