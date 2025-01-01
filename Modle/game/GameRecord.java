package game;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class GameRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String winnerName;
    private int score;
    private LocalDate date;
    private int duration; // in minutes

    // Constructor
    public GameRecord(String winnerName, int score, LocalDate date, int duration) {
        if (score < 0) {
            throw new IllegalArgumentException("Score must be non-negative.");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive.");
        }
        this.winnerName = winnerName;
        this.score = score;
        this.date = date;
        this.duration = duration;
    }

    // Getters and Setters
    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score must be non-negative.");
        }
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive.");
        }
        this.duration = duration;
    }

    // Override toString
    @Override
    public String toString() {
        return "GameRecord [Winner: " + winnerName + ", Score: " + score +
               ", Date: " + date + ", Duration: " + duration + " mins]";
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GameRecord that = (GameRecord) obj;
        return score == that.score &&
               duration == that.duration &&
               Objects.equals(winnerName, that.winnerName) &&
               Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(winnerName, score, date, duration);
    }
}
