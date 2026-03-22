import java.util.List;
import java.util.Random;

public class RaceController {

    /*
        countdownLights goes:
        0-4 = all lights lit (one per LIGHTS_INTERVAL)
        5 = lights out, race begins
    */
    private int countdownLights = 0;

    //800 ms
    private static final int LIGHTS_INTERVAL = 800;

    private Track track;
    private Race race;
    private List<Sector> sectorList;

    //There will be 3 AIControllers
    private AIController[] AIController;

    private final Random rand = new Random();

    private static final int NUM_CARS = 4;

    public RaceController() {
        track = new Track("Oval", sectorList);
    }


    
}
