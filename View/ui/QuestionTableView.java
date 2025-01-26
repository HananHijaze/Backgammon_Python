package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import game.Question;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuestionTableView extends Application {

    private ObservableList<Question> questionData;
    private TableView<Question> tableView; // Declare tableView as an instance variable

    @Override
    public void start(Stage primaryStage) {
        tableView = new TableView<>();

        // Define columns
        TableColumn<Question, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        questionColumn.setPrefWidth(200); // Set preferred width for this column


        TableColumn<Question, List<String>> answersColumn = new TableColumn<>("Answers");
        answersColumn.setCellValueFactory(new PropertyValueFactory<>("answers"));
        answersColumn.setCellFactory(col -> new TableCell<Question, List<String>>() {
            private final TextArea textArea = new TextArea();

            {
                textArea.setWrapText(true);
                textArea.setEditable(false);
                textArea.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            }

            @Override
            protected void updateItem(List<String> item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    textArea.setText(String.join("\n", item));
                    setGraphic(textArea);
                }
            }
        });

        TableColumn<Question, String> correctAnswerColumn = new TableColumn<>("Correct Answer");
        correctAnswerColumn.setCellValueFactory(data -> {
            Question question = data.getValue();
            int correctIndex = Integer.parseInt(question.getCorrectAns()) - 1; // Convert to 0-based index
            if (correctIndex >= 0 && correctIndex < question.getAnswers().size()) {
                return javafx.beans.property.SimpleStringProperty.stringExpression(
                        javafx.beans.binding.Bindings.createStringBinding(() -> question.getAnswers().get(correctIndex))
                );
            } else {
                return javafx.beans.property.SimpleStringProperty.stringExpression(
                        javafx.beans.binding.Bindings.createStringBinding(() -> "Invalid Index")
                );
            }
        });

        TableColumn<Question, String> difficultyColumn = new TableColumn<>("Difficulty");
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        // Add columns to the table
        tableView.getColumns().addAll(questionColumn, answersColumn, correctAnswerColumn, difficultyColumn);

        // Set data
        questionData = getQuestionData();
        tableView.setItems(questionData);

        // Add double-click event listener
        tableView.setRowFactory(tv -> {
            TableRow<Question> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Question question = row.getItem();
                    handleDoubleClick(question);
                }
            });
            return row;
        });

        // Add "Add Question" button and center it
        Button addQuestionButton = createStyledButton("Add Question");
        addQuestionButton.setOnAction(e -> addNewQuestion());

 

        // Create a layout with the button at the top and the table below
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        layout.setStyle("-fx-padding: 20; -fx-background-color: linear-gradient(to bottom, #6b4423, #8b6914);");
        // Create the scene and set it to the stage
        Scene scene = new Scene(layout, 1200, 450);
        primaryStage.setTitle("Questions Table");
        primaryStage.setScene(scene);
        primaryStage.show();
        layout.getChildren().addAll(addQuestionButton, tableView);

    }
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #2e8b57; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-padding: 10px;");
        return button;
    }

    private void addNewQuestion() {
        // Create a dialog to add a new question
        Dialog<Question> dialog = new Dialog<>();
        dialog.setTitle("Add New Question");
        dialog.setHeaderText("Enter the details of the new question:");

        // Create input fields
        TextField questionField = new TextField();
        questionField.setPromptText("Question");

        TextField answer1Field = new TextField();
        answer1Field.setPromptText("Answer 1");

        TextField answer2Field = new TextField();
        answer2Field.setPromptText("Answer 2");

        TextField answer3Field = new TextField();
        answer3Field.setPromptText("Answer 3");

        TextField answer4Field = new TextField();
        answer4Field.setPromptText("Answer 4");

        TextField correctAnswerField = new TextField();
        correctAnswerField.setPromptText("Correct Answer (1-4)");

        TextField difficultyField = new TextField();
        difficultyField.setPromptText("Difficulty");

        // Arrange fields in a grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Question:"), 0, 0);
        grid.add(questionField, 1, 0);

        grid.add(new Label("Answer 1:"), 0, 1);
        grid.add(answer1Field, 1, 1);

        grid.add(new Label("Answer 2:"), 0, 2);
        grid.add(answer2Field, 1, 2);

        grid.add(new Label("Answer 3:"), 0, 3);
        grid.add(answer3Field, 1, 3);

        grid.add(new Label("Answer 4:"), 0, 4);
        grid.add(answer4Field, 1, 4);

        grid.add(new Label("Correct Answer:"), 0, 5);
        grid.add(correctAnswerField, 1, 5);

        grid.add(new Label("Difficulty:"), 0, 6);
        grid.add(difficultyField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Add OK and Cancel buttons
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Convert the result to a Question object when OK is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                List<String> answers = new ArrayList<>();
                answers.add(answer1Field.getText());
                answers.add(answer2Field.getText());
                answers.add(answer3Field.getText());
                answers.add(answer4Field.getText());
                return new Question(
                        questionField.getText(),
                        answers,
                        correctAnswerField.getText(),
                        difficultyField.getText()
                );
            }
            return null;
        });

        // Show the dialog and add the new question if OK is clicked
        dialog.showAndWait().ifPresent(newQuestion -> {
            questionData.add(newQuestion);
            saveQuestionsToFile();
            tableView.refresh(); // Refresh the table view to show the new question
        });
    }
    private void handleDoubleClick(Question question) {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit or Delete");
        alert.setHeaderText("What would you like to do with this question?");
        alert.setContentText("Choose an action:");

        ButtonType editButton = new ButtonType("Edit");
        ButtonType deleteButton = new ButtonType("Delete");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(editButton, deleteButton, cancelButton);

        // Show the dialog and get the result
        alert.showAndWait().ifPresent(response -> {
            if (response == editButton) {
                editQuestion(question);
            } else if (response == deleteButton) {
                deleteQuestion(question);
            }
        });
    }

    private void editQuestion(Question question) {
        // Create a dialog to edit the question
        Dialog<Question> dialog = new Dialog<>();
        dialog.setTitle("Edit Question");
        dialog.setHeaderText("Edit the details of the question:");

        // Create input fields
        TextField questionField = new TextField(question.getQuestion());
        questionField.setPromptText("Question");

        TextField answer1Field = new TextField(question.getAnswers().get(0));
        answer1Field.setPromptText("Answer 1");

        TextField answer2Field = new TextField(question.getAnswers().get(1));
        answer2Field.setPromptText("Answer 2");

        TextField answer3Field = new TextField(question.getAnswers().get(2));
        answer3Field.setPromptText("Answer 3");

        TextField answer4Field = new TextField(question.getAnswers().get(3));
        answer4Field.setPromptText("Answer 4");

        TextField correctAnswerField = new TextField(question.getCorrectAns());
        correctAnswerField.setPromptText("Correct Answer (1-4)");

        TextField difficultyField = new TextField(question.getDifficulty());
        difficultyField.setPromptText("Difficulty");

        // Arrange fields in a grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Question:"), 0, 0);
        grid.add(questionField, 1, 0);

        grid.add(new Label("Answer 1:"), 0, 1);
        grid.add(answer1Field, 1, 1);

        grid.add(new Label("Answer 2:"), 0, 2);
        grid.add(answer2Field, 1, 2);

        grid.add(new Label("Answer 3:"), 0, 3);
        grid.add(answer3Field, 1, 3);

        grid.add(new Label("Answer 4:"), 0, 4);
        grid.add(answer4Field, 1, 4);

        grid.add(new Label("Correct Answer:"), 0, 5);
        grid.add(correctAnswerField, 1, 5);

        grid.add(new Label("Difficulty:"), 0, 6);
        grid.add(difficultyField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Add OK and Cancel buttons
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Convert the result to a Question object when OK is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                question.setQuestion(questionField.getText());
                question.getAnswers().set(0, answer1Field.getText());
                question.getAnswers().set(1, answer2Field.getText());
                question.getAnswers().set(2, answer3Field.getText());
                question.getAnswers().set(3, answer4Field.getText());
                question.setCorrectAns(correctAnswerField.getText());
                question.setDifficulty(difficultyField.getText());
                return question;
            }
            return null;
        });

        // Show the dialog and save changes if OK is clicked
        dialog.showAndWait().ifPresent(updatedQuestion -> {
            saveQuestionsToFile();
            tableView.refresh(); // Refresh the table view to show updated data
        });
    }

    private void deleteQuestion(Question question) {
        questionData.remove(question);
        saveQuestionsToFile();
        tableView.refresh(); // Refresh the table view to show updated data
    }

    private void saveQuestionsToFile() {
        try {
            // Get the file path from resources
            URL resource = getClass().getClassLoader().getResource("Questions.json");
            if (resource == null) {
                throw new IllegalArgumentException("Questions.json not found in resources");
            }
            String filePath = resource.toURI().getPath();
            filePath = filePath.replaceFirst("^/(.:/)", "$1"); // Fix leading slash on Windows
            filePath = filePath.replace("%20", " "); // Decode spaces

            try (FileWriter writer = new FileWriter(filePath)) {
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("questions", gson.toJsonTree(questionData));
                gson.toJson(jsonObject, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Question> getQuestionData() {
        try {
            // Get the file path from resources
            URL resource = getClass().getClassLoader().getResource("Questions.json");
            if (resource == null) {
                throw new IllegalArgumentException("Questions.json not found in resources");
            }
            String filePath = resource.toURI().getPath();
            filePath = filePath.replaceFirst("^/(.:/)", "$1"); // Fix leading slash on Windows
            filePath = filePath.replace("%20", " "); // Decode spaces

            try (Reader reader = new FileReader(filePath)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Question>>() {}.getType();
                JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
                List<Question> questions = gson.fromJson(jsonObject.get("questions"), listType);
                return FXCollections.observableArrayList(questions);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
