package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
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
        Stop[] stops = new Stop[] { new Stop(0, Color.BROWN), new Stop(1, Color.FORESTGREEN) };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        VBox layout = new VBox(10);
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Choose a game mode:");
        label.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-weight: bold;");

        // Radio buttons for modes
        ToggleGroup modeGroup = new ToggleGroup();
        RadioButton easyBtn = new RadioButton("Easy");
        easyBtn.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        easyBtn.setToggleGroup(modeGroup);

        RadioButton mediumBtn = new RadioButton("Medium");
        mediumBtn.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        mediumBtn.setToggleGroup(modeGroup);

        RadioButton hardBtn = new RadioButton("Hard");
        hardBtn.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        hardBtn.setToggleGroup(modeGroup);

        // Default selection
        easyBtn.setSelected(true);

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-font-size: 14;");
        confirmBtn.setOnAction(event -> {
            selectedMode = ((RadioButton) modeGroup.getSelectedToggle()).getText().toLowerCase();
            dialogStage.close();
        });

        layout.getChildren().addAll(label, easyBtn, mediumBtn, hardBtn, confirmBtn);
        Scene scene = new Scene(layout, 300, 200);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        return selectedMode;
    }
}
