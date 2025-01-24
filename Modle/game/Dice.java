package game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;
import interfaces.ColorParser;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * This class represents the dice object in Backgammon game.
 * 
 * @teamname TeaCup
 * @author Bryan Sng, 17205050
 * @author @LxEmily, 17200573
 * @author Braddy Yeoh, 17357376
 *
 */
public class Dice extends ImageView implements ColorParser {
	private  int MAX_DICE_SIZE ;
	private Image[] dices;
	private int diceRollResult;
	private ColorAdjust colorAdjust;
	private Color color;
	
	/**
	 * Constructors
	 * 		- Initialize the dices array with the possible dice images (i.e. 1-6).
	 */
	public Dice(Dice otherDice) {
		this(otherDice.getColor(),otherDice.MAX_DICE_SIZE);
		this.diceRollResult = otherDice.getDiceResult();
	}
	public Dice(int diceRollResult) {
		this.diceRollResult = diceRollResult;
	}
	public Dice(Color color, int maxSize) {
		super();
		this.color = color;
		this.MAX_DICE_SIZE=maxSize;
		dices = new Image[MAX_DICE_SIZE];
		initImages(color);
		
		colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(-0.5);
	}
	
	/**
	 * Initializes the dice images:
	 * 		- by getting the images from file,
	 * 		- adding them to the dices array.
	 * @param color
	 */
	private void initImages(Color c) {
		String colorString = parseColor(c);
		for (int i = 0; i < dices.length; i++) {
			try {
				InputStream input = getClass().getResourceAsStream("img/dices/" + colorString + "/" + (i+1) + ".png");
				dices[i] = new Image(input);
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the roll dice result (i.e. number from 1 to 6).
	 * @return the roll dice result.
	 */
	public int roll() {
		Random rand = new Random();
		int res = rand.nextInt(MAX_DICE_SIZE) + 1;
		return res;
	}
	
	/**
	 * Set the image of dice based on result.
	 * i.e. If result is 1, show image with dice at 1.
	 * @param result of dice roll.
	 */
	public Dice draw(int result) {
		System.out.println("in method draw the result is: "+result);
		diceRollResult = result;
		setImage(dices[result-1]);
		setEffect(null);
		rotate();
		return this;
	}
	
	/**
	 * Rotate the dice image.
	 */
	private void rotate() {
		// rotation range of 15 to -15.
		Random rand = new Random();
		int rotation = rand.nextInt(30) - 15 + 1;
		setRotate(rotation);
	}
	
	public void setUsed() {
		// darken image.
		setEffect(colorAdjust);
	}
	
	public void setNotUsed() {
		setEffect(null);
	}
	
	public int getDiceResult() {
		return diceRollResult;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean equalsValueOf(Dice otherDice) {
		return diceRollResult == otherDice.getDiceResult();
	}
	@Override
	public String toString() {
		return "Dice [MAX_DICE_SIZE=" + MAX_DICE_SIZE + ", diceRollResult=" + diceRollResult + ", color=" + color + "]";
	}
	public int getDiceRollResult() {
		return diceRollResult;
	}
	public void setDiceRollResult(int diceRollResult) {
		this.diceRollResult = diceRollResult;
	}
	
	
}
