// MatchController.java
package game_engine;

import java.time.LocalDate;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import constants.GameConstants;
import constants.GameMode;
import constants.MessageType;
import constants.PlayerPerspectiveFrom;
import game.GameRecord;
import game.QType;
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
import ui.ModeSelectionDialog;
import ui.QuestionTypeSelectionDialog;
import ui.RollDieButton;
import ui.ScoreboardPrompt;
import ui.Dialogs;
import ui.GameHistoryUI;

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
    private long gameStartTime;
    private long gameEndTime;

    public MatchController(Stage stage) {
        super();
        this.stage = stage;
        this.gameHistory= new ArrayList<>();
        initApplication();
        initGame();
        style();
        
        gameHistory = SysData.loadGameHistory();
    }
 // Use the shared MusicPlayer instance passed from Main
    public MatchController(Stage stage, MusicPlayer musicPlayer) {
        super();
        this.stage = stage;
        this.musicPlayer = musicPlayer;
        this.gameHistory = new ArrayList<>();
        initApplication();
        initGame();
        style();
        gameHistory = SysData.loadGameHistory();
    }

    private String promptModeSelection(Stage stage) {
        // Use the dialog to get the mode
        ModeSelectionDialog modeDialog = new ModeSelectionDialog();
        String selectedMode = modeDialog.showAndWait(stage);

        // Default to "easy" if the player cancels or doesn't select a mode
        if (selectedMode == null || selectedMode.isEmpty()) {
            selectedMode = "easy";
        }

        return selectedMode; // Return the selected mode
    }

    private String promptQtype(Stage stage) {
    	QuestionTypeSelectionDialog typeDialog = new QuestionTypeSelectionDialog();
        String selectedType = typeDialog.showAndWait(stage);
        
        if (selectedType == null || selectedType.isEmpty()) {
            selectedType = "Questions";
        }

        return selectedType; // Return the selected mode

    	}
    private void initApplication() {
        bottomPlayer = new Player(PlayerPerspectiveFrom.BOTTOM);
        topPlayer = new Player(PlayerPerspectiveFrom.TOP);
        infoPnl = new InfoPanel();
        rollDieBtn = new RollDieButton();
        cmdPnl = new CommandPanel();
        isPlayerInfosEnteredFirstTime = true;
        isPromptCancel = false;

    }

    private void initGame() {
        //this.mode = promptModeSelection(stage);
        game = new GameComponentsController(bottomPlayer, topPlayer );
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
        // Save game history
        SysData.saveGameHistory(gameHistory);

        isPlayerInfosEnteredFirstTime = true;
        isPromptCancel = false;
        hadCrawfordGame = false;
        isCrawfordGame = false;
        Settings.setTotalGames(Settings.DEFAULT_TOTAL_GAMES);
        SysData.saveGameHistory(gameHistory);
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
    	 gameStartTime = System.currentTimeMillis(); 
        GameMode mode=GameMode.getInstance();
        mode.setMode(promptModeSelection(stage));
        QType.getInstance().setType(promptQtype(stage));
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

            // Mark that player information has been entered
            isPlayerInfosEnteredFirstTime = false;

            // Make the roll die button visible
            rollDieBtn.setVisible(true);

            // Start the gameplay logic
            gameplay.start();
        } else {
            // Display an error message if the game was not started
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

        // Call handleMatchEnd to update game history
        handleMatchEnd(winner.getShortName(), winner.getScore());
        
        // Show the updated game history after the match ends
        GameHistoryUI.createAndShowGUI(gameHistory);

        // Display the match over dialog
        Dialogs<ButtonType> dialog = new Dialogs<>(
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
		// Create dialog.
		Dialogs<promptResults> dialog = new Dialogs<promptResults>("Please enter player names and number of games to play", stage, "Start");
		
		// Create dialog contents.
		ScoreboardPrompt contents = new ScoreboardPrompt();
		
		// Add contents to dialog.
		dialog.getDialogPane().setContent(contents);
		
		// On click start button, return player names as result.
		// Else result is null, cancel the game.
		dialog.setResultConverter(click -> {
			if (click == dialog.getButton())
				return new promptResults(contents.getPlayerInput("black"), contents.getPlayerInput("white"), contents.getPlayerInput("score"));
			return null;
		});
		
		// Show dialog to get player input.
		Optional<promptResults> result = dialog.showAndWait();
		
		// If result is present and name is not empty, change player names.
		// If result is null, cancel starting the game.
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
        return gameHistory;
    }
    public void handleMatchEnd(String winnerName, int winnerScore) {
    	  gameEndTime = System.currentTimeMillis(); // Record the end time
    	  int gameDurationInMinutes = (int) ((gameEndTime - gameStartTime) / 60000); // Calculate duration

        // Create a new GameRecord
        GameRecord newRecord = new GameRecord(
            winnerName,
            winnerScore,
            LocalDate.now(),
            gameDurationInMinutes
        );

        // Update top 10 scores
        updateTopScores(newRecord);

        // Save the updated game history to a file
        SysData.saveGameHistory(gameHistory);

        // Print the updated game history for debugging
        System.out.println("Updated Game History:");
        for (GameRecord record : gameHistory) {
            System.out.println(record);
        }
        
    }


 
}