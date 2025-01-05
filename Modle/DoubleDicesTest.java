//import static org.junit.Assert.*;
//
//import java.awt.Color;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import game.Dices;
//import game.DieResults;
//
//public class DoubleDicesTest {
//	 private Dices dices;
//	  @Before
//	    public void setUp() {
//	        // יוצרים אובייקט קוביות עם צבע אדום ו-3 קוביות (סה"כ 6 לפי הקוד)
//	        dices = new Dices("easy");
//	    }
//
//	    @Test
//	    public void testAddDoubleDie() {
//	        // Arrange
//	        DieResults initialResult = new DieResults();
//	        
//
//	        // Act
//	        DieResults doubledResult = dices.addDoubleDie(initialResult);
//
//	        // Assert
//	        assertNotNull("The result should not be null.", doubledResult);
//	        assertEquals("The doubled result should have twice the initial size.", 
//	                     initialResult.size() * 2, doubledResult.size());
//	        
//	        // בודקים שכל התוצאות שוות ל-2 (לפי הדאבל המקורי)
//	        for (int i = 0; i < doubledResult.size(); i++) {
//	            assertEquals("Each value in the result should match the doubled die value.", 
//	                         2, doubledResult.get(i).getDiceResult());
//	        }
//	    }
//	}
