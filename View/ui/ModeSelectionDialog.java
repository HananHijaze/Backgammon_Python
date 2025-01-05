package ui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModeSelectionDialog {

    private String selectedMode;

    public String showAndWait(Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Select Game Mode");

        Label label = new Label("Choose a game mode:");
        label.setStyle("-fx-font-size: 16;");

        // Radio buttons for modes
        ToggleGroup modeGroup = new ToggleGroup();
        RadioButton easyBtn = new RadioButton("Easy");
        easyBtn.setToggleGroup(modeGroup);
        RadioButton mediumBtn = new RadioButton("Medium");
        mediumBtn.setToggleGroup(modeGroup);
        RadioButton hardBtn = new RadioButton("Hard");
        hardBtn.setToggleGroup(modeGroup);

        // Default selection
        easyBtn.setSelected(true);

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnAction(event -> {
            RadioButton selectedButton = (RadioButton) modeGroup.getSelectedToggle();
            selectedMode = selectedButton.getText().toLowerCase(); // Store the selected mode
            dialogStage.close();
        });

        VBox layout = new VBox(10, label, easyBtn, mediumBtn, hardBtn, confirmBtn);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        return selectedMode;
    }
}
