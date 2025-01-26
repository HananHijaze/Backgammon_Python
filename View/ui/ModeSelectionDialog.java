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

public class ModeSelectionDialog {

    private String selectedMode;

    public String showAndWait(Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Select Game Mode");

        // Gradient background
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ad1111")), // Brown
                new Stop(1, Color.web("#0a0a0a"))  // Green
        );

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        Label label = new Label("Choose a game mode:");
        label.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");
        label.setFont(Font.font("Arial", 18));

        // Radio buttons for modes
        ToggleGroup modeGroup = new ToggleGroup();
        RadioButton easyBtn = createStyledRadioButton("Easy", modeGroup);
        RadioButton mediumBtn = createStyledRadioButton("Medium", modeGroup);
        RadioButton hardBtn = createStyledRadioButton("Hard", modeGroup);

        // Default selection
        easyBtn.setSelected(true);

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setFont(Font.font("Arial", 16));
        confirmBtn.setStyle("-fx-background-color: #0a0a0a; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;");
        confirmBtn.setEffect(new DropShadow(10, Color.BLACK));
        confirmBtn.setOnMouseEntered(e -> confirmBtn.setStyle("-fx-background-color: #2e8b57; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;"));
        confirmBtn.setOnMouseExited(e -> confirmBtn.setStyle("-fx-background-color: #0a0a0a; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px;"));

        confirmBtn.setOnAction(event -> {
            RadioButton selectedButton = (RadioButton) modeGroup.getSelectedToggle();
            selectedMode = selectedButton.getText().toLowerCase(); // Store the selected mode
            dialogStage.close();
        });

        layout.getChildren().addAll(label, easyBtn, mediumBtn, hardBtn, confirmBtn);

        Scene scene = new Scene(layout, 400, 300);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        return selectedMode;
    }

    private RadioButton createStyledRadioButton(String text, ToggleGroup group) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setToggleGroup(group);
        radioButton.setFont(Font.font("Arial", 16));
        radioButton.setStyle("-fx-text-fill: white;");
        return radioButton;
    }
}