package constants;
public class GameMode {
    private String mode; // Instance variable to store the mode
    private static GameMode instance; // Static variable for the singleton instance

    /**
     * Private constructor to prevent direct instantiation.
     */
    private GameMode() {
        // Default mode or initialization logic can go here, if needed
        this.mode = "easy";//is the default
    }

    /**
     * Public method to provide access to the singleton instance.
     * 
     * @return the single instance of GameMode
     */
    public static GameMode getInstance() {
        if (instance == null) {
          instance = new GameMode();
        }
        return instance;
    }

    /**
     * Getter for mode.
     * 
     * @return the current game mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Setter for mode.
     * 
     * @param mode the game mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Resets the singleton instance (useful for testing or reinitialization).
     */
    public static void resetInstance() {
        instance = null;
    }
}
