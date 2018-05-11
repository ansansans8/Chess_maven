package com.chessgame.model.xml;

import com.chessgame.model.Alliance;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import com.chessgame.model.pieces.Piece.PieceType;

@XmlRootElement(name = "piece")
public class XMLPiece {
    
    private PieceType XMLPieceType;
    private Alliance alliance;
    private int tileCoordinate;
    private boolean isFirstMove;

    @XmlAttribute(name="type")
    public PieceType getXMLPieceType() {
        return XMLPieceType;
    }
    
    public void setXMLPieceType(PieceType pieceType) {
        this.XMLPieceType = pieceType;
    }
    
    @XmlAttribute(name="alliance")
    public Alliance getAlliance() {
        return alliance;
    }

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }
    
    @XmlAttribute(name="position")
    public int getTileCoordinate() {
        return tileCoordinate;
    }

    public void setTileCoordinate(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    
    @XmlAttribute(name="firstMove")
    public boolean getIsFirstMove() {
        return isFirstMove;
    }
    
    public void setIsFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }
    
    @Override
    public String toString() {
        return "piece type= " + XMLPieceType + " alliance= " + alliance + " position= " + tileCoordinate + " isFirstMove is " + isFirstMove;
    }
}