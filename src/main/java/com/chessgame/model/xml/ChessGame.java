package com.chessgame.model.xml;

import com.chessgame.model.Alliance;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChessGame")
public class ChessGame {

    private ArrayList<XMLPiece> pieceList;
    private String id;
    private Alliance XMLnextMoveMaker;
    
    @XmlElementWrapper
    List<String> nullCollection;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @XmlAttribute(name="nextPlayer")
    public Alliance getXMLnextMoveMaker() {
        return XMLnextMoveMaker;
    }

    public void setXMLnextMoveMaker(Alliance XMLnextMoveMaker) {
        this.XMLnextMoveMaker = XMLnextMoveMaker;
    }

    @XmlElementWrapper(name = "board")
    @XmlElement(name = "piece")
    public ArrayList<XMLPiece> getPieceList() {
        return pieceList;
    }

    public void setPieceList(ArrayList<XMLPiece> pieceList) {
        this.pieceList = pieceList;
    }
}