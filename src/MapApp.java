
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class MapApp extends Application {

    private static final double MAP_WIDTH = 1280;
    private static final double MAP_HEIGHT = 720;
    private static final int NUM_AIRPORTS = 10;
    private int nameAirport = 0;
    private Image mapImage;
    private PixelReader pixelReader;

    @Override
    public void start(Stage primaryStage) {
        mapImage = new Image("file:src/images/map.png");  // Ruta de la imagen del mapa
        pixelReader = mapImage.getPixelReader();

        Canvas canvas = new Canvas(MAP_WIDTH, MAP_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el mapa como fondo
        drawMap(gc);

        // Generar ubicaciones aleatorias y colocar aeropuertos
        Random random = new Random();
        for (int i = 0; i < NUM_AIRPORTS; i++) {
            double x, y;
            boolean isOnLand;

            do {
                x = random.nextDouble() * MAP_WIDTH;
                y = random.nextDouble() * MAP_HEIGHT;

                Color pixelColor = pixelReader.getColor((int) x, (int) y);
                double hue = pixelColor.getHue();
                double saturation = pixelColor.getSaturation();
                double brightness = pixelColor.getBrightness();

                boolean isGreen = hue >= 60 && hue <= 180 && saturation >= 0.3 && brightness >= 0.3;
                isOnLand = isGreen; // Si el color no está en el rango de tonos de verde, no está en el mar
            } while (!isOnLand);

            // Obtener las coordenadas de latitud y longitud
            double latitude = convertYToLatitude(y);
            double longitude = convertXToLongitude(x);

            AirPort airport = new AirPort(("Aeropuerto " ), 10); // Puedes reemplazar "Ubicación" por la ubicación real del aeropuerto
            airport.setLatitude(latitude);
            airport.setLongitude(longitude);
            // Dibujar el aeropuerto
            drawAirport(gc, x, y,("Aeropuerto " + i));

            System.out.println(("Aeropuerto " + nameAirport) + (i + 1) + ": Latitud = " + latitude + ", Longitud = " + longitude);
            StackPane root = new StackPane(canvas);
            Scene scene = new Scene(root, MAP_WIDTH, MAP_HEIGHT);

            primaryStage.setTitle("Map App");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    private void drawMap(GraphicsContext gc) {
        // Dibujar el mapa como fondo
        gc.drawImage(mapImage, 0, 0, MAP_WIDTH, MAP_HEIGHT);

        // Dibujar elementos adicionales del mapa (carreteras, fronteras, etc.)
        // ...
    }

    private void drawAirport(GraphicsContext gc, double x, double y, String location) {
        // Dibujar el aeropuerto
        gc.setFill(Color.RED);
        gc.fillOval(x - 5, y - 5, 10, 10);

        // Agregar la ubicación encima del aeropuerto
        gc.setFill(Color.BLACK);
        gc.fillText(location, x-35, y - 10);
    }

    private double convertYToLatitude(double y) {
        // Convertir la coordenada Y del clic al valor de latitud correspondiente
        double latitudeRange = 90.0; // Rango de latitudes posibles (-90 a 90)
        return (y / MAP_HEIGHT) * latitudeRange - latitudeRange / 2.0;
    }

    private double convertXToLongitude(double x) {
        // Convertir la coordenada X del clic al valor de longitud correspondiente
        double longitudeRange = 180.0; // Rango de longitudes posibles (-180 a 180)
        return (x / MAP_WIDTH) * longitudeRange - longitudeRange / 2.0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
