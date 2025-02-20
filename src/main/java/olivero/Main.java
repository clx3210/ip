package olivero;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import olivero.ui.MainWindow;

/**
 * Sets up the controller and graphical views of the application.
 */
public class Main extends Application {

    private static final String SAVE_FILE_PATH = "data/tasks.txt";

    private static final String RESOURCE_PATH = "/view/MainWindow.fxml";

    private final Olivero olivero = new Olivero(SAVE_FILE_PATH);
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(RESOURCE_PATH));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setUpOlivero(olivero);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
