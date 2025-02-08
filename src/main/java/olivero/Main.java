package olivero;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import olivero.ui.MainWindow;

/**
 * Sets up the controller and Graphical views of the application.
 */
public class Main extends Application {

    private final Olivero olivero = new Olivero("data/tasks.txt");
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setOlivero(olivero);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
