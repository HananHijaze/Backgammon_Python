package ui;

import game_engine.MatchController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {

    private Stage primaryStage;

    public MainMenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createScene() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        
        Button startGameButton = new Button("Start Game");
        Button gameHistoryButton = new Button("Games History");
        Button quitButton = new Button("Quit");

        // Event Handlers
        startGameButton.setOnAction(e -> startGame());
        gameHistoryButton.setOnAction(e -> openGameHistory());
        quitButton.setOnAction(e -> primaryStage.close());

        // Add buttons to the layout
        layout.getChildren().addAll(startGameButton, gameHistoryButton, quitButton);

        return new Scene(layout, 500, 500);
    }

    private void startGame() {
        System.out.println("Start Game button clicked.");
        MatchController gameView = new MatchController(primaryStage);
        Scene gameScene = new Scene(gameView);

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Backgammon Game");
        
        gameView.startGame();

    }

    private void openGameHistory() {
        System.out.println("Game History button clicked.");
        // TODO: Implement game history view logic
    }
}
