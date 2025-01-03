// Main.java
package game_engine;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.MainMenuView;

/**
 * This class runs the entire application.
 * 
 * @teamname TeaCup
 * @author Bryan Sng, 17205050
 * @author @LxEmily, 17200573
 * @author Braddy Yeoh, 17357376
 *
 */
/**********/
public class Main extends Application {
    public static void main(String[] args) {
        launch(args); // calls start method.
    }

    @Override
    public void start(Stage primaryStage) {
        MatchController matchController = new MatchController(primaryStage); // Create MatchController instance
        MainMenuView mainMenu = new MainMenuView(primaryStage,matchController);
        Scene mainScene = mainMenu.createScene();

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Backgammon Main Menu");
        primaryStage.show();
    }
    /**
     * Set the application's icon.
     * @param stage, the stage of the application.
     */
    public void setStageIcon(Stage stage) {
        try {
            InputStream input = Main.class.getResourceAsStream("/game/img/icon/icon.png");
            stage.getIcons().add(new Image(input));
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
