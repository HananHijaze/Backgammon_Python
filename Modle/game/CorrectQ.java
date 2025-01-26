package game;

public class CorrectQ {
    private static CorrectQ instance;
    private boolean isCorrect;

    // Private constructor to prevent instantiation
    private CorrectQ() {
    	this.isCorrect=true;
    }

    // Static method to get the single instance
    public static CorrectQ getInstance() {
        if (instance == null) {
            instance = new CorrectQ();
        }
        return instance;
    }

    // Getter for isCorrect
    public boolean isCorrect() {
        return isCorrect;
    }

    // Setter for isCorrect
    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
