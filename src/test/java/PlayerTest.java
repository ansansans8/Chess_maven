import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.Board.Builder;
import com.chessgame.model.board.Move;
import com.chessgame.model.pieces.Bishop;
import com.chessgame.model.pieces.King;
import com.chessgame.model.pieces.Pawn;
import com.chessgame.model.pieces.Queen;
import com.chessgame.model.pieces.Rook;
import com.chessgame.model.player.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PlayerTest {
    
    final Board board = Board.createStandardBoard();
    Player whitePlayer = board.getWhitePlayer();
    Player blackPlayer = board.getBlackPlayer();
    Player player = board.getCurrentPlayer();
    
    @Test
    public void testLegitBoardBlackWithCurrentPlayer() {
        assertNotNull(player.getPlayerKing());
    }
    
    @Test
    public void testLegitBoardWithWhitePlayer() {
        assertNotNull(whitePlayer.getPlayerKing());
    }
    
    @Test
    public void testLegitBoardWithBlackPlayer() {
        assertNotNull(blackPlayer.getPlayerKing());
    }
    
    @Test(expected = RuntimeException.class)
    public void testBoardWithoutKing() {
        final Board.Builder bu = new Board.Builder();
        final Board bo = bu.build();
    }
    
    @Test(expected = RuntimeException.class)
    public void testBoardWithoutWhiteKing() {
        final Board.Builder bu = new Board.Builder();
        bu.setPiece(new King(0, Alliance.BLACK));
        final Board bo = bu.build();
    }
    
    @Test(expected = RuntimeException.class)
    public void testBoardWithoutBlackKing() {
        final Board.Builder bu = new Board.Builder();
        bu.setPiece(new King(0, Alliance.WHITE));
        final Board bo = bu.build();
    }
    
    @Test
    public void testCurrentPlayer() {
        assertNotNull(player);
    }
    
    @Test
    public void testCurrentPlayerEqualsToWhitePlayer() {
        assertEquals(player, whitePlayer);
    }
    
    @Test
    public void testCurrentPlayerEqualsToBlackPlayer() {
        assertFalse(player == blackPlayer);
    }
    
    @Test
    public void testCurrentPlayerOpponent() {
        assertNotNull(player.getOpponent());
    }
    
    @Test
    public void testCurrentPlayerAlliance() {
        assertTrue(player.getAlliance() == Alliance.WHITE);
    }
    
    @Test
    public void testCurrentPlayerOpponentAlliance() {
        assertTrue(player.getOpponent().getAlliance() == Alliance.BLACK);
    }
    
    @Test
    public void testCurrentPlayerIsInStaleMate() {
        assertFalse(player.inInStaleMate());
    }
    
    @Test
    public void testCurrentPlayerIsCastled() {
        assertFalse(player.isCastled());
    }
    
    @Test
    public void testCurrentPlayerIsInCheckMate() {
        assertFalse(player.isInCheckMate());
    }
    
    @Test
    public void testCurrentPlayerIsLegalMove() {
        assertTrue(player.isMoveLegal(player.getLegalMoves().stream().findFirst().get()));
    }
    
    @Test
    public void testCurrentPlayerOpponentIsInStaleMate() {
        assertFalse(player.getOpponent().inInStaleMate());
    }
    
    @Test
    public void testCurrentPlayerOpponentIsCastled() {
        assertFalse(player.getOpponent().isCastled());
    }
    
    @Test
    public void testCurrentPlayerOpponentIsInCheckMate() {
        assertFalse(player.getOpponent().isInCheckMate());
    }
        
    @Test
    public void testCurrentPlayerLegalMovesSizeIs20() {
        assertEquals(player.getLegalMoves().size(), 20);
    }
    
    @Test
    public void testCurrentPlayerLegalMovesSizeLessThan20() {
        assertFalse(player.getLegalMoves().size() < 20);
    }
    
    @Test
    public void testCurrentPlayerLegalMovesSizeGreaterThan20() {
        assertFalse(player.getLegalMoves().size() > 20);
    }
    
    @Test
    public void testCurrentPlayerOpponentLegalMovesSizeIs20() {
        assertEquals(player.getOpponent().getLegalMoves().size(), 20);
    }
    
    @Test
    public void testCurrentPlayerOpponentLegalMovesSizeLessThan20() {
        assertFalse(player.getOpponent().getLegalMoves().size() < 20);
    }
    
    @Test
    public void testCurrentPlayerOpponentLegalMovesSizeGreaterThan20() {
        assertFalse(player.getOpponent().getLegalMoves().size() > 20);
    }
    
    @Test
    public void testWhitePlayerActivePiecesHasKing() {
        assertTrue(whitePlayer.getActivePieces().contains(whitePlayer.getPlayerKing()));
    }
    
    @Test
    public void testWhitePlayerOpponentActivePiecesHasKing() {
        assertTrue(whitePlayer.getOpponent().getActivePieces().contains(whitePlayer.getOpponent().getPlayerKing()));
    }
        
    @Test
    public void testWhitePlayerIsInCheckMate() {
        assertFalse(whitePlayer.isInCheckMate());
    }
    
    @Test
    public void testWhitePlayerIsInStaleMate() {
        assertFalse(whitePlayer.inInStaleMate());
    }
    
    @Test
    public void testWhitePlayerIsCastled() {
        assertFalse(whitePlayer.isCastled());
    }
    
    @Test
    public void testWhitePlayerIsInCheck() {
        assertFalse(whitePlayer.isInCheck());
    }
    
    @Test
    public void testWhitePlayerIsLegalMove() {
        assertTrue(whitePlayer.isMoveLegal(whitePlayer.getLegalMoves().stream().findFirst().get()));
    }
    
    @Test
    public void testWhitePlayerActivePiecesIsEmpty() {
        assertFalse(whitePlayer.getActivePieces().isEmpty());
    }
    
    @Test
    public void testBlackPlayerActivePiecesHasKing() {
        assertTrue(blackPlayer.getActivePieces().contains(blackPlayer.getPlayerKing()));
    }
    
    @Test
    public void testBlackPlayerOpponentActivePiecesHasKing() {
        assertTrue(blackPlayer.getOpponent().getActivePieces().contains(blackPlayer.getOpponent().getPlayerKing()));
    }
        
    @Test
    public void testBlackPlayerIsInCheckMate() {
        assertFalse(blackPlayer.isInCheckMate());
    }
    
    @Test
    public void testBlackPlayerIsInStaleMate() {
        assertFalse(blackPlayer.inInStaleMate());
    }
    
    @Test
    public void testBlackPlayerIsCastled() {
        assertFalse(blackPlayer.isCastled());
    }
    
    @Test
    public void testBlackPlayerIsInCheck() {
        assertFalse(blackPlayer.isInCheck());
    }
    
    @Test
    public void testBlackPlayerIsLegalMove() {
        assertTrue(blackPlayer.isMoveLegal(blackPlayer.getLegalMoves().stream().findFirst().get()));
    }
    
    @Test
    public void testBlackPlayerActivePiecesIsEmpty() {
        assertFalse(blackPlayer.getActivePieces().isEmpty());
    }
 
}
