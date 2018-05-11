package com.chessgame.controller;

import com.chessgame.model.board.Board;
import com.chessgame.model.board.Move;
import com.chessgame.model.board.Tile;
import com.chessgame.model.pieces.Piece;
import com.chessgame.model.player.MoveTransition;
import com.chessgame.ui.PieceImage;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Group;

import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chessgame.model.board.BoardUtils.*;
import static com.chessgame.ui.GUIUtils.*;

public class MainController extends BorderPane {

    private Group pieceGroup;
    private BoardPanel boardPanel;
    private AnchorPane anchorPane;
    private Board board;
    private boolean showLegalMovesSetting;
    private BoardDirection boardDirection;

    private Tile sourceTile;
    private Tile hoveredTile;
    private Tile destinationTile;
    private TilePanel sourceTilePanel;
    private TilePanel hoveredTilePanel;
    private Piece humanMovedPiece;

    public MainController(final BoardDirection direction) {

        this.board = Board.createStandardBoard();
        this.boardDirection = direction;
        this.pieceGroup = new Group();
        this.anchorPane = new AnchorPane();

        this.showLegalMovesSetting = true;
        this.boardPanel = new BoardPanel();
        anchorPane.getChildren().addAll(boardPanel, pieceGroup);
        setCenter(anchorPane);
    }

    public void setBoardDirection(BoardDirection boardDirection) {
        this.boardDirection = boardDirection;
    }

    public BoardDirection getBoardDirection() {
        return this.boardDirection;
    }

    public void setShowLegalMoves(boolean showLegalMoves) {
        this.showLegalMovesSetting = showLegalMoves;
    }

    public void setBoard(Board engineBoard) {
        this.board = engineBoard;
    }

    public Board getBoard() {
        return board;
    }

    public void drawBoard() {
        anchorPane.getChildren().clear();
        boardPanel.getChildren().clear();
        boardPanel = new BoardPanel();
        anchorPane.getChildren().addAll(boardPanel, pieceGroup);
    }

    private int tileIDFromCoOrdinate(int x, int y) {
        int tileID = x + y * 8;
        return tileID;
    }

    protected class BoardPanel extends TilePane {

        final List<TilePanel> boardTiles;

        BoardPanel() {
            this.boardTiles = new ArrayList<>();
            setMinSize(TILE_SIZE * TILE_WIDTH, TILE_SIZE * TILE_HEIGHT);
            setMaxSize(TILE_SIZE * TILE_WIDTH, TILE_SIZE * TILE_HEIGHT);
            pieceGroup.getChildren().clear();

            for (int i = 0; i < NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(boardDirection.flippedTileID(i), Orientation.HORIZONTAL, H_TILE_GAP, V_TILE_GAP);
                this.boardTiles.add(tilePanel);

                tilePanel.setOnMousePressed(e -> {
                    tilePanel.assignMoveTileColor();
                });

                tilePanel.setOnMouseReleased(e -> {
                    tilePanel.assignTileColor();
                });

                getChildren().add(tilePanel);
            }
        }

        protected void drawBoard() {
            anchorPane.getChildren().clear();
            boardPanel.getChildren().clear();
            boardPanel = new BoardPanel();
            anchorPane.getChildren().addAll(boardPanel, pieceGroup);
        }
    }

    public class TilePanel extends TilePane {

        private final int tileID;
        private String originalColorString;

        TilePanel(final int tileID, Orientation orientation, double hgap, double vgap) {
            super(orientation, hgap, vgap);
            this.tileID = tileID;

            setMinSize(TILE_SIZE, TILE_SIZE);
            setMaxSize(TILE_SIZE, TILE_SIZE);
            assignTileColor();

            assignTilePieceIcon(board);
            highLightLegalMoves(board);
        }

        public void drawTilePanel(Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
        }

        private void highLightLegalMoves(final Board board) {
            if (showLegalMovesSetting) {
                for (Move move : pieceLegalMoves(board)) {
                    if (move.getDestinationCoordinate() == this.tileID) {
                        assignMoveTileColor();
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.getCurrentPlayer().getAlliance()) {
                return humanMovedPiece.setPossibleMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTileColor() {
            if (FIRST_ROW[this.tileID]
                    || THIRD_ROW[this.tileID]
                    || FIFTH_ROW[this.tileID]
                    || SEVENTH_ROW[this.tileID]) {

                setStyle(tileID % 2 != 0 ? LIGHT_TILE_COLOUR : DARK_TILE_COLOUR);
                originalColorString = getStyle();
            } else if (SECOND_ROW[this.tileID]
                    || FOURTH_ROW[this.tileID]
                    || SIXTH_ROW[this.tileID]
                    || EIGHTH_ROW[this.tileID]) {

                setStyle(tileID % 2 == 0 ? LIGHT_TILE_COLOUR : DARK_TILE_COLOUR);
                originalColorString = getStyle();
            }
        }

        private void assignMoveTileColor() {
            if (showLegalMovesSetting) {
                if (FIRST_ROW[this.tileID]
                        || THIRD_ROW[this.tileID]
                        || FIFTH_ROW[this.tileID]
                        || SEVENTH_ROW[this.tileID]) {

                    setStyle(tileID % 2 != 0 ? SELECTED_LIGHT_TILE_COLOUR : SELECTED_DARK_TILE_COLOUR);
                    originalColorString = getStyle();
                } else if (SECOND_ROW[this.tileID]
                        || FOURTH_ROW[this.tileID]
                        || SIXTH_ROW[this.tileID]
                        || EIGHTH_ROW[this.tileID]) {

                    setStyle(tileID % 2 == 0 ? SELECTED_LIGHT_TILE_COLOUR : SELECTED_DARK_TILE_COLOUR);
                    originalColorString = getStyle();
                }
            }
        }

        public void assignTilePieceIcon(final Board board) {

            if (board.getTile(this.tileID).isTileOccupied()) {

                final String path = DEFAULT_PATH_TO_IMAGES + board.getTile(this.tileID).getPiece().getPieceAlliance().toString().substring(0, 1)
                        + board.getTile(this.tileID).getPiece().toString() + ".png";
                final int x = getXCoordinate(boardDirection.flippedTileID(this.tileID));
                final int y = getYCoordinate(boardDirection.flippedTileID(this.tileID));

                final PieceImage pieceImage = new PieceImage(MainController.this.board.getTile(tileID).getPiece(), x, y, path);
                pieceGroup.getChildren().add(pieceImage);

                pieceImage.setOnMousePressed(e -> {
                    final int tileCoordinate = boardDirection.flippedTileID(tileIDFromCoOrdinate(x, y));

                    pieceImage.mouseX = e.getSceneX();
                    pieceImage.mouseY = e.getSceneY();

                    if (sourceTile == null) {
                        sourceTilePanel = boardPanel.boardTiles.get(boardDirection.flippedTileID(tileCoordinate));
                        sourceTile = MainController.this.board.getTile(tileCoordinate);
                        humanMovedPiece = sourceTile.getPiece();

                        if (showLegalMovesSetting) {
                            Collection<Move> selectedPiece = pieceLegalMoves(MainController.this.board);
                            for (Move move : selectedPiece) {
                                TilePanel tilePanel = boardPanel.boardTiles.get(boardDirection.flippedTileID(move.getDestinationCoordinate()));
                                tilePanel.assignMoveTileColor();
                            }
                        }
                    }
                });

                pieceImage.setOnMouseEntered(e -> {
                    pieceImage.toFront();
                    if (showLegalMovesSetting) {
                        final int tileCoOrd = boardDirection.flippedTileID(tileIDFromCoOrdinate(x, y));
                        hoveredTilePanel = boardPanel.boardTiles.get(boardDirection.flippedTileID(tileCoOrd));
                        hoveredTile = MainController.this.board.getTile(tileCoOrd);

                        if (MainController.this.board.getTile(tileCoOrd).isTileOccupied()
                                && MainController.this.board.getTile(tileCoOrd).getPiece().getPieceAlliance() == MainController.this.board.getCurrentPlayer().getAlliance()) {
                            hoveredTilePanel.setStyle(SELECTED_TILE_COLOUR);
                        }
                    }
                });

                pieceImage.setOnMouseExited(e -> {
                    final int tileCoOrd = boardDirection.flippedTileID(tileIDFromCoOrdinate(x, y));
                    boardPanel.boardTiles.get(boardDirection.flippedTileID(tileCoOrd)).setStyle(boardPanel.boardTiles.get(tileCoOrd).originalColorString);
                });

                pieceImage.setOnMouseDragged(e -> {
                    if (showLegalMovesSetting) {
                        sourceTilePanel.setStyle(SELECTED_TILE_COLOUR);
                    }
                    pieceImage.relocate(
                            Math.max(0, Math.min(TILE_SIZE * TILE_WIDTH - TILE_SIZE, e.getSceneX() - pieceImage.mouseX + pieceImage.oldX)),
                            Math.max(0, Math.min(TILE_SIZE * TILE_WIDTH - TILE_SIZE, e.getSceneY() - pieceImage.mouseY + pieceImage.oldY)));
                });

                pieceImage.setOnMouseReleased(e -> {
                    int newX = toBoard(pieceImage.getLayoutX());
                    int newY = toBoard(pieceImage.getLayoutY());
                    int x0 = (int) pieceImage.getOldX();
                    int y0 = (int) pieceImage.getOldY();

                    final int tileCoOrd = boardDirection.flippedTileID(tileIDFromCoOrdinate(newX, newY));
                    destinationTile = MainController.this.board.getTile(tileCoOrd);
                    final Move move = Move.MoveFactory.createMove(MainController.this.board, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                    final MoveTransition transition = MainController.this.board.getCurrentPlayer().makeMove(move);

                    if (transition.getMovesStatus().isDone()) {

                        MainController.this.board = transition.getTransitionBoard();

                        sourceTilePanel.setStyle(sourceTilePanel.originalColorString);
                        sourceTilePanel = null;
                        sourceTile = null;
                        humanMovedPiece = null;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard();
                            }
                        });
                    }
                    else {
                        pieceImage.relocate(x0, y0);
                        sourceTilePanel = null;
                        sourceTile = null;
                        humanMovedPiece = null;
                        Collection<Move> selectedPiece = pieceLegalMoves(MainController.this.board);
                        for (final TilePanel tilePanel : boardPanel.boardTiles) {
                            tilePanel.assignTileColor();
                        }
                    }
                });
            }
        }
    }

    private int toBoard(double pixel) {
        return (int) ((pixel + TILE_SIZE / 2) / TILE_SIZE);
    }

    private int getXCoordinate(int tileID) {
        int x = tileID % 8;
        return x;

    }

    private int getYCoordinate(int tileID) {
        int y = (int) Math.floor(tileID / 8);
        return y;
    }
}
