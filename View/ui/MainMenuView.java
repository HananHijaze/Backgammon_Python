package ui;

import game_engine.MatchController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuView {

    private Stage primaryStage;

    public MainMenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createScene() {
        VBox layout = new VBox(15); // Vertical alignment with spacing
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: linear-gradient(to bottom, #6b4423, #8b6914);");

        // Create buttons with styles
        Button startGameButton = createStyledButton("Start Game");
        Button gameHistoryButton = createStyledButton("Games History");
        Button quitButton = createStyledButton("Quit");

        // Add tooltips for better UX
        startGameButton.setTooltip(new Tooltip("Start a new game"));
        gameHistoryButton.setTooltip(new Tooltip("View the history of games"));
        quitButton.setTooltip(new Tooltip("Exit the application"));

        // Event Handlers
        startGameButton.setOnAction(e -> startGame());
        gameHistoryButton.setOnAction(e -> openGameHistory());
        quitButton.setOnAction(e -> confirmQuit());

        // Add buttons to the layout
        layout.getChildren().addAll(startGameButton, gameHistoryButton, quitButton);

        return new Scene(layout, 500, 500); // Scene with a fixed size
    }

    /**
     * Creates a styled button with hover effects.
     *
     * @param text The text to display on the button.
     * @return A styled Button object.
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle(
            "-fx-background-color: #2e8b57; " + // Default green background
            "-fx-border-color: #ffffff; " +     // White border
            "-fx-border-width: 2px; " +
            "-fx-padding: 10px; " +
            "-fx-cursor: hand;"
        );

        // Hover effects
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: #3cb371; " + // Lighter green on hover
            "-fx-border-color: #ffffff; " +
            "-fx-border-width: 2px; " +
            "-fx-padding: 10px; " +
            "-fx-cursor: hand;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: #2e8b57; " + // Return to default green
            "-fx-border-color: #ffffff; " +
            "-fx-border-width: 2px; " +
            "-fx-padding: 10px; " +
            "-fx-cursor: hand;"
        ));

        return button;
    }

    /**
     * Handles the Start Game button click event.
     */
    private void startGame() {
        MatchController gameView = new MatchController(primaryStage);
        Scene gameScene = new Scene(gameView);

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Backgammon Game");
        gameView.startGame();
    }

    /**
     * Handles the Game History button click event.
     */
    private void openGameHistory() {
        System.out.println("Game History button clicked.");
        // TODO: Implement the game history view logic
    }

    /**
     * Handles the Quit button click event.
     */
    private void confirmQuit() {
        System.out.println("Quit button clicked. Application will close.");
        primaryStage.close(); // Close the application
    }
}
