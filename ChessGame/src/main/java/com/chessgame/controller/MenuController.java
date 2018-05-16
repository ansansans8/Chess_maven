package com.chessgame.controller;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

import com.chessgame.model.board.Board;
import com.chessgame.model.pieces.Piece;
import com.chessgame.model.xml.XMLUtils;
import com.chessgame.ui.GUIUtils;

import javafx.scene.control.Label;

import java.awt.ScrollPane;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;


public class MenuController {
        
    private MainController mainController;
    private XMLController xmlController = new XMLController();
    
    public MenuController(MainController boardBorderPane) {
        this.mainController = boardBorderPane;
    }

    public void setNewGameAction(MenuItem newGame) {
        newGame.setOnAction(e -> {
            mainController.setBoard(Board.createStandardBoard());
            mainController.drawBoard();
        });
    }

    public void setExitAction(MenuItem exit) {
        exit.setOnAction(e -> {
            System.exit(0);
        });
    }
    
    public void setSaveGameAction(MenuItem saveGame) {
        saveGame.setOnAction(e -> {
            List<Piece> piecesOnTheBoard = Stream.concat(
                    mainController.getBoard().getBlackPieces().stream(),
                    mainController.getBoard().getWhitePieces().stream())
                        .collect(Collectors.toList());            
            
            Stage saveStage = new Stage();
            saveStage.setTitle("Save");
            
            GridPane saveGridPane = new GridPane();
            saveGridPane.setPadding(new Insets(10, 10, 10, 10));
            saveGridPane.setVgap(5);
            saveGridPane.setHgap(5);
            
            TextField nameField = new TextField();
            nameField.setPromptText("Give the desired name of the saved file!");
            nameField.setPrefColumnCount(10);
            GridPane.setConstraints(nameField, 0, 0);
            saveGridPane.add(nameField, 0, 0);
            
            Button saveButton = new Button("save");
            GridPane.setConstraints(saveButton, 1, 0);
            saveGridPane.getChildren().add(saveButton);

            Label warningMessage = new Label();
            GridPane.setConstraints(warningMessage, 0, 1);
            saveGridPane.getChildren().add(warningMessage);
            
            Scene saveStageScene = new Scene(saveGridPane, 400, 100);
            saveStage.setScene(saveStageScene);
            saveStage.show();
            
            saveButton.setOnAction(f -> {
            	if (nameField.getText().isEmpty()) {
            		warningMessage.setText("You must give a name for the saved game!");
            	}
            	else {            		
                    xmlController.setXmlOut(piecesOnTheBoard,
					        mainController.getBoard().getCurrentPlayer(), nameField.getText());
                    try {
						xmlController.getXmlOut().createXML();
					} catch (JAXBException e1) {
						e1.printStackTrace();
					}
					saveStage.close();
            	}
            });
        });
    }
    
    public void setLoadGameAction(MenuItem loadGame) {
        loadGame.setOnAction(e -> {
        	
        	Stage loadStage = new Stage();
            loadStage.setTitle("Load");
            
            GridPane loadGridPane = new GridPane();
            loadGridPane.setPadding(new Insets(10, 10, 10, 10));
            loadGridPane.setVgap(5);
            loadGridPane.setHgap(5);
            
            TextField nameField = new TextField();
            nameField.setPromptText("Give the name of the saved file!");
            nameField.setPrefColumnCount(10);
            GridPane.setConstraints(nameField, 0, 0);
            loadGridPane.add(nameField, 0, 0);
            
            int i = 1;
            XMLUtils u = new XMLUtils();
            for (String name : u.savedGameNames()) {
            	Label fileName = new Label(name);
            	GridPane.setConstraints(fileName, 0, i);
            	loadGridPane.getChildren().add(fileName);
            	i++;
            }
            
            Button loadButton = new Button("load");
            GridPane.setConstraints(loadButton, 1, 0);
            loadGridPane.getChildren().add(loadButton);

            Label warningMessage = new Label();
            GridPane.setConstraints(warningMessage, 0, i);
            loadGridPane.getChildren().add(warningMessage);
            
            Scene loadStageScene = new Scene(loadGridPane, 400, 400);
            loadStage.setScene(loadStageScene);
            loadStage.show();

            loadButton.setOnAction(f -> {
            	if (nameField.getText().isEmpty()) {
            		warningMessage.setText("You must give an existing filename!");
            	}
            	else if(!u.savedGameNames().contains(nameField.getText())) {
					warningMessage.setText("You must give an existing filename!");
            	}
            	else {            		
            		try {
                    	mainController.setBoard(xmlController.getXmlIn().createStandardBoardFromXML(nameField.getText()));
                        mainController.drawBoard();
					} catch (JAXBException e1) {
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();			
					} 
					loadStage.close();
            	}
            });
        });
    }
    
    public void setShowWinsAction(MenuItem showWins) {
        showWins.setOnAction(e -> {
        });
    }

    public void setFlipBoardAction(MenuItem flipBoardMenu) {
        flipBoardMenu.setOnAction(e -> {
            if (mainController.getBoardDirection() == GUIUtils.BoardDirection.FLIPPED){
                mainController.setBoardDirection(GUIUtils.BoardDirection.NORMAL);
                mainController.drawBoard();

            }
            else {
                mainController.setBoardDirection(GUIUtils.BoardDirection.FLIPPED);
                mainController.drawBoard();
            }
        });

        
    }

    public void setshowLegalMovesAction(CheckMenuItem showLegalMoves) {
        showLegalMoves.setOnAction(e -> {
            if (showLegalMoves.isSelected()){
                mainController.setShowLegalMoves(true);
                }
            else{
                mainController.setShowLegalMoves(false);
            }

        });
    }
        
}
