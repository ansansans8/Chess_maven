package com.chessgame.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Chess")
public class Chess {
    
    private ChessGame chessGame;
    
    @XmlElement(name="chessGame")
    public ChessGame getChessGame() {
        return chessGame;
    }
    
    public void setChessGame(ChessGame chessGame) {
        this.chessGame = chessGame;
    }
}