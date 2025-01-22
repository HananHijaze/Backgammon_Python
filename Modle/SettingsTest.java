import static org.junit.Assert.*;

import org.junit.Test;

import game_engine.Settings;

public class SettingsTest {

	@Test
	public void testSetTotalGames() {
		   // Arrange
        int newTotalGames = 9; // המספר החדש שנרצה להגדיר

        // Act
        Settings.setTotalGames(newTotalGames); // קריאה לפונקציה

        // Assert
        assertEquals("TOTAL_GAMES_IN_A_MATCH should be updated to the new value", 
                     newTotalGames, 
                     Settings.TOTAL_GAMES_IN_A_MATCH);
	}

}
