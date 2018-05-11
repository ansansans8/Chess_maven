package com.chessgame.model.pieces;

import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.BoardUtils;
import com.chessgame.model.board.Move;
import com.chessgame.model.board.Move.AttackMove;
import com.chessgame.model.board.Move.StandardMove;
import com.chessgame.model.board.Tile;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Knight extends Piece {

    private static final int[] POSSIBLE_DIRECTIONS = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int piecePosition,
                  final Alliance pieceAlliance) {
        super(piecePosition, PieceType.KNIGHT, pieceAlliance, true);
    }

    public Knight(final int piecePosition,
                  final Alliance pieceAlliance,
                  final boolean isFirstMove) {
        super(piecePosition, PieceType.KNIGHT, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> setPossibleMoves(final Board board) {
        
        List<Move> legalMoves = new ArrayList<>();
        
        for(int possibleDirection : POSSIBLE_DIRECTIONS) {
            int possibleDestinationCoordinate = this.piecePosition + possibleDirection;
            
            if(BoardUtils.isValidCoordinate(possibleDestinationCoordinate)) {
                if((isFirstColumnException(this.piecePosition, possibleDirection))
                    ||(isSecondColumnException(this.piecePosition, possibleDirection))
                    ||(isSeventColumnException(this.piecePosition,possibleDirection))
                    ||(isEighthColumnException(this.piecePosition,possibleDirection))) {
                    continue;
                }                
                final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);
                
                if(!possibleDestinationTile.isTileOccupied()) {
                    legalMoves.add(new StandardMove(board, this, possibleDestinationCoordinate));
                }
                else {
                    if (this.pieceAlliance != possibleDestinationTile.getPiece().getPieceAlliance()) {
                        legalMoves.add(new AttackMove(board, this, possibleDestinationTile.getPiece().getPiecePosition(), possibleDestinationTile.getPiece()));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getDestinationCoordinate(), move.getMovingPiece().getPieceAlliance());
    }

    private static boolean isFirstColumnException(int currentPosition, int possibleOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                ((possibleOffset == -17) || (possibleOffset == -10) ||
                 (possibleOffset == 6) || (possibleOffset == 15));
    }

    private static boolean isSecondColumnException(int currentPosition, int possibleOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition]&&((possibleOffset==-10)||(possibleOffset==6));
    }

    private static boolean isSeventColumnException(int currentPosition, int possibleOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && 
                ((possibleOffset == -6) || (possibleOffset == 10));
    }

    private static boolean isEighthColumnException(int currentPosition, int possibleOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] &&
                ((possibleOffset == 17) || (possibleOffset == 10) ||
                 (possibleOffset == -6) || (possibleOffset == -15));
    }

    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }
}
