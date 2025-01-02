package ui;

import game.GameRecord;
import game_engine.MatchController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class MainMenuView {
    private Stage primaryStage;
    private MatchController matchController;

    public MainMenuView(Stage primaryStage, MatchController matchController) {
        if (primaryStage == null || matchController == null) {
            throw new IllegalArgumentException("PrimaryStage and MatchController cannot be null.");
        }
        this.primaryStage = primaryStage;
        this.matchController = matchController;
    }

    public Scene createScene() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: linear-gradient(to bottom, #6b4423, #8b6914);");

        Button startGameButton = createStyledButton("Start Game");
        Button gameHistoryButton = createStyledButton("Game History");
        Button quitButton = createStyledButton("Quit");

        startGameButton.setTooltip(new Tooltip("Start a new game"));
        gameHistoryButton.setTooltip(new Tooltip("View the history of games"));
        quitButton.setTooltip(new Tooltip("Exit the application"));

        startGameButton.setOnAction(e -> startGame());
        gameHistoryButton.setOnAction(e -> openGameHistory());
        quitButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(startGameButton, gameHistoryButton, quitButton);

        return new Scene(layout, 500, 500);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #2e8b57; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-padding: 10px;");
        return button;
    }

    private void startGame() {
        System.out.println("Start Game button clicked.");
        MatchController gameView = new MatchController(primaryStage);
        Scene gameScene = new Scene(gameView);

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Backgammon Game");
        
        gameView.startGame();
    }



    // Utility method to display an error message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openGameHistory() {
        List<GameRecord> gameHistory = matchController.getGameHistory();
        GameHistoryUI.createAndShowGUI(gameHistory);
    }
}
