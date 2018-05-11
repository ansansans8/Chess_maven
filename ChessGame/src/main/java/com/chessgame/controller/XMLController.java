package com.chessgame.controller;

import java.io.File;
import com.chessgame.model.xml.Chess;
import com.chessgame.model.xml.ChessGame;
import com.chessgame.model.xml.XMLPiece;
import static com.chessgame.model.xml.XMLUtils.*;
import com.chessgame.model.Alliance;

import com.chessgame.model.board.Board;
import com.chessgame.model.pieces.Bishop;
import com.chessgame.model.pieces.King;
import com.chessgame.model.pieces.Knight;
import com.chessgame.model.pieces.Pawn;
import com.chessgame.model.pieces.Piece;
import com.chessgame.model.pieces.Queen;
import com.chessgame.model.pieces.Rook;
import com.chessgame.model.player.Player;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XMLController {
    
    private XMLIn xmlIn;
    private final XMLOut xmlOut;
    
    public XMLController() {
        this.xmlIn = new XMLIn();
        this.xmlOut = new XMLOut();
    }
    
    public XMLIn getXmlIn() {
        return xmlIn;
    }

    public void setXmlIn(XMLIn xmlIn) {
        this.xmlIn = xmlIn;
    }

    public XMLOut getXmlOut() {
        return xmlOut;
    }

    public void setXmlOut(List<Piece> piecesOnTheBoard, Player currentPlayer, String savedGameID) {
        this.xmlOut.setCurrentPlayer(currentPlayer);
        this.xmlOut.setPiecesOnTheBoard(piecesOnTheBoard);
        this.xmlOut.setSavedGameID(savedGameID);
    }
    
    public class XMLIn {
    
        public  Board createStandardBoardFromXML() throws JAXBException {

            if (FILE_EXISTS) {

                JAXBContext jaxbContext = JAXBContext.newInstance(Chess.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                Chess outChess = (Chess) jaxbUnmarshaller.unmarshal(XML_FILE);

                final Board.Builder builder = new Board.Builder();

                for (XMLPiece piece : outChess.getChessGame().getPieceList()) {
                    switch (piece.getXMLPieceType().toString()) {

                        case "P" :
                            builder.setPiece(new Pawn(piece.getTileCoordinate(), piece.getAlliance(), piece.getIsFirstMove()));
                            break;

                        case "R" : 
                            builder.setPiece(new Rook(piece.getTileCoordinate(), piece.getAlliance()));
                            break;

                        case "Q" : 
                            builder.setPiece(new Queen(piece.getTileCoordinate(), piece.getAlliance()));
                            break;

                        case "K" : 
                            builder.setPiece(new King(piece.getTileCoordinate(), piece.getAlliance()));
                            break;

                        case "N" : 
                            builder.setPiece(new Knight(piece.getTileCoordinate(), piece.getAlliance()));
                            break;    

                        case "B" : 
                            builder.setPiece(new Bishop(piece.getTileCoordinate(), piece.getAlliance()));
                            break;    
                    }
                }
                builder.setMoveMaker(outChess.getChessGame().getXMLnextMoveMaker());

                return builder.build();
            }
            else {
                System.out.println("ez");
                return Board.createStandardBoard();
            }
        }
    }
    
    public class XMLOut {
    
        private List<Piece> piecesOnTheBoard;
        private Player currentPlayer;
        private String savedGameID;

        public XMLOut() {
            this.piecesOnTheBoard = new ArrayList<>();
        }
        
        public void setPiecesOnTheBoard(List<Piece> piecesOnTheBoard) {
            this.piecesOnTheBoard = piecesOnTheBoard;
        }

        public void setCurrentPlayer(Player currentPlayer) {
            this.currentPlayer = currentPlayer;
        }
        
        public void setSavedGameID(String savedGameID) {
            this.savedGameID = savedGameID;
        }

        public void createXML() throws JAXBException {

            final XMLBuilder builder = new XMLBuilder();

            piecesOnTheBoard.forEach((piece) -> {
                builder.putPieceToXML(piece);
            });

            builder.setMoveMaker(this.currentPlayer.getAlliance());
            builder.setGameId(savedGameID);

            builder.build();
        }

        public class XMLBuilder {

            private ChessGame chessGame;
            private ArrayList<XMLPiece> xmlPieceList;
            private Alliance nextMoveMaker;
            private Chess chess;
            private String savedGameID;
            private File outFile;

            public XMLBuilder() {
                this.xmlPieceList = new ArrayList<>();
            }

            public void putPieceToXML(final Piece piece) {

                XMLPiece xmlPiece = new XMLPiece();
                xmlPiece.setXMLPieceType(piece.getPieceType());
                xmlPiece.setAlliance(piece.getPieceAlliance());
                xmlPiece.setTileCoordinate(piece.getPiecePosition());
                xmlPiece.setIsFirstMove(piece.isFirstMove());

                xmlPieceList.add(xmlPiece);
            }

            public void setMoveMaker (final Alliance nextMoveMaker) {
                this.nextMoveMaker = nextMoveMaker;
            }
            
            private void setGameId(String savedGameID) {
                this.savedGameID =  savedGameID;
                setOutFile(savedGameID);
            }
            
            private void setOutFile(String outFile) {
                this.outFile = new File(PATH_TO_XML.concat(outFile));
            }

            public void build() throws JAXBException {
                chessGame = new ChessGame();

                chessGame.setPieceList(xmlPieceList);
                chessGame.setXMLnextMoveMaker(nextMoveMaker);
                chessGame.setId(savedGameID);

                chess = new Chess();

                chess.setChessGame(chessGame);

                JAXBContext context = JAXBContext.newInstance(Chess.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                marshaller.marshal(chess, outFile);
            }
        }
    }
}
