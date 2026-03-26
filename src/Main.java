import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        
        launch(args);
        
    }

    @Override
    public void start(Stage stage){
        Group root = new Group();
        Scene scene = new Scene(root, 640, 480);

        // The actual paper to draw on
        Canvas canvas = new Canvas(640, 480);
        // Basically the pen that lets me draw.
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add((canvas));

        Car car1 = new Car(0, 0, Color.YELLOW);
        Car car2 = new Car(0, 20, Color.BLUE);
        Car car3 = new Car(0, 40, Color.RED);
        car3.setPlayer();

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.Q && car3.speed < car3.MAX_SPEED) {
                car3.speed += 0.5;
            }
            if (event.getCode() == KeyCode.E && car3.speed > 0) {
                car3.speed -= 0.5;
            }
        });
        stage.setScene(scene);
        stage.show();

        Timer timer = new Timer(cars, graphicsContext);
        timer.start();

        /*
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Game.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
         */
    }
    
}
