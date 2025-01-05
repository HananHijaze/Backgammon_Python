import static org.junit.Assert.*;

import org.junit.Test;

import constants.PlayerPerspectiveFrom;
import game_engine.Settings;

public class SettingsPlayerNameTest {

	  @Test
	    public void testGetDefaultPlayerNameBottom() {
	        // Arrange
	        PlayerPerspectiveFrom pov = PlayerPerspectiveFrom.BOTTOM;
	        
	        // Act
	        String playerName = Settings.getDefaultPlayerName(pov);

	        // Assert
	        assertEquals("The default name for the bottom perspective should be 'player1'.", "player1", playerName);
	    }
	   @Test
	    public void testGetDefaultPlayerNameTop() {
	        // Arrange
	        PlayerPerspectiveFrom pov = PlayerPerspectiveFrom.TOP;
	        
	        // Act
	        String playerName = Settings.getDefaultPlayerName(pov);

	        // Assert
	        assertEquals("The default name for the top perspective should be 'player2'.", "player2", playerName);
	    }

}
