package olivero.ui;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import olivero.Olivero;
import olivero.commands.CommandResult;
import olivero.exceptions.CommandExecutionException;

/**
 * Represents the main Gui for displaying chat messages.
 */
public class MainWindow extends AnchorPane {

    private static final int EXIT_DELAY_SECONDS = 1;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Olivero olivero;
    private final Image userImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/pingu.png")));
    private final Image oliveroImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/pingu_angry.png")));

    /** true if the current window is exiting, false otherwise */
    private boolean isExiting = false;



    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setOlivero(Olivero olivero) {
        this.olivero = olivero;
        greet();
    }



    private void greet() {
        dialogContainer
                .getChildren()
                .add(DialogBox.getOliveroDialog(this.olivero.getGreetingMessage(), oliveroImage));
    }
    @FXML
    private void handleUserInput() {
        // skip if exit is in progress
        if (this.isExiting) {
            return;
        }

        String input = userInput.getText();
        String response;
        try {
            CommandResult result = olivero.runCommand(input);
            response = result.getMessage();
            if (result.isExit()) {
                // code to exit application
                this.isExiting = true;

                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> Platform.runLater(Platform::exit),
                        EXIT_DELAY_SECONDS, TimeUnit.SECONDS);
                scheduler.shutdown();

            }
        } catch (CommandExecutionException e) {
            response = e.getMessage();
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getOliveroDialog(response, oliveroImage)
        );
        userInput.clear();
    }


}
