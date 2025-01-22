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
        ImageView instructionsIcon = createInstructionsIcon();
        instructionsIcon.setOnMouseClicked(e -> showInstructions());


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

    private void showInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Instructions");
        alert.setHeaderText(null);
        alert.setContentText("Welcome to the game! Here's how to play...");
        alert.showAndWait();
    }

    private void openGameHistory() {
        System.out.println("Game History button clicked.");
        List<GameRecord> gameHistory = matchController.getGameHistory();
        GameHistoryUI.createAndShowGUI(gameHistory);    }
  
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
    private void showInstructions() {
        // Create a new Stage for the instructions
        Stage instructionsStage = new Stage();
        instructionsStage.setTitle("Game Instructions");

        // Gradient background styling
        String gradientStyle = "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #6b4423, #2e8b57);";

        // Create a VBox layout for the instructions content
        VBox instructionsLayout = new VBox(20);
        instructionsLayout.setAlignment(Pos.TOP_LEFT);
        instructionsLayout.setStyle(gradientStyle);
        instructionsLayout.setPadding(new Insets(20));

        // Title label
        Label titleLabel = new Label("- Instructions -");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.WHITE);

        // Create a TextFlow for rich-text formatting
        TextFlow instructionsText = new TextFlow();

        // Add formatted text
        Text hardnessTitle = new Text("Hardness Levels:\n");
        hardnessTitle.setFont(new Font("Arial", 18));
        hardnessTitle.setFill(Color.WHITE);
        hardnessTitle.setStyle("-fx-font-weight: bold;");

        Text hardnessDetails = new Text(
            "Easy: ");
        hardnessDetails.setStyle("-fx-font-weight: bold;");
        hardnessDetails.setFont(new Font("Arial", 16));
        hardnessDetails.setFill(Color.WHITE);

        Text easyDescription = new Text("Played with two regular dice.\n");
        easyDescription.setFont(new Font("Arial", 16));
        easyDescription.setFill(Color.WHITE);

        Text medium = new Text("Medium: ");
        medium.setStyle("-fx-font-weight: bold;");
        medium.setFont(new Font("Arial", 16));
        medium.setFill(Color.WHITE);

        Text mediumDescription = new Text("Adds a question die to the easy mode dice.\n");
        mediumDescription.setFont(new Font("Arial", 16));
        mediumDescription.setFill(Color.WHITE);

        Text hard = new Text("Hard: ");
        hard.setStyle("-fx-font-weight: bold;");
        hard.setFont(new Font("Arial", 16));
        hard.setFill(Color.WHITE);

        Text hardDescription = new Text(
            "Played with two improved dice and one question die. The player must answer the question correctly to move their pieces.\n\n");
        hardDescription.setFont(new Font("Arial", 16));
        hardDescription.setFill(Color.WHITE);

        Text stationsTitle = new Text("Stations:\n");
        stationsTitle.setFont(new Font("Arial", 18));
        stationsTitle.setFill(Color.WHITE);
        stationsTitle.setStyle("-fx-font-weight: bold;");

        Text stationsDetails = new Text(
            "Question Station: In every game, there are three question stations, and their location is random.\n"
                + "Surprise Station: In every game, there is one surprise station, and its location is selected randomly. The player who lands on the surprise station will receive an additional turn.\n\n");
        stationsDetails.setFont(new Font("Arial", 16));
        stationsDetails.setFill(Color.WHITE);

        Text diceTitle = new Text("Dice:\n");
        diceTitle.setFont(new Font("Arial", 18));
        diceTitle.setFill(Color.WHITE);
        diceTitle.setStyle("-fx-font-weight: bold;");

        Text diceDetails = new Text(
            "Regular: A fair six-sided die, each side has a number 1 to 6.\n"
                + "Improved: A fair nine-sided die, each side has a number from -3 to 6.\n"
                + "Question Die: The die has three sides (easy, medium, and hard) representing the question difficulty levels.\n");
        diceDetails.setFont(new Font("Arial", 16));
        diceDetails.setFill(Color.WHITE);

        // Add all formatted text to the TextFlow
        instructionsText.getChildren().addAll(
            hardnessTitle, hardnessDetails, easyDescription,
            medium, mediumDescription,
            hard, hardDescription,
            stationsTitle, stationsDetails,
            diceTitle, diceDetails
        );

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setFont(new Font("Arial", 16));
        closeButton.setTextFill(Color.WHITE);
        closeButton.setStyle("-fx-background-color: #6b4423; -fx-border-color: white; -fx-border-width: 2px;");
        closeButton.setOnAction(e -> instructionsStage.close());

        // Layout the components
        instructionsLayout.getChildren().addAll(titleLabel, instructionsText, closeButton);
        instructionsLayout.setAlignment(Pos.TOP_CENTER);

        // Create a Scene and set it to the stage
        Scene instructionsScene = new Scene(instructionsLayout, 500, 500);
        instructionsStage.setScene(instructionsScene);

        // Show the Stage
        instructionsStage.show();
    }


}
