// MatchController.java
package game_engine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import constants.GameConstants;
import constants.MessageType;
import constants.PlayerPerspectiveFrom;
import game.GameRecord;
import interfaces.ColorPerspectiveParser;
import interfaces.InputValidator;
import musicplayer.MusicPlayer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.CommandPanel;
import ui.InfoPanel;
import ui.RollDieButton;
import ui.ScoreboardPrompt;
import ui.Dialogs;

public class MatchController extends GridPane implements ColorPerspectiveParser, InputValidator {
    private Player bottomPlayer;
    private Player topPlayer;
    private GameComponentsController game;
    private InfoPanel infoPnl;
    private RollDieButton rollDieBtn;
    private CommandPanel cmdPnl;
    private CommandController cmd;
    private GameplayController gameplay;
    private EventController event;
    private MusicPlayer musicPlayer;
    private Stage stage;
    private List<GameRecord> gameHistory;
    private boolean isPlayerInfosEnteredFirstTime, isPromptCancel, hadCrawfordGame, isCrawfordGame;

    public MatchController(Stage stage) {
        super();
        this.stage = stage;
        this.gameHistory= new ArrayList<>();
        gameHistory.add(new GameRecord("Default Player", 50, LocalDate.now(), 20));
        initApplication();
        initGame();
        style();
    }

    private void initApplication() {
        bottomPlayer = new Player(PlayerPerspectiveFrom.BOTTOM);
        topPlayer = new Player(PlayerPerspectiveFrom.TOP);
        infoPnl = new InfoPanel();
        rollDieBtn = new RollDieButton();
        cmdPnl = new CommandPanel();
        musicPlayer = new MusicPlayer();
        isPlayerInfosEnteredFirstTime = true;
        isPromptCancel = false;
    }

    private void initGame() {
        game = new GameComponentsController(bottomPlayer, topPlayer);
        gameplay = new GameplayController(stage, this, game, infoPnl, bottomPlayer, topPlayer);
        cmd = new CommandController(stage, this, game, gameplay, infoPnl, cmdPnl, bottomPlayer, topPlayer, musicPlayer);
        gameplay.setCommandController(cmd);
        event = new EventController(stage, this, game, gameplay, cmdPnl, cmd, infoPnl, rollDieBtn);
        cmd.setEventController(event);
        initLayout();
    }

    public void resetApplication() {
        cmdPnl.reset();
        musicPlayer.reset();
        bottomPlayer.reset();
        topPlayer.reset();
        infoPnl.reset();
        resetGame();
        game.resetTimers();

        isPlayerInfosEnteredFirstTime = true;
        isPromptCancel = false;
        hadCrawfordGame = false;
        isCrawfordGame = false;
        Settings.setTotalGames(Settings.DEFAULT_TOTAL_GAMES);
    }

    public void resetGame() {
        bottomPlayer.setHasCube(false);
        topPlayer.setHasCube(false);
        game.reset();
        gameplay.reset();
        cmd.reset();
        event.reset();
    }

    public void restartGame() {
        resetGame();
        startGame();
    }

    public void startGame() {
        // Prompt players for their information
        promptStartGame();

        if (!isPromptCancel) {
            // Handle Crawford Game if applicable
            if (!hadCrawfordGame && checkIsCrawfordGame()) {
                isCrawfordGame = true;
                hadCrawfordGame = true;
                infoPnl.print("Current game is a Crawford game.");
            } else if (isCrawfordGame) {
                isCrawfordGame = false;
                infoPnl.print("New game is not a Crawford game.", MessageType.DEBUG);
            }

            isPlayerInfosEnteredFirstTime = false;
            rollDieBtn.setVisible(true);

            gameplay.start(); // Start the gameplay logic
        } else {
            infoPnl.print("Game not started. Please enter valid information.", MessageType.ERROR);
        }
    }


    private boolean checkIsCrawfordGame() {
        return topPlayer.getScore() == Settings.TOTAL_GAMES_IN_A_MATCH - 1 || bottomPlayer.getScore() == Settings.TOTAL_GAMES_IN_A_MATCH - 1;
    }

    public boolean isMatchOver() {
        return (topPlayer.getScore() >= Settings.TOTAL_GAMES_IN_A_MATCH) || (bottomPlayer.getScore() >= Settings.TOTAL_GAMES_IN_A_MATCH);
    }

    public void handleMatchOver(boolean isOutOfTime) {
        Player winner;
        if (isOutOfTime) {
            winner = gameplay.getOpponent();
        } else {
            winner = gameplay.getCurrent();
        }

        Random random = new Random();
        int gameDuration = random.nextInt(26) + 5;

        // Create a new GameRecord
        GameRecord newRecord = new GameRecord(
            winner.getShortName(),
            winner.getScore(),
            LocalDate.now(),
            gameDuration
        );

        // Update top 10 scores
        updateTopScores(newRecord);

        // Display the match over dialog
        Dialogs<ButtonType> dialog = new Dialogs<ButtonType>(
            "Congratulations, " + winner.getShortName() + " wins the match!",
            stage,
            "Play again"
        );

        ScoreboardPrompt contents = new ScoreboardPrompt(topPlayer, bottomPlayer);

        dialog.getDialogPane().setContent(contents);

        Platform.runLater(() -> {
            Optional<ButtonType> result = dialog.showAndWait();

            if (ButtonType.OK.equals(result.get())) {
                resetApplication();
                cmd.runCommand("/start");
            } else {
                resetApplication();
                infoPnl.print("Enter /start if you wish to play again.", MessageType.ANNOUNCEMENT);
                infoPnl.print("Enter /quit if you wish to quit.", MessageType.ANNOUNCEMENT);
            }
        });
    }
    private void updateTopScores(GameRecord newRecord) {
        // Add new record if fewer than 10 records exist
        if (gameHistory.size() < 10) {
            gameHistory.add(newRecord);
        } else {
            // Find the record with the lowest score
            GameRecord lowestRecord = gameHistory.stream()
                .min(Comparator.comparingInt(GameRecord::getScore))
                .orElse(null);

            // Replace the lowest score if the new score is higher
            if (lowestRecord != null && newRecord.getScore() > lowestRecord.getScore()) {
                gameHistory.remove(lowestRecord);
                gameHistory.add(newRecord);
            }
        }

        // Sort gameHistory in descending order of scores
        gameHistory.sort((r1, r2) -> Integer.compare(r2.getScore(), r1.getScore()));
    }


    public void handleMatchOver() {
        handleMatchOver(false);
    }

    private void promptStartGame() {
        Dialogs<promptResults> dialog = new Dialogs<promptResults>("Please enter player names and number of games to play", stage, "Start");

        ScoreboardPrompt contents = new ScoreboardPrompt();

        dialog.getDialogPane().setContent(contents);

        dialog.setResultConverter(click -> {
            if (click == dialog.getButton())
                return new promptResults(contents.getPlayerInput("black"), contents.getPlayerInput("white"), contents.getPlayerInput("score"));
            return null;
        });

        Optional<promptResults> result = dialog.showAndWait();

        result.ifPresent((promptResults results) -> {
            if (results.bName.length() != 0)
                cmd.runCommand("/name black " + results.bName);
            if (results.wName.length() != 0)
                cmd.runCommand("/name white " + results.wName);

            String userInput = results.totalGames;
            if (userInput.length() == 0 || isValidInput(userInput)) {
                if (userInput.length() != 0) {
                    Settings.setTotalGames(Integer.parseInt(userInput));
                    game.getPlayerPanel(Settings.getTopPerspectiveColor()).updateTotalGames();
                    game.getPlayerPanel(Settings.getBottomPerspectiveColor()).updateTotalGames();
                }
                infoPnl.print("Max totalGames per game set to " + Settings.TOTAL_GAMES_IN_A_MATCH + ".");
                isPromptCancel = false;
            } else {
                infoPnl.print("You must play to a positive odd number less than 100. Please try again.", MessageType.ERROR);
                isPromptCancel = true;
                promptStartGame();
            }
        });
        if (!result.isPresent()) {
            isPromptCancel = true;
            infoPnl.print("Game not started.");
        }
    }

    private static class promptResults {
        String bName;
        String wName;
        String totalGames;

        promptResults(String bName, String wName, String totalGames) {
            this.bName = bName;
            this.wName = wName;
            this.totalGames = totalGames;
        }
    }

    private boolean isValidInput(String userInput) {
        boolean isValidInput = false;
        if (isNumber(userInput)) {
            int num = Integer.parseInt(userInput);
            if (num > 0 && num % 2 != 0 && num < 100) {
                isValidInput = true;
            }
        }
        return isValidInput;
    }

    public void style() {
        setBackground(GameConstants.getTableImage());
        setPadding(new Insets(10));
        setVgap(GameConstants.getUIVGap());
        setHgap(5);
        setAlignment(Pos.CENTER);
        setMaxSize(GameConstants.getScreenSize().getWidth(), GameConstants.getScreenSize().getHeight());
    }

    public void initLayout() {
        VBox terminal = new VBox();
        terminal.getChildren().addAll(infoPnl, cmdPnl);
        terminal.setAlignment(Pos.CENTER);
        terminal.setEffect(new DropShadow(20, 0, 0, Color.BLACK));

        getChildren().clear();
        add(game, 0, 0, 1, 3);
        add(rollDieBtn, 1, 2); 
    }

    public void setRollDiceAccelarator() {
        Scene scene = rollDieBtn.getScene();
        if (scene == null) {
            throw new IllegalArgumentException("Roll Dice Button not attached to a scene.");
        }

        scene.getAccelerators().put(
            new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN),
            rollDieBtn::fire
        );
    }

    public boolean isCrawfordGame() {
        return isCrawfordGame;
    }
  
    public List<GameRecord> getGameHistory() {
        return new ArrayList<>(gameHistory); // Return a copy to prevent modification
    }
    public void handleMatchEnd(String winnerName, int winnerScore) {
        // Generate a random game duration between 5 and 30 minutes
        Random random = new Random();
        int gameDuration = random.nextInt(26) + 5; // Random duration between 5-30 mins

        // Create a new GameRecord
        GameRecord newRecord = new GameRecord(
            winnerName,
            winnerScore,
            LocalDate.now(),
            gameDuration
        );

        // Update top 10 scores
        updateTopScores(newRecord);

        // Print the updated game history for debugging
        System.out.println("Updated Game History:");
        for (GameRecord record : gameHistory) {
            System.out.println(record);
        }
    }

    
}
