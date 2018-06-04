package com.chessgame.model.xml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class XMLUtils {
    
    public static final String PATH_TO_XML = System.getProperty("user.dir") + "Saves";
    public static final File SAVE_DIRECTORY = createSaveDirectory();
    
    private static File createSaveDirectory() {
    	File dir = new File(PATH_TO_XML + "/");
    	boolean isCreated = dir.mkdirs();
    	
    	return dir;
    }
    
    public List<String> savedGameNames() {
    	File[] listOfFiles = SAVE_DIRECTORY.listFiles();
    	List<String> names = new ArrayList<>();
    	
    	for (File file : listOfFiles) {
    		if (file.isFile()) {
    			names.add(file.getName().substring(0, file.getName().length() - 4));
    		}
    	}
    	
    	return names;
    }
    
}
