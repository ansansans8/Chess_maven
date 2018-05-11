package com.chessgame.ui;

import com.chessgame.controller.MainController;
import com.google.common.collect.Lists;

import java.util.List;

public class GUIUtils {
    public static final String LIGHT_TILE_COLOUR = "-fx-background-color: #FFFACD;";
    public static final String DARK_TILE_COLOUR = "-fx-background-color: #8B4513;";
    public static final String SELECTED_DARK_TILE_COLOUR = "-fx-background-color: #469F27;";
    public static final String SELECTED_LIGHT_TILE_COLOUR = "-fx-background-color: #99FD80;";
    //public static final String SELECTED_TILE_COLOUR = "-fx-background-color: #33FF33;";
    public static final String SELECTED_TILE_COLOUR = "-fx-background-color: #0000FF;";
    public static final String RED_COLOUR = "-fx-background-color: #FF0000;";
    public static final String BLACK_COLOUR = "-fx-background-color: #000000;";
    public static final String GREY_COLOUR = "-fx-background-color: #AAAAAA;";
    public static final String WHITE_COLOUR = "-fx-background-color: #FFFFFF;";
    public static final String DEFAULT_PATH_TO_IMAGES = "src/main/java/com/chessgame/ui/assets/images/pieceimages/";
    public static final int TILE_SIZE = 45;
    public static final int TILE_WIDTH = 8;
    public static final int TILE_HEIGHT = 8;
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 400;
    public final static double H_TILE_GAP = 0;
    public final static double V_TILE_GAP = 0;
    public final static int OFF_BOARD_POSITION = -999;

    public enum BoardDirection {
        NORMAL {
            @Override
            public List<MainController.TilePanel> flipped(final List<MainController.TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            public int flippedTileID(final int tileID) {
                return tileID;
            }

            @Override
            public BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            public List<MainController.TilePanel> flipped(final List<MainController.TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            public int flippedTileID(final int tileID) {
                return (63 - tileID);
            }

            @Override
            public BoardDirection opposite() {
                return NORMAL;
            }
        };

        public abstract List<MainController.TilePanel> flipped(final List<MainController.TilePanel> boardTiles);

        public abstract int flippedTileID(final int tileID);

        public abstract BoardDirection opposite();
    }
}