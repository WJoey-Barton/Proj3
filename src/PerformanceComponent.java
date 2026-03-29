//Joey Barton

/*
Defines the interface for all vehicle performance components.
All performance components implement this interface
to provide a rating used in the simulation's speed physics calculations.
*/

public interface PerformanceComponent {

    //Minimum possible rating a component can have
    double MIN_RATING = 5.3;

    //Maximum possible rating a component can have
    double MAX_RATING = 8.0;

    //Retrieves the current performance rating of the component.
    double getRating();
    
}
