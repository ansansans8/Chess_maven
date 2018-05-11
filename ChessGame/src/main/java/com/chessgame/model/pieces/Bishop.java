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


public class Bishop extends Piece {

    private static final int[] POSSIBLE_DIRECTIONS = { -9, -7, 7, 9 };

    public Bishop(final int piecePosition,
                  final Alliance pieceAlliance) {
        super(piecePosition, PieceType.BISHOP, pieceAlliance, true);
    }

    public Bishop(final int piecePosition,
                  final Alliance pieceAlliance, final boolean isFirstMove) {
        super(piecePosition, PieceType.BISHOP, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> setPossibleMoves(final Board board) {
        
        List<Move> legalMoves = new ArrayList<>();

        for (int possibleDirection : POSSIBLE_DIRECTIONS) {
            int possibleDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidCoordinate(possibleDestinationCoordinate)) {

                if (isFirstColumnException(possibleDestinationCoordinate, possibleDirection) ||
                    isEigthColumnException(possibleDestinationCoordinate, possibleDirection)){
                    break;
                }
                
                possibleDestinationCoordinate += possibleDirection;
                
                if (BoardUtils.isValidCoordinate(possibleDestinationCoordinate)) {

                    final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

                    if (!possibleDestinationTile.isTileOccupied()) {
                        legalMoves.add(new StandardMove(board, this, possibleDestinationCoordinate));
                    }
                    else {
                        if (this.pieceAlliance != possibleDestinationTile.getPiece().getPieceAlliance()) {
                            legalMoves.add(new AttackMove(board, this, possibleDestinationCoordinate, possibleDestinationTile.getPiece()));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getDestinationCoordinate(), move.getMovingPiece().getPieceAlliance());
    }

    public boolean isFirstColumnException(int currentPosition, int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                (candidateOffset == -9 ||candidateOffset== 7);
    }

    public boolean isEigthColumnException(int currentPosition, int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] &&
                (candidateOffset == 9 ||candidateOffset== -7);
    }

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }
}
