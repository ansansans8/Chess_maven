package com.chessgame.model.xml;

import java.io.File;

public class XMLUtils {
    
    public final static String PATH_TO_XML = "./saved_games/elso.xml";
    public static File XML_FILE  = new File(PATH_TO_XML);
    public static int ID_COUNTER = 0;
    public static boolean FILE_EXISTS = isFileExists();
    
    public static boolean isFileExists() {
        File f = new File(PATH_TO_XML);
        return f.exists();
    }
    
}
