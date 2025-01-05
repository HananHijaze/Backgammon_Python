package game;

import constants.DieInstance;
import constants.GameConstants;
import constants.GameMode;
import interfaces.ColorParser;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * This class represents a HBox of dices.
 * 
 * @teamname TeaCup
 * @author Bryan Sng, 17205050
 * @author @LxEmily, 17200573
 * @author Braddy Yeoh, 17357376
 *
 */
public class Dices extends HBox implements ColorParser {
	private Dice[] dices;
	private int flagforinitdice=0;

	
	/**
	 * Overloaded Constructor
	 * 		- Initialize the dices array with any number of dices.
	 * 		- Set this node's alignment.
	 * @param color of dices.
	 * @param numberOfDices number of dices. 
	 */
	public Dices(String mode) {
	    super();
	    setAlignment(Pos.CENTER);
	    setSpacing(GameConstants.getDiceSize().getWidth() / 4.0);
	    initDices(mode);
	    
	}
	private void initDices(String mode) {
		
	    switch (mode.toLowerCase()) {
	        case "easy":
	        case "medium":
	            // Two red dices with maxDiceSize = 6
	            dices = new Dice[4];
	            dices[0] = new Dice(Color.RED, 6);
	            dices[1] = new Dice(Color.RED, 6);
	            //random dices to be overwritten later when we get double dice
	            dices[2] = new Dice(Color.RED, 6);
	            dices[3] = new Dice(Color.RED, 6);
	            break;

	        case "hard":
	            // One red dice with maxDiceSize = 6 and one green dice with maxDiceSize = 9
	            dices = new Dice[4];
	            dices[0] = new Dice(Color.RED, 6);
	            dices[1] = new Dice(Color.RED, 10);//remember to add the pictures for it to work
	          //random dices to be overwritten later when we get double dice
	            dices[2] = new Dice(Color.RED, 6);
	            dices[3] = new Dice(Color.RED, 6);
	            break;

	        default:
	            throw new IllegalArgumentException("Invalid mode: " + mode);
	    }
	    flagforinitdice++;
	}
	
	
	
	public Dice[] getDices() {
		return dices;
	}




	public void setDices(Dice[] dices) {
		this.dices = dices;
	}




	/**
	 * Draw the die to the board, provided this HBox is drawn as well.
	 * @param instance instance where the dices are single, double or default.
	 */
	private void drawDices(DieInstance instance) {
		getChildren().clear();
		int numDices = getNumDices(instance);
		System.out.println(numDices);
		int i = 0;
		for (; i < numDices; i++) {
			if (numDices==4) {
				getChildren().add(new Dice(dices[0]));
			}
			getChildren().add(dices[i]);
			
		}
	}
	
	/**
	 * Returns an array of integers, containing the result of each dice roll.
	 * @return result of each dice roll in terms of an array of integers.
	 */
	public DieResults getTotalRoll(DieInstance instance) {
		if (flagforinitdice<2)
			initDices(GameMode.getInstance().getMode());
		int numDices = getNumDices(DieInstance.DEFAULT);
		DieResults res = new DieResults();
		for (int i = 0; i < numDices; i++) {
			res.add(dices[i].draw(dices[i].roll()));
		}
		
		if (isDouble(res)) {
			res = addDoubleDie(res);
		}else
			drawDices(instance);

		return res;
	}
	
	/**
	 * Checks if result of die roll is a double instance.
	 * @param res, result of die roll.
	 * @return boolean value indicating if so.
	 */
	private boolean isDouble(DieResults res) {
	    if (res.size() > 1) {
	        Dice first = res.getFirst();
	        Dice second = res.get(1);
	        return first.equalsValueOf(second);
	    }
	    return false;
	}

	
	/**
	 * Doubles the current cube objects in dices as well as the roll die result.
	 * @param res the roll die result.
	 * @return double the roll die result.
	 */
	private DieResults addDoubleDie(DieResults res) {
		int numberOfDices = getNumDices(DieInstance.DOUBLE);
		DieResults newRes = new DieResults();
		for (int i = 0; i < numberOfDices; i++) {
			newRes.add(dices[0].draw(res.getFirst().getDiceResult()));
		}
		drawDices(DieInstance.DOUBLE);
		return newRes;
	}
	
	/**
	 * Returns the number of dices based on the die instance.
	 * @param instance instance where the dices are single, double or default.
	 * @return number of dices.
	 */
	private int getNumDices(DieInstance instance) {
		int numDices = 0;
		switch (instance) {
			case SINGLE:
				numDices = 1;
				break;
			case DOUBLE:
				numDices = dices.length;
				break;
			case DEFAULT:
				numDices = dices.length/2;
				break;
		}
		
		return numDices;
	}
	
	// Used to hard-create double rolls, added in Board's calculateMoves() method.
	// Activated by FORCE_DOUBLE_INSTANCE constant in GameConstants.
	public DieResults getDoubleRoll(DieInstance instance) {
		int numberOfDices = getNumDices(instance);
		int randomRoll = dices[0].roll();
		DieResults res = new DieResults();
		for (int i = 0; i < numberOfDices; i++) {
			res.add(dices[i].draw(randomRoll));
		}
		drawDices(instance);
		return res;
	}
	
	// Used to hard-create double rolls of ones, added in Board's calculateMoves() method.
	// Activated by FORCE_DOUBLE_ONES constant in GameConstants.
	public DieResults getDoubleOnes(DieInstance instance) {
		int numberOfDices = getNumDices(instance);
		DieResults res = new DieResults();
		for (int i = 0; i < numberOfDices; i++) {
			res.add(dices[i].draw(1));
		}
		drawDices(instance);
		return res;
	}
	
	// Used to hard-create double rolls of ones, added in Board's calculateMoves() method.
	// Activated by FORCE_DOUBLE_TWOS constant in GameConstants.
	public DieResults getDoubleTwos(DieInstance instance) {
		int numberOfDices = getNumDices(instance);
		DieResults res = new DieResults();
		for (int i = 0; i < numberOfDices; i++) {
			res.add(dices[i].draw(2));
		}
		drawDices(instance);
		return res;
	}
	
	public void reset() {
		getChildren().clear();
	}
}
