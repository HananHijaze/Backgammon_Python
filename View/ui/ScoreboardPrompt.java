package ui;

import game_engine.Player;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ScoreboardPrompt extends GridPane {

    // Fonts for styling
    private Font scoreFont = Font.font("Arial", 150);
    private Font labelFont = Font.font("Arial", 20);
    private Font fieldFont = Font.font("Arial", 16);

    // Elements for the mode selection
    private String selectedMode;

    // Scoreboard elements
    private TextField blackNameField, whiteNameField, totalGamesField;
    private Label blackLabel, whiteLabel, totalGamesLabel;
    private VBox blackScoreBox, whiteScoreBox, matchScoreBox;
    public ScoreboardPrompt(Player topPlayer, Player bottomPlayer) {
        initStyle();
        initScoreboardComponents();

        // Initialize with player names and scores
        blackNameField.setText(bottomPlayer.getName());
        whiteNameField.setText(topPlayer.getName());
        updateScoreBox(blackScoreBox, bottomPlayer.getScore());
        updateScoreBox(whiteScoreBox, topPlayer.getScore());
    }
    public String getPlayerInput(String fieldType) {
        switch (fieldType.toLowerCase()) {
            case "black":
                return blackNameField.getText();
            case "white":
                return whiteNameField.getText();
            case "score":
                return totalGamesField.getText();
            default:
                throw new IllegalArgumentException("Invalid field type: " + fieldType);
        }
    }

    private void updateScoreBox(VBox scoreBox, int score) {
        Label scoreLabel = (Label) scoreBox.getChildren().get(0); // Assumes the first child is the score label
        scoreLabel.setText(String.valueOf(score));
    }

    public ScoreboardPrompt() {
        initStyle();
        initScoreboardComponents();
        initModeSelection();
    }

    private void initStyle() {
        setAlignment(Pos.CENTER);
        setVgap(10);
        setHgap(10);
    }

    private void initScoreboardComponents() {
        // Scoreboard Labels and Fields
        blackLabel = new Label("Black Player:");
        whiteLabel = new Label("White Player:");
        totalGamesLabel = new Label("Match to:");

        blackNameField = new TextField("Default: Black");
        whiteNameField = new TextField("Default: White");
        totalGamesField = new TextField("11");

        blackLabel.setFont(labelFont);
        whiteLabel.setFont(labelFont);
        totalGamesLabel.setFont(labelFont);

        blackNameField.setFont(fieldFont);
        whiteNameField.setFont(fieldFont);
        totalGamesField.setFont(fieldFont);

        // Score Boxes
        blackScoreBox = createScoreBox("0", Color.BLACK);
        whiteScoreBox = createScoreBox("0", Color.WHITE);
        matchScoreBox = createScoreBox("11", Color.LIGHTGRAY);

        // Adding components to the layout
        add(blackLabel, 0, 0);
        add(blackNameField, 1, 0);
        add(blackScoreBox, 2, 0);

        add(whiteLabel, 0, 1);
        add(whiteNameField, 1, 1);
        add(whiteScoreBox, 2, 1);

        add(totalGamesLabel, 0, 2);
        add(totalGamesField, 1, 2);
        add(matchScoreBox, 2, 2);
    }

    private VBox createScoreBox(String score, Color backgroundColor) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(100, 100);
        box.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));

        Label scoreLabel = new Label(score);
        scoreLabel.setFont(scoreFont);
        scoreLabel.setTextFill(Color.WHITE);

        box.getChildren().add(scoreLabel);
        return box;
    }

    private void initModeSelection() {
        Label label = new Label("Select Game Mode:");
        label.setFont(labelFont);

        ToggleGroup modeGroup = new ToggleGroup();

        RadioButton easyMode = new RadioButton("Easy");
        RadioButton mediumMode = new RadioButton("Medium");
        RadioButton hardMode = new RadioButton("Hard");

        easyMode.setToggleGroup(modeGroup);
        mediumMode.setToggleGroup(modeGroup);
        hardMode.setToggleGroup(modeGroup);

        easyMode.setSelected(true); // Default selection

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            selectedMode = ((RadioButton) modeGroup.getSelectedToggle()).getText();
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        });

        VBox modeBox = new VBox(10, label, easyMode, mediumMode, hardMode, confirmButton);
        modeBox.setAlignment(Pos.CENTER);

        Stage modeStage = new Stage();
        modeStage.initModality(Modality.APPLICATION_MODAL);
        modeStage.setTitle("Mode Selection");
        modeStage.setScene(new Scene(modeBox, 300, 200));
        modeStage.showAndWait();
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public static void main(String[] args) {
        // Example usage: launch a scoreboard prompt with mode selection
        ScoreboardPrompt prompt = new ScoreboardPrompt();
        String mode = prompt.getSelectedMode();
        System.out.println("Selected Mode: " + mode);
    }
}
