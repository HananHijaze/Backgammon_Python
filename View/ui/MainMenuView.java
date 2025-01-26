package ui;

import java.util.List;

import game.GameRecord;
import game_engine.MatchController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

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

        // Buttons
        Button startGameButton = createStyledButton("Start Game");
        Button gameHistoryButton = createStyledButton("Game History");
        Button questionListButton = createStyledButton("Question List");
        Button quitButton = createStyledButton("Quit");

        // Tooltips
        startGameButton.setTooltip(new Tooltip("Start a new game"));
        gameHistoryButton.setTooltip(new Tooltip("View the history of games"));
        questionListButton.setTooltip(new Tooltip("View the list of questions"));
        quitButton.setTooltip(new Tooltip("Exit the application"));

        // Event Handlers
        startGameButton.setOnAction(e -> startGame());
        gameHistoryButton.setOnAction(e -> openGameHistory());
        questionListButton.setOnAction(e -> openQuestionList());
        quitButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(startGameButton, gameHistoryButton, questionListButton, quitButton);

      


        // Add Instructions Icon
        Label instructionsIcon = createInstructionsIcon();
        instructionsIcon.setOnMouseClicked(e -> showInstructions());


        StackPane root = new StackPane(layout, instructionsIcon);
        StackPane.setAlignment(instructionsIcon, Pos.TOP_RIGHT);

        return new Scene(root, 500, 500);

    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #2e8b57; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-padding: 10px;");
        return button;
    }


    private Label createInstructionsIcon() {
        Label icon = new Label("\u2139"); // Unicode for "info" symbol
        icon.setFont(new Font("Arial", 40)); // Increase font size
        icon.setTextFill(Color.WHITE); // Set the color to white
        icon.setStyle("-fx-cursor: hand; -fx-padding: 10;"); // Add padding for spacing
        return icon;
    }




 
    private void startGame() {
        System.out.println("Start Game button clicked.");
        MatchController gameView = new MatchController(primaryStage);
        Scene gameScene = new Scene(gameView);

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Backgammon Game");
        
        gameView.startGame();
    }
    private void openQuestionList() {
        // Open the QuestionTableView to show the question list
        QuestionTableView questionTableView = new QuestionTableView();
        try {
            Stage questionListStage = new Stage();
            questionTableView.start(questionListStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   

    private void openGameHistory() {
        System.out.println("Game History button clicked.");
        List<GameRecord> gameHistory = matchController.getGameHistory();
        GameHistoryUI.createAndShowGUI(gameHistory);    }
  
   
    private void showInstructions() {
        InstructionsView instructionsView = new InstructionsView();
        instructionsView.show();
    }
    private void styleButtons(Button button) {
        button.setFont(new Font("Arial", 16)); // Set the font and size
        button.setTextFill(Color.WHITE); // Set the text color
        button.setStyle("-fx-background-color: #6b4423; " // Set the background color
                       + "-fx-border-color: white; " // Add a white border
                       + "-fx-border-width: 2px; " // Border thickness
                       + "-fx-background-radius: 5px; " // Rounded corners
                       + "-fx-padding: 5px 15px;"); // Padding for button
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #8c5a2b; "
                                                    + "-fx-border-color: white; "
                                                    + "-fx-border-width: 2px; "
                                                    + "-fx-background-radius: 5px; "
                                                    + "-fx-padding: 5px 15px;")); // Hover effect
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #6b4423; "
                                                   + "-fx-border-color: white; "
                                                   + "-fx-border-width: 2px; "
                                                   + "-fx-background-radius: 5px; "
                                                   + "-fx-padding: 5px 15px;")); // Default style
    }


}