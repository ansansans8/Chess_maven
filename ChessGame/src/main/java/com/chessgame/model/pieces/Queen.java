package com.chessgame.model.pieces;

import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.BoardUtils;
import com.chessgame.model.board.Move;
import com.chessgame.model.board.Move.StandardMove;
import com.chessgame.model.board.Tile;
import com.chessgame.model.board.Move.AttackMove;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece{

    private static final int[] POSSIBLE_DIRECTIONS = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public Queen(final int piecePosition,
                 final Alliance pieceAlliance) {
        super(piecePosition, PieceType.QUEEN, pieceAlliance, true);
    }

    public Queen(final int piecePosition,
                 final Alliance pieceAlliance,
                 final boolean isFirstMove) {
        super(piecePosition, PieceType.QUEEN, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> setPossibleMoves(Board board) {
        
        List<Move> legalMoves = new ArrayList<>();

        for (int possibleDirection : POSSIBLE_DIRECTIONS) {
            int possibleDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidCoordinate(possibleDestinationCoordinate)) {
                if (isFirstColumnException(possibleDestinationCoordinate , possibleDirection) ||
                    isEighthColumnException(possibleDestinationCoordinate, possibleDirection)) {
                    break;
                }
                
                possibleDestinationCoordinate += possibleDirection;
                
                if (BoardUtils.isValidCoordinate(possibleDestinationCoordinate)) {

                    final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

                    if (!possibleDestinationTile.isTileOccupied()) {
                        legalMoves.add(new StandardMove(board, this, possibleDestinationCoordinate));
                    }
                    else {
                        if (this.pieceAlliance != possibleDestinationTile.getPiece().pieceAlliance) {
                            legalMoves.add(new AttackMove(board, this, possibleDestinationTile.getPiece().getPiecePosition(), possibleDestinationTile.getPiece()));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getDestinationCoordinate(), move.getMovingPiece().getPieceAlliance());
    }

    public boolean isFirstColumnException(int currentPosition, int possibleOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                (possibleOffset == -1 || possibleOffset == -9 || possibleOffset == 7);
    }

    public boolean isEighthColumnException(int currentPosition, int possibleOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] &&
                (possibleOffset == 1 ||possibleOffset == 9 || possibleOffset == -7);
    }

    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }
}
