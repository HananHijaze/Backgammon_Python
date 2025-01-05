import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import move.Move;
import move.PipToPip;
import move.RollMoves;

public class RollMovesTest {
	  private RollMoves rollMoves;

	  
	    @Test
	    public void testAddMove() {
	        rollMoves = new RollMoves();
	        Move move = new PipToPip(5, 10, rollMoves, false);

	        rollMoves.getMoves().add(move);

	        assertEquals("Move should be added to the list", 1, rollMoves.getMoves().size());
	        assertEquals("Move should match the added move", move, rollMoves.getMoves().get(0));
	    }



}
