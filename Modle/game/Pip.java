package game;

import java.io.IOException;
import java.io.InputStream;

import constants.GameConstants;
import interfaces.ColorParser;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;

/**
 * This class represents the Pip object in Backgammon.
 * This class helps BoardComponents class to initialize the checkers for each pip object.
 * This class also adds the checkers objects to the pip object, to be drawn to the stage.
 * 
 * @teamname TeaCup
 * @author Bryan Sng, 17205050
 * @author @LxEmily, 17200573
 * @author Braddy Yeoh, 17357376
 *
 */
public class Pip extends CheckersStorer implements ColorParser {
	private Background normalBG;
	private Background highlightedBG; 
	private int pipNum;
	private char type;
	/**
	 * Default Constructor
	 * 		- Initialize the img and imgHighlighteded instance variable of the pip.
	 * 		- Set this pip's transformation, alignment, size, etc.
	 * 		- Set that img to be the background of this pip.
	 * 		- Initialize this pip's listeners.
	 * 
	 * @param color of the pip.
	 * @param rotation either 0 or 180. 0 = pointing upwards. 180 = pointing downwards. 
	 */
	public Pip(Color color, double rotation, int pipNum) {
		super();
		this.pipNum = pipNum;
		this.type='r';
		String colorString = parseColor(color);
		 
		System.out.println("The color is: "+colorString);/*******************Debug*********************/
		
		InputStream input1 = getClass().getResourceAsStream("img/board/" + colorString + "_point.png");
		InputStream input2 = getClass().getResourceAsStream("img/board/" + colorString + "_point_highlighted.png");
		normalBG = new Background(new BackgroundImage(new Image(input1), null, null, null, null));
		highlightedBG = new Background(new BackgroundImage(new Image(input2), null, null, null, null));
		try {
			input1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			input2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setRotate(rotation);
		setAlignment(Pos.BOTTOM_CENTER);
		// don't simply set point max and pref size, this will effect how the point is drawn.
		setMinSize(GameConstants.getPipSize().getWidth(), GameConstants.getPipSize().getHeight());	// highlighted and non-highlighted should have the same width & height.
		setNormalImage();
	}
	
	public Pip(Color color, double rotation, int pipNum, char type) {
		super();
		this.pipNum = pipNum;
		this.type=type;
		String colorString = parseColor(color);
		 
		System.out.println("The "+ this.type + " color is: "+colorString);/*******************Debug*********************/
		
		//choose pip image based on type
		String imgPath="img/board/" + colorString + "_point.png";
		String highlightedImgPath="img/board/" + colorString + "_point_highlighted.png";
		if(this.type=='q' || this.type=='s') {
			imgPath="img/board/customized/" + colorString + "_point.png";
			highlightedImgPath="img/board/customized/" + colorString + "_point_highlighted.png";
		}
			
		InputStream input1 = getClass().getResourceAsStream(imgPath);
		InputStream input2 = getClass().getResourceAsStream(highlightedImgPath);
		normalBG = new Background(new BackgroundImage(new Image(input1), null, null, null, null));
		highlightedBG = new Background(new BackgroundImage(new Image(input2), null, null, null, null));
		try {
			input1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			input2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setRotate(rotation);
		setAlignment(Pos.BOTTOM_CENTER);
		// don't simply set point max and pref size, this will effect how the point is drawn.
		setMinSize(GameConstants.getPipSize().getWidth(), GameConstants.getPipSize().getHeight());	// highlighted and non-highlighted should have the same width & height.
		setNormalImage();
	}
	
	public void setType(char type) {
		this.type=type;
	}
	
	public char getType(){
		return this.type;
	}
	
	/**
	 * Use the highlighted image.
	 */
	public void setHighlightImage() {
		setBackground(highlightedBG);
	}

	/**
	 * Use the normal image (i.e. image that is not highlighted).
	 */
	public void setNormalImage() {
		setBackground(normalBG);
	}
	
	/**
	 * Returns the pointNum instance variable (the number the point represents).
	 * @return the pointNum instance variable.
	 */
	public int getPipNumber() {
		return pipNum;
	}
}
