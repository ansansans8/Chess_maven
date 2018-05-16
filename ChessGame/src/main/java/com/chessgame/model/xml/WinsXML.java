package com.chessgame.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public interface WinsXML {
    
    @XmlRootElement(name = "whiteWins")
    public class WhiteWins {
        
        private int numberOfWins;

        @XmlAttribute(name="wins")
        public int getNumberOfWins() {
            return numberOfWins;
        }

        public void setNumberOfWins(int numberOfWins) {
            this.numberOfWins = numberOfWins;
        }        
        
    }
    
    @XmlRootElement(name = "blackWins")
    public class BlackWins {
        
        private int numberOfWins;

        @XmlAttribute(name="wins")
        public int getNumberOfWins() {
            return numberOfWins;
        }

        public void setNumberOfWins(int numberOfWins) {
            this.numberOfWins = numberOfWins;
        }
        
    }
    
}
