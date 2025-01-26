package game;

import ui.QuestionDialog;
import ui.QuestionTableView;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.function.Consumer;

public class QuestionManager {
    private List<Question> questions;

    public QuestionManager() {
        QuestionTableView questionTableView = new QuestionTableView();
        this.questions = questionTableView.getQuestionData();

        if (questions.isEmpty()) {
            throw new RuntimeException("No questions available!");
        }
    }

    public void displayQuestion(int difficulty, Consumer<Boolean> callback) {
        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions available.", "Error", JOptionPane.ERROR_MESSAGE);
            callback.accept(false);
            return;
        }

        // Filter questions by difficulty
        List<Question> filteredQuestions = questions.stream()
                .filter(q -> Integer.parseInt(q.getDifficulty()) == difficulty)
                .collect(Collectors.toList());

        if (filteredQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions found for this difficulty level.", "Error", JOptionPane.ERROR_MESSAGE);
            callback.accept(false);
            return;
        }

        // Pick a random question
        Random random = new Random();
        Question question = filteredQuestions.get(random.nextInt(filteredQuestions.size()));
        System.out.println(question);

        // Show the question dialog
        new QuestionDialog(question, selectedAnswer -> {
            int correctIndex = Integer.parseInt(question.getCorrectAns()) - 1;
            String correctAnswer = question.getAnswers().get(correctIndex);

            boolean isCorrect = selectedAnswer.equals(correctAnswer);
            String message = isCorrect ? "Correct!" : "Incorrect! The correct answer was: " + correctAnswer;
            int messageType = isCorrect ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;

            // Ensure the result dialog is always on top
            SwingUtilities.invokeLater(() -> {
                JDialog resultDialog = new JDialog();
                resultDialog.setAlwaysOnTop(true); // Make it always on top
                JOptionPane.showMessageDialog(resultDialog, message, "Result", messageType);
                resultDialog.dispose(); // Dispose of the dialog once closed
            });

            callback.accept(isCorrect);
        }).setVisible(true);
    }

}
