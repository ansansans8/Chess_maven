
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.Board.Builder;
import com.chessgame.model.board.Move;
import com.chessgame.model.pieces.Bishop;
import com.chessgame.model.pieces.King;
import com.chessgame.model.pieces.Pawn;
import com.chessgame.model.pieces.Queen;
import com.chessgame.model.pieces.Rook;

public class PieceTest {
    
    @Test
    public void testCheck() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(4, Alliance.BLACK));
        builder.setPiece(new King(60, Alliance.WHITE));
        builder.setPiece(new Queen(0, Alliance.WHITE));        
        builder.setMoveMaker(Alliance.BLACK);
        Board board = builder.build();
        
        assertTrue(board.getBlackPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().isInCheckMate());
        assertFalse(board.getCurrentPlayer().inInStaleMate());
        
    }
    
    @Test
    public void testCheckMate() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(4, Alliance.BLACK));
        builder.setPiece(new King(20, Alliance.WHITE));
        builder.setPiece(new Queen(12, Alliance.WHITE));        
        builder.setMoveMaker(Alliance.BLACK);
        Board board = builder.build();
        
        assertTrue(board.getBlackPlayer().isInCheck());
        assertTrue(board.getCurrentPlayer().isInCheckMate());
        assertFalse(board.getCurrentPlayer().inInStaleMate());
        
    }
    
    @Test
    public void testStaleMate() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(0, Alliance.BLACK));
        builder.setPiece(new King(10, Alliance.WHITE));
        builder.setPiece(new Rook(9, Alliance.WHITE));        
        builder.setMoveMaker(Alliance.BLACK);
        Board board = builder.build();
        
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertFalse(board.getBlackPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().isInCheckMate());
        assertTrue(board.getCurrentPlayer().inInStaleMate());
        
    }
    
    @Test
    public void testKingException() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(8, Alliance.BLACK));
        builder.setPiece(new King(7, Alliance.WHITE));
        builder.setPiece(new Pawn(6, Alliance.BLACK, false));
        builder.setMoveMaker(Alliance.WHITE);
        Board board = builder.build();
        
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 3);
        assertEquals(blackLegals.size(), 6);
        
    }
    
    @Test
    public void testPawnPromotion() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(7, Alliance.BLACK));
        builder.setPiece(new King(0, Alliance.WHITE));
        builder.setPiece(new Pawn(12, Alliance.WHITE, false));
        builder.setMoveMaker(Alliance.WHITE);
        Board board = builder.build();
        
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 4);
        assertEquals(blackLegals.size(), 3);
        
    }
    
    @Test
    public void testPawnAttack() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(7, Alliance.BLACK));
        builder.setPiece(new King(0, Alliance.WHITE));
        builder.setPiece(new Pawn(16, Alliance.BLACK, false));
        builder.setPiece(new Pawn(25, Alliance.WHITE, false));
        builder.setMoveMaker(Alliance.WHITE);
        Board board = builder.build();
        
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 5);
        assertEquals(blackLegals.size(), 5);
        assertEquals(board.getTile(25).getPiece().setPossibleMoves(board).size(), 2);
        
    }
    
    @Test
    public void testPawnException() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(5, Alliance.BLACK));
        builder.setPiece(new King(8, Alliance.WHITE));
        builder.setPiece(new Pawn(16, Alliance.WHITE, false));
        builder.setMoveMaker(Alliance.WHITE);
        Board board = builder.build();
        
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 4);
        assertEquals(blackLegals.size(), 5);
        assertEquals(board.getTile(16).getPiece().setPossibleMoves(board).size(), 0);
    }
    
    @Test
    public void testBishop() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(5, Alliance.BLACK));
        builder.setPiece(new King(8, Alliance.WHITE));
        builder.setPiece(new Bishop(17, Alliance.WHITE));
        builder.setMoveMaker(Alliance.WHITE);
        Board board = builder.build();
        
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertEquals(board.getTile(17).getPiece().setPossibleMoves(board).size(), 8);
    }
    
    @Test
    public void testPiece() {
        
        Board.Builder builder = new Builder();
        builder.setPiece(new King(5, Alliance.BLACK));
        builder.setPiece(new King(8, Alliance.WHITE));
        builder.setMoveMaker(Alliance.WHITE);
        Board board = builder.build();
        
        King piece = new King(8, Alliance.WHITE);
        King whiteKing = board.getWhitePlayer().getPlayerKing();
        
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        
        assertEquals(whiteLegals.size(), 5);
        assertEquals(blackLegals.size(), 5);
        
        assertTrue(whiteKing.equals(board.getWhitePlayer().getPlayerKing()));
    }
    
}
