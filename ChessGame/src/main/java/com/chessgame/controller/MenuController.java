package com.chessgame.controller;

import com.chessgame.model.board.Board;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import com.chessgame.model.pieces.Piece;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;
import com.chessgame.ui.GUIUtils;


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
            
            /*
            Stage newStage = new Stage();
            Button saveButton = new Button("save");           
            
            VBox saveGamePopUp = new VBox();
            TextField nameField = new TextField("Name of the file: ");
            ObservableList list = saveGamePopUp.getChildren(); 
            list.addAll(nameField, saveButton);
            
            saveButton.setOnAction(f -> {
                xmlController.setXmlOut(piecesOnTheBoard,
                    mainController.getBoard().getCurrentPlayer(), nameField.getText());
                newStage.close();
            });

            Scene stageScene = new Scene(saveGamePopUp, 50, 50);
            newStage.setScene(stageScene);
            newStage.show();
            */
            xmlController.setXmlOut(piecesOnTheBoard,
                    mainController.getBoard().getCurrentPlayer(), "elso.xml");

            try {
                xmlController.getXmlOut().createXML();
            } catch (JAXBException ex) { 
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void setLoadGameAction(MenuItem loadGame) {
        loadGame.setOnAction(e -> {
            
            try {
                mainController.setBoard(xmlController.getXmlIn().createStandardBoardFromXML());
                mainController.drawBoard();
                
            } catch (JAXBException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setFlipBoardAction(MenuItem flipBoardMenu) {
        flipBoardMenu.setOnAction(e -> {
            if (mainController.getBoardDirection()==GUIUtils.BoardDirection.FLIPPED){
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
