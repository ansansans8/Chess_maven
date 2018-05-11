package com.chessgame.ui;

import static com.chessgame.ui.GUIUtils.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.chessgame.controller.MenuController;
import com.chessgame.controller.MainController;

public class Main extends Application {

    private Scene scene;
    private BorderPane borderPane;
    private FlowPane flowPane;
    private MainController mainController;
    private MenuController menuController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.scene = new Scene(createContent(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("D9LNEE - CHESS PROJECT");
        primaryStage.show();
    }

    private Parent createContent() {
        
        this.borderPane = new BorderPane();
        this.flowPane = new FlowPane();

        mainController = new MainController(BoardDirection.NORMAL);
        mainController.setMinSize(TILE_SIZE * TILE_WIDTH, TILE_SIZE * TILE_HEIGHT);

        this.flowPane.getChildren().addAll(mainController);
        this.flowPane.setHgap(TILE_SIZE);

        this.borderPane.setCenter(this.flowPane);

        final int vBoxHeight = 25;
        final int vBoxPadding = 2;
        final int vBoxSpacing = 4;

        VBox vBox = new VBox();
        vBox.setPrefSize(vBoxHeight, vBoxHeight);
        vBox.setSpacing(vBoxSpacing);
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(vBoxPadding))));
        
        final MenuBar tableMenuBar = createTableMenuBar();
        vBox.getChildren().addAll(tableMenuBar);
        
        this.borderPane.setTop(vBox);
       
        return borderPane;
    }

    private MenuBar createTableMenuBar() {
        
        menuController = new MenuController(mainController);
        
        final MenuBar tableMenuBar = new MenuBar();
        tableMenuBar.getMenus().add(createFileMenu());
        tableMenuBar.getMenus().add(createPreferenceMenu());
        
        return tableMenuBar;
    }

    private Menu createFileMenu() {
                
        final Menu fileMenu = new Menu("File");
        
        final MenuItem newGame = new MenuItem("New game");
        menuController.setNewGameAction(newGame);
        
        final MenuItem saveGame = new MenuItem("Save game");
        menuController.setSaveGameAction(saveGame);
        
        final MenuItem loadGame = new MenuItem("Load game");
        menuController.setLoadGameAction(loadGame);
        
        final MenuItem exit = new MenuItem("Exit");
        menuController.setExitAction(exit);

        fileMenu.getItems().addAll(newGame, saveGame, loadGame, exit);
        
        return fileMenu;
    }

    private Menu createPreferenceMenu() {
        
        final Menu preferenceMenu = new Menu("Preference");
        final MenuItem flipBoardMenu = new MenuItem("Flip Board");
        final CheckMenuItem showLegalMoves = new CheckMenuItem("Show Legal Moves");
        showLegalMoves.setSelected(true);
        
        menuController.setFlipBoardAction(flipBoardMenu);
        menuController.setshowLegalMovesAction(showLegalMoves);

        preferenceMenu.getItems().addAll(flipBoardMenu, showLegalMoves);

        return preferenceMenu;
    }
}