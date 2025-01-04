package game_engine;

import java.io.*;

import game.GameRecord;
import java.util.ArrayList;
import java.util.List;

public class SysData {
    private static final String FILE_NAME = "game_history.dat";

    // Save game history to a file
    public static void saveGameHistory(List<GameRecord> gameHistory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game_history.dat"))) {
            oos.writeObject(gameHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Load game history from a file
    public static List<GameRecord> loadGameHistory() {
        File file = new File("game_history.dat");
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<GameRecord>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
