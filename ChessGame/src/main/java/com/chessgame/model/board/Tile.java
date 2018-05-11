package com.chessgame.model.board;

import com.chessgame.model.pieces.Piece;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    private static final Map<Integer, EmptyTile > ALL_EMPTY_TILE = createEveryEmptyTiles();
    
    protected final int tileCoordinate;

    private static Map<Integer, EmptyTile> createEveryEmptyTiles() {
        
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? 
                new OccuppiedTile(tileCoordinate, piece) :
                ALL_EMPTY_TILE.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

    public int getTileCoordinate(){
        return this.tileCoordinate;
    }

    public static final class EmptyTile extends Tile {
        
        private EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied (){
            return false;
        }

        @Override
        public Piece getPiece(){
            return null;
        }
    }

    public static final class OccuppiedTile extends Tile{
        
        private final Piece pieceOnTile;

        private OccuppiedTile(int tileCoordinate, final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied(){
            return true;
        }

        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
        }
    }
}