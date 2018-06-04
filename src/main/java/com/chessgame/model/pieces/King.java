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


public class King extends Piece {
    
    private final static int[] POSSIBLE_DIRECTIONS= { -9, -8, -7, -1, 1, 7, 8, 9 };

    public King(final int piecePosition,
                 final Alliance pieceAlliance,
                 final boolean isFirstMove) {
        super(piecePosition, PieceType.KING, pieceAlliance, isFirstMove);
    }
    
    public King(final int piecePosition,
                final Alliance pieceAlliance) {
        super(piecePosition, PieceType.KING, pieceAlliance, true);
    }

    @Override
    public Collection<Move> setPossibleMoves(Board board) {
        
        List<Move> legalMoves = new ArrayList<>();

        for (int possibleDirection : POSSIBLE_DIRECTIONS ) {
            int possibleDestinationCoordinate = this.piecePosition + possibleDirection;

            if ((isFirstColumnException(this.piecePosition, possibleDirection)) ||
               (isEighthColumnException(this.piecePosition, possibleDirection))) {
                continue;
            }

            if (BoardUtils.isValidCoordinate(possibleDestinationCoordinate)) {
                final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);
                
                if(!possibleDestinationTile.isTileOccupied()) {
                    legalMoves.add(new StandardMove(board, this, possibleDestinationCoordinate));
                }
                else {
                    if (this.pieceAlliance != possibleDestinationTile.getPiece().getPieceAlliance())
                    {
                        legalMoves.add(new AttackMove(board, this, possibleDestinationTile.getPiece().getPiecePosition(), possibleDestinationTile.getPiece()));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovingPiece().getPieceAlliance());
    }

    private boolean isFirstColumnException(int currentPosition, int candidateOffset)
    {
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset==-1) || (candidateOffset==-9) || (candidateOffset==7));
    }

    private boolean isEighthColumnException(int currentPosition, int candidateOffset)
    {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset==1) || (candidateOffset==9) || (candidateOffset==-7));
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }
}
