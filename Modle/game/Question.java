package game;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Question {
    private String question;           // Question text
    private List<String> answers;      // List of possible answers

    @SerializedName("correct_ans")
    private String correctAns;         // Correct answer index as a string (e.g., "2")

    private String difficulty;         // Difficulty level as a string (e.g., "1")

    // Constructor
    public Question(String question, List<String> answers, String correctAns, String difficulty) {
        this.question = question;
        this.answers = answers;
        this.correctAns = correctAns;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", correctAns='" + correctAns + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
