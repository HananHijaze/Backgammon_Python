package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QuestionTypeSelectionDialog {

    private String selectedQuestionType;

    public String showAndWait(Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Select Question Type");

        // Gradient background
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ad1111")), // Red
                new Stop(1, Color.web("#0a0a0a"))  // Black
        );

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // Label for question type selection
        Label label = new Label("Choose a question type:");
        label.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");
        label.setFont(Font.font("Arial", 18));

        // Radio buttons for question types
        ToggleGroup questionTypeGroup = new ToggleGroup();
        RadioButton softwareEngineeringBtn = createStyledRadioButton("Software Engineering", questionTypeGroup);
        RadioButton informationRetrievalBtn = createStyledRadioButton("Information Retrieval", questionTypeGroup);
        RadioButton artificialIntelligenceBtn = createStyledRadioButton("Artificial Intelligence", questionTypeGroup);

        // Default selection
        softwareEngineeringBtn.setSelected(true);

        // Confirm Button
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setFont(Font.font("Arial", 16));
        confirmBtn.setStyle("-fx-background-color: #0a0a0a; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;");
        confirmBtn.setEffect(new DropShadow(10, Color.BLACK));
        confirmBtn.setOnMouseEntered(e -> confirmBtn.setStyle("-fx-background-color: #ad1111; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;"));
        confirmBtn.setOnMouseExited(e -> confirmBtn.setStyle("-fx-background-color: #0a0a0a; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;"));

        confirmBtn.setOnAction(event -> {
            RadioButton selectedButton = (RadioButton) questionTypeGroup.getSelectedToggle();
            selectedQuestionType = selectedButton.getText().toLowerCase(); // Store the selected question type
            dialogStage.close();
        });

        layout.getChildren().addAll(label, softwareEngineeringBtn, informationRetrievalBtn, artificialIntelligenceBtn, confirmBtn);

        Scene scene = new Scene(layout, 350, 250); // Adjusted size for the dialog
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        return selectedQuestionType;
    }

    private RadioButton createStyledRadioButton(String text, ToggleGroup group) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setToggleGroup(group);
        radioButton.setFont(Font.font("Arial", 16));
        radioButton.setStyle("-fx-text-fill: white;");
        return radioButton;
    }
}