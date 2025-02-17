package olivero.ui;

import java.util.Objects;

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

    private static final String USER_IMAGE_PATH = "/images/pingu.png";
    private static final String OLIVERO_IMAGE_PATH = "/images/pingu_angry.png";

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
            Objects.requireNonNull(this.getClass().getResourceAsStream(USER_IMAGE_PATH)));
    private final Image oliveroImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(OLIVERO_IMAGE_PATH)));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setupOlivero(Olivero olivero) {
        this.olivero = olivero;
        setupAndGreet();
    }

    private void setupAndGreet() {
        String initialMessage = this.olivero.setupResources();
        dialogContainer
                .getChildren()
                .add(DialogBox.getOliveroDialog(initialMessage, oliveroImage));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        userInput.clear();
        String response;
        try {
            CommandResult result = olivero.runCommand(input);
            response = result.getMessage();

            // close the platform if the command exits
            if (result.isExit()) {
                Platform.exit();
                return;
            }
        } catch (CommandExecutionException e) {
            response = e.getMessage();
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getOliveroDialog(response, oliveroImage)
        );
    }

}
