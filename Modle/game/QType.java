package game;

public class QType {

    // Private static instance of the class
    private static QType instance;

    // Attribute to store the String value
    private String type;

    // Private constructor to prevent instantiation from outside
    private QType() {
        // Initialize with a default value if needed
        this.type = "Software Engineering"; // set a default value 
    }

    // Public method to get the singleton instance
    public static QType getInstance() {
        if (instance == null) {
            instance = new QType();
        }
        return instance;
    }

    // Method to set the type
    public void setType(String type) {
        this.type = type;
    }

    // Method to get the type
    public String getType() {
        return this.type;
    }

    // Optional: Method to reset the type (if needed)
    public void resetType() {
        this.type = null; // or set it to a default value
    }
}