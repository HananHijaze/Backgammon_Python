import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import game.Dice;
import game.Dices;

public class DiceTest {
	private Dices dices;

	@Test
    public void testInitDicesEasyMode() {
        // בדיקת מצב "easy"
        dices = new Dices("easy");
        Dice[] initializedDices = dices.getDices();

        // בדיקה שאורך המערך נכון
        assertEquals("Easy mode should initialize 4 dices", 4, initializedDices.length);
    }

}
