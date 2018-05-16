import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.chessgame.model.Alliance;
import com.chessgame.model.pieces.Piece;
import com.chessgame.model.xml.Chess;
import com.chessgame.model.xml.ChessGame;
import com.chessgame.model.xml.XMLPiece;

public class XMLTest {
    
    XMLPiece xmlPiece = new XMLPiece();
    ChessGame chessGame = new ChessGame();
    Chess chess = new Chess();
    
    @Before
    public void initalize() {
        xmlPiece.setAlliance(Alliance.BLACK);
        xmlPiece.setIsFirstMove(true);
        xmlPiece.setTileCoordinate(10);
        xmlPiece.setXMLPieceType(Piece.PieceType.PAWN);
        
        ArrayList<XMLPiece> pieceList = new ArrayList<>();
        pieceList.add(xmlPiece);
        
        chessGame.setId("first");
        chessGame.setPieceList(pieceList);
        chessGame.setXMLnextMoveMaker(Alliance.WHITE);
        
        chess.setChessGame(chessGame);
    }
    
    @Test
    public void testXMLModel() {
        assertEquals(this.xmlPiece.getAlliance(), Alliance.BLACK);
        assertFalse(this.xmlPiece.getAlliance() == Alliance.WHITE);
        assertTrue(this.xmlPiece.getIsFirstMove());
        assertEquals(xmlPiece.getTileCoordinate(), 10);
        assertEquals(xmlPiece.getXMLPieceType(), Piece.PieceType.PAWN);
        
        assertEquals(chessGame.getId(), "first");
        assertEquals(chessGame.getPieceList().size(), 1);
        assertEquals(chessGame.getXMLnextMoveMaker(), Alliance.WHITE);
        
        assertEquals(chess.getChessGame(), chessGame);
    }
    
    
}
