package game;

import ui.QuestionDialog;
import ui.QuestionTableView;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QuestionManager {
    private List<Question> questions;

    // בנאי שטוען שאלות אוטומטית
    public QuestionManager() {
        // יצירת מופע של QuestionTableView לטעינת השאלות
        QuestionTableView questionTableView = new QuestionTableView();
        this.questions = questionTableView.getQuestionData();
        System.out.println(questions);

        if (questions.isEmpty()) {
            throw new RuntimeException("No questions available!");
        }
    }

    public boolean displayQuestion(int difficulty) {
        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions available.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // יצירת JFrame
        JFrame frame = new JFrame("Backgammon Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(false);

        // סינון השאלות לפי דרגת קושי
        List<Question> filteredQuestions = questions.stream()
                .filter(q -> Integer.parseInt(q.getDifficulty()) == difficulty)
                .collect(Collectors.toList());

        if (filteredQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No questions found for this difficulty level.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // בחירת שאלה רנדומלית מתוך הרשימה המסוננת
        Random random = new Random();
        Question question = filteredQuestions.get(random.nextInt(filteredQuestions.size()));
        System.out.println(question);

        // הצגת הדיאלוג
        QuestionDialog dialog = new QuestionDialog(frame, question);
        dialog.setVisible(true);

        // אימות התשובה
        String selectedAnswer = dialog.getSelectedAnswer();
        if (selectedAnswer != null) {
            int correctIndex = Integer.parseInt(question.getCorrectAns()) - 1;
            String correctAnswer = question.getAnswers().get(correctIndex);

            if (selectedAnswer.equals(correctAnswer)) {
                JOptionPane.showMessageDialog(frame, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect! The correct answer was: " + correctAnswer, "Result", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
		
    }
}
