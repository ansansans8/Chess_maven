package com.chessgame.model.pieces;

import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.Move;

import java.util.Collection;

public abstract class Piece {
    
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected boolean isFirstMove;
    private final int cashedHashCode;

    public Piece(final int piecePosition,
            final PieceType pieceType,
          final Alliance pieceAlliance,
          final boolean isFirstMove){
          //final Image img, int x, int y){

        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.pieceType = pieceType;
        this.cashedHashCode = computeHashCode();
        this.isFirstMove = isFirstMove;

    }

    protected int computeHashCode() {
        
        int result = pieceType.hashCode();
        
        result = 31* result+pieceAlliance.hashCode();
        result = 31* result+piecePosition;
        result = 31* result+(isFirstMove ? 1 : 0);
        
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if (this == other){
            return true;
        }
        if (!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece) other;
        
        return pieceAlliance == otherPiece.getPieceAlliance()&&
               pieceType == otherPiece.getPieceType() &&
               piecePosition == otherPiece.getPiecePosition()&&
               isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.computeHashCode();
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }
    
    public void setFirstMove(boolean firstMove) {
        this.isFirstMove = firstMove;
    }
    
    public PieceType getPieceType() {
        return this.pieceType;
    }
 
    public abstract Collection<Move> setPossibleMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public enum PieceType {
        
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private final String pieceName;
        
        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }
        
        @Override
        public String toString(){
            return this.pieceName;
        }        
    }
}