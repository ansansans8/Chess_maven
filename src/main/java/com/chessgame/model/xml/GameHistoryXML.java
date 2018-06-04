package com.chessgame.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.chessgame.model.xml.WinsXML.BlackWins;
import com.chessgame.model.xml.WinsXML.WhiteWins;

@XmlRootElement(name = "gameHistory")
public class GameHistoryXML {
    
    private WhiteWins ww;
    private BlackWins bw;
    
    @XmlElement(name = "whiteWins")
    public WhiteWins getWw() {
        return ww;
    }

    public void setWw(WhiteWins ww) {
        this.ww = ww;
    }

     @XmlElement(name = "blackWins")
    public BlackWins getBw() {
        return bw;
    }

    public void setBw(BlackWins bw) {
        this.bw = bw;
    }
}