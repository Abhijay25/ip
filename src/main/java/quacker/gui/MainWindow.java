package quacker.gui;
import javafx.application.Platform;
import quacker.Quacker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Quacker quacker;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image quackerImage = new Image(this.getClass().getResourceAsStream("/images/DaQuacker.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        
        String welcome = "Hello, I'm Quacker! How Can I Help You?";
        dialogContainer.getChildren().addAll(
                DialogBox.getQuackerDialog(welcome, quackerImage)
        );
    }

    /** Injects the Quacker instance */
    public void setQuacker(Quacker quackerInstance) {
        quacker = quackerInstance;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Quacker's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = quacker.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getQuackerDialog(response, quackerImage)
        );
        userInput.clear();
        
        if (input.toLowerCase().equals("bye")) {
            Platform.exit();
        }
    }
}

