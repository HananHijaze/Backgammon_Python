package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class InstructionsView {
    public void show() {
        Stage instructionsStage = new Stage();
        instructionsStage.setTitle("Game Instructions");

        // Gradient background styling
        String gradientStyle = "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #6b4423, #2e8b57);";

        // Layout
        VBox instructionsLayout = new VBox(20);
        instructionsLayout.setAlignment(Pos.TOP_LEFT);
        instructionsLayout.setStyle(gradientStyle);
        instructionsLayout.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("- Instructions -");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.WHITE);

        // TextFlow for formatted instructions
        TextFlow instructionsText = new TextFlow();

        // Adding text with formatting
        instructionsText.getChildren().addAll(
            createBoldText("Hardness Levels:\n"),
            createBoldText("Easy: "), createNormalText("Played with two regular dice.\n"),
            createBoldText("Medium: "), createNormalText("Adds a question die to the easy mode dice.\n"),
            createBoldText("Hard: "), createNormalText(
                "Played with two improved dice and one question die. The player must answer the question correctly to move their pieces.\n\n"),
            createBoldText("Stations:\n"),
            createNormalText(
                "Question Station: In every game, there are three question stations, and their location is random.\n" +
                "Surprise Station: In every game, there is one surprise station. Its location will be selected randomly. " +
                "The player who lands on the surprise station will receive an additional turn.\n\n"),
            createBoldText("Dice:\n"),
            createBoldText("Regular: "), createNormalText(
                "A fair six-sided die, each side has a number 1 to 6.\n"),
            createBoldText("Improved: "), createNormalText(
                "A fair nine-sided die, each side has a number from -3 to 6.\n"),
            createBoldText("Question Die: "), createNormalText(
                "The die has three sides (easy, medium, and hard) representing the question difficulty level.\n")
        );
     // Close button
        Button closeButton = new Button("Close");
        closeButton.setFont(new Font("Arial", 18)); // Increase font size
        closeButton.setTextFill(Color.WHITE);
        closeButton.setStyle("-fx-background-color: #6b4423; -fx-border-color: white; -fx-border-width: 2px; -fx-padding: 10 20;");
        closeButton.setPrefWidth(100); // Set a preferred width for the button
        closeButton.setOnAction(e -> instructionsStage.close());

        // Combine elements
        instructionsLayout.getChildren().addAll(titleLabel, instructionsText, closeButton);
        instructionsLayout.setAlignment(Pos.TOP_CENTER);

        // Increase the window size
        Scene instructionsScene = new Scene(instructionsLayout, 600, 600); // Increased dimensions
        instructionsStage.setScene(instructionsScene);

        // Show stage
        instructionsStage.show();
    }

    private Text createBoldText(String content) {
        Text text = new Text(content);
        text.setFont(new Font("Arial", 18));
        text.setStyle("-fx-font-weight: bold;");
        text.setFill(Color.WHITE);
        return text;
    }

    private Text createNormalText(String content) {
        Text text = new Text(content);
        text.setFont(new Font("Arial", 16));
        text.setFill(Color.WHITE);
        return text;
    }
}