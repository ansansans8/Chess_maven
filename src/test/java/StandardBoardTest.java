import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.Board.Builder;
import com.chessgame.model.board.Move;
import com.chessgame.model.board.Tile;
import com.chessgame.model.pieces.Bishop;
import com.chessgame.model.pieces.King;
import com.chessgame.model.pieces.Knight;
import com.chessgame.model.pieces.Pawn;
import com.chessgame.model.pieces.Queen;
import com.chessgame.model.pieces.Rook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class StandardBoardTest {
    
    final Board board = Board.createStandardBoard();
    final Rook blackLeftRook = (Rook) board.getTile(0).getPiece();
    final Knight blackLeftKnight = (Knight) board.getTile(1).getPiece();
    final Bishop blackLeftBishop = (Bishop) board.getTile(2).getPiece();
    final Queen blackQueen = (Queen) board.getTile(3).getPiece();
    final King blackKing = (King) board.getTile(4).getPiece();    
    final Bishop blackRightBishop = (Bishop) board.getTile(5).getPiece();
    final Knight blackRightKnight = (Knight) board.getTile(6).getPiece();
    final Rook blackRightRook = (Rook) board.getTile(7).getPiece();
    final Pawn blackPawn1 = (Pawn) board.getTile(8).getPiece();
    final Pawn blackPawn2 = (Pawn) board.getTile(9).getPiece();
    final Pawn blackPawn3 = (Pawn) board.getTile(10).getPiece();
    final Pawn blackPawn4 = (Pawn) board.getTile(11).getPiece();
    final Pawn blackPawn5 = (Pawn) board.getTile(12).getPiece();
    final Pawn blackPawn6 = (Pawn) board.getTile(13).getPiece();
    final Pawn blackPawn7 = (Pawn) board.getTile(14).getPiece();
    final Pawn blackPawn8 = (Pawn) board.getTile(15).getPiece();
    
    final Rook whiteLeftRook = (Rook) board.getTile(56).getPiece();
    final Knight whiteLeftKnight = (Knight) board.getTile(57).getPiece();
    final Bishop whiteLeftBishop = (Bishop) board.getTile(58).getPiece();
    final Queen whiteQueen = (Queen) board.getTile(59).getPiece();
    final King whiteKing = (King) board.getTile(60).getPiece();    
    final Bishop whiteRightBishop = (Bishop) board.getTile(61).getPiece();
    final Knight whiteRightKnight = (Knight) board.getTile(62).getPiece();
    final Rook whiteRightRook = (Rook) board.getTile(63).getPiece();
    final Pawn whitePawn1 = (Pawn) board.getTile(48).getPiece();
    final Pawn whitePawn2 = (Pawn) board.getTile(49).getPiece();
    final Pawn whitePawn3 = (Pawn) board.getTile(50).getPiece();
    final Pawn whitePawn4 = (Pawn) board.getTile(51).getPiece();
    final Pawn whitePawn5 = (Pawn) board.getTile(52).getPiece();
    final Pawn whitePawn6 = (Pawn) board.getTile(53).getPiece();
    final Pawn whitePawn7 = (Pawn) board.getTile(54).getPiece();
    final Pawn whitePawn8 = (Pawn) board.getTile(55).getPiece();
    
   
    @Test
    public void testBlackPiecesAreEmpty() {
        assertFalse(board.getBlackPieces().isEmpty());
    }
    
    @Test
    public void testBlackPiecesSizeEqualsTo16() {
        assertEquals(board.getBlackPieces().size(), 16);
    }
    
    @Test
    public void testBlackPiecesSizeLessThan16() {
        assertFalse(board.getBlackPieces().size() < 16);
    }
    
    @Test
    public void testBlackPiecesSizeGreaterThan16() {
        assertFalse(board.getBlackPieces().size() > 16);
    }
    
    @Test
    public void testBlackPiecesContainsLeftRook() {
        assertTrue(board.getBlackPieces().contains(blackLeftRook));
    }
    
    @Test
    public void testBlackPiecesContainsLeftBishop() {
        assertTrue(board.getBlackPieces().contains(blackLeftBishop));
    }
    
    @Test
    public void testBlackPiecesContainsQueen() {
        assertTrue(board.getBlackPieces().contains(blackQueen));
    }
    
    @Test
    public void testBlackPiecesContainsKing() {
        assertTrue(board.getBlackPieces().contains(blackKing));
    }
    
    @Test
    public void testBlackPiecesContainsRightBishop() {
        assertTrue(board.getBlackPieces().contains(blackRightBishop));
    }
    
    @Test
    public void testBlackPiecesContainsRightKnight() {
        assertTrue(board.getBlackPieces().contains(blackRightKnight));
    }
    
    @Test
    public void testBlackPiecesContainsRightRook() {
        assertTrue(board.getBlackPieces().contains(blackRightRook));
    }
    
    @Test
    public void testBlackPiecesContainsPawn1() {
        assertTrue(board.getBlackPieces().contains(blackPawn1));
    }
    
    @Test
    public void testBlackPiecesContainsPawn2() {
        assertTrue(board.getBlackPieces().contains(blackPawn2));
    }
    
    @Test
    public void testBlackPiecesContainsPawn3() {
        assertTrue(board.getBlackPieces().contains(blackPawn3));
    }
    
    @Test
    public void testBlackPiecesContainsPawn4() {
        assertTrue(board.getBlackPieces().contains(blackPawn4));
    }
    
    @Test
    public void testBlackPiecesContainsPawn5() {
        assertTrue(board.getBlackPieces().contains(blackPawn5));
    }
    
    @Test
    public void testBlackPiecesContainsPawn6() {
        assertTrue(board.getBlackPieces().contains(blackPawn6));
    }
    
    @Test
    public void testBlackPiecesContainsPawn7() {
        assertTrue(board.getBlackPieces().contains(blackPawn7));
    }
    
    @Test
    public void testBlackPiecesContainsWhiteKing() {
        assertFalse(board.getBlackPieces().contains(whiteKing));
    }
    
    
    
    
    
    @Test
    public void testWhitePiecesAreEmpty() {
        assertFalse(board.getWhitePieces().isEmpty());
    }
    
    @Test
    public void testWhitePiecesSizeEqualsTo16() {
        assertEquals(board.getWhitePieces().size(), 16);
    }
    
    @Test
    public void testWhitePiecesSizeLessThan16() {
        assertFalse(board.getWhitePieces().size() < 16);
    }
    
    @Test
    public void testWhitePiecesSizeGreaterThan16() {
        assertFalse(board.getWhitePieces().size() > 16);
    }
        
    @Test
    public void testWhitePiecesContainsLeftRook() {
        assertTrue(board.getWhitePieces().contains(whiteLeftRook));
    }
    
    @Test
    public void testWhitePiecesContainsLeftBishop() {
        assertTrue(board.getWhitePieces().contains(whiteLeftBishop));
    }
    
    @Test
    public void testWhitePiecesContainsQueen() {
        assertTrue(board.getWhitePieces().contains(whiteQueen));
    }
    
    @Test
    public void testWhitePiecesContainsKing() {
        assertTrue(board.getWhitePieces().contains(whiteKing));
    }
    
    @Test
    public void testWhitePiecesContainsRightBishop() {
        assertTrue(board.getWhitePieces().contains(whiteRightBishop));
    }
    
    @Test
    public void testWhitePiecesContainsRightKnight() {
        assertTrue(board.getWhitePieces().contains(whiteRightKnight));
    }
    
    @Test
    public void testWhitePiecesContainsRightRook() {
        assertTrue(board.getWhitePieces().contains(whiteRightRook));
    }
    
    @Test
    public void testWhitePiecesContainsPawn1() {
        assertTrue(board.getWhitePieces().contains(whitePawn1));
    }
    
    @Test
    public void testWhitePiecesContainsPawn2() {
        assertTrue(board.getWhitePieces().contains(whitePawn2));
    }
    
    @Test
    public void testWhitePiecesContainsPawn3() {
        assertTrue(board.getWhitePieces().contains(whitePawn3));
    }
    
    @Test
    public void testWhitePiecesContainsPawn4() {
        assertTrue(board.getWhitePieces().contains(whitePawn4));
    }
    
    @Test
    public void testWhitePiecesContainsPawn5() {
        assertTrue(board.getWhitePieces().contains(whitePawn5));
    }
    
    @Test
    public void testWhitePiecesContainsPawn6() {
        assertTrue(board.getWhitePieces().contains(whitePawn6));
    }
    
    @Test
    public void testWhitePiecesContainsPawn7() {
        assertTrue(board.getWhitePieces().contains(whitePawn7));
    }
    
    @Test
    public void testWhitePiecesContainsBlackKing() {
        assertFalse(board.getWhitePieces().contains(blackKing));
    }
    
    
        
    @Test
    public void testZeroPositionTileHasPiece() {
        assertNotNull(board.getTile(0).getPiece());
    }
        
    @Test
    public void test15PositionTileHasPiece() {
        assertNotNull(board.getTile(15).getPiece());
    }
        
    @Test
    public void test16PositionTileHasPiece() {
        assertNull(board.getTile(16).getPiece());
    }
        
    @Test
    public void test40PositionTileHasPiece() {
        assertNull(board.getTile(40).getPiece());
    }
        
    @Test
    public void test47PositionTileHasPiece() {
        assertNull(board.getTile(47).getPiece());
    }
        
    @Test
    public void test48PositionTileHasPiece() {
        assertNotNull(board.getTile(48).getPiece());
    }
        
    @Test
    public void test63PositionTileHasPiece() {
        assertNotNull(board.getTile(63).getPiece());
    }
    
    @Test
    public void testEmptyTilePiece() {
        assertEquals(board.getTile(20), Tile.createTile(20, null));
    }
    
}