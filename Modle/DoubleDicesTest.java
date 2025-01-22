import static org.junit.Assert.*;

import javafx.scene.paint.Color;

import org.junit.Before;
import org.junit.Test;

import game.Dice;
import game.Dices;
import game.DieResults;

public class DoubleDicesTest {
	 private Dices dices;

	    @Before
	    public void setUp() {
	        dices = new Dices("easy"); // Initialize with "easy" mode
	    }

	    @Test
	    public void testAddDoubleDie() {
	        // Arrange
	        DieResults initialResult = new DieResults();
	        Dice mockDice = new Dice(Color.RED, 6);
	        mockDice.draw(3); // Set a value for the dice
	        initialResult.add(mockDice);
	        initialResult.add(mockDice);

	        // Act
	        DieResults doubledResult = dices.addDoubleDie(initialResult);

	        // Assert
	        assertNotNull("The doubled result should not be null.", doubledResult);
	        assertEquals("The doubled result should have twice the size of the initial result.", 
	                     initialResult.size() * 2, doubledResult.size());

	        for (Dice dice : doubledResult) {
	            assertEquals("Each dice in the doubled result should have the same value as the original dice.", 
	                         3, dice.getDiceResult());
	        }
	    }
	}
