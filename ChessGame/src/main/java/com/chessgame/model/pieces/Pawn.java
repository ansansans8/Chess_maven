package com.chessgame.model.pieces;

import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.BoardUtils;
import com.chessgame.model.board.Move;
import com.chessgame.model.board.Move.*;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Pawn extends Piece{
 
    private final static int[] POSSIBLE_DIRECTIONS = { 8, 16, 7, 9 };

    public Pawn(final int piecePosition,
                final Alliance pieceAlliance,
                final boolean isFirstMove) {
        super(piecePosition, PieceType.PAWN, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> setPossibleMoves(final Board board) {
        
        List<Move> legalMoves = new ArrayList<>();
        
        for (int possibleDirection : POSSIBLE_DIRECTIONS) {
            int possibleDestinationCoordinate =
                    this.piecePosition + (this.pieceAlliance.getDirection() * possibleDirection);
            
            if (!BoardUtils.isValidCoordinate(possibleDestinationCoordinate)) {
                continue;
            }
            if (possibleDirection == 8 && !board.getTile(possibleDestinationCoordinate).isTileOccupied()) {
                
                if (BoardUtils.EIGHTH_ROW[possibleDestinationCoordinate] ||
                    BoardUtils.FIRST_ROW[possibleDestinationCoordinate]) {
                    
                    legalMoves.add(new PawnPromotion(
                            new PawnStandardMove(board, this, possibleDestinationCoordinate), new Queen(possibleDestinationCoordinate, this.pieceAlliance)));
                }
                else {
                    legalMoves.add(new PawnStandardMove(board, this, possibleDestinationCoordinate));
                }
            }
            else if (possibleDirection == 16 && this.isFirstMove()) {
                    int doubleMove = this.piecePosition + this.pieceAlliance.getDirection() * 8;
                    
                    if (!board.getTile(doubleMove).isTileOccupied() &&
                            !board.getTile(possibleDestinationCoordinate).isTileOccupied()) {
                        legalMoves.add(new PawnStandardMove(board, this, possibleDestinationCoordinate));
                }
            }
            else if (possibleDirection == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance == Alliance.WHITE) ||
                      (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance == Alliance.BLACK))) {
                
                if (board.getTile(possibleDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnPossibleTile = board.getTile(possibleDestinationCoordinate).getPiece();
                    
                    if (this.pieceAlliance != pieceOnPossibleTile.getPieceAlliance()) {
                        
                        if (BoardUtils.EIGHTH_ROW[possibleDestinationCoordinate] ||
                            BoardUtils.FIRST_ROW[possibleDestinationCoordinate]) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestinationCoordinate, pieceOnPossibleTile),
                                    new Queen(possibleDestinationCoordinate, this.pieceAlliance)));
                        }
                        else {
                            legalMoves.add(new PawnAttackMove(board, this, possibleDestinationCoordinate, pieceOnPossibleTile));
                        }
                    }
                }
            }
            else if (possibleDirection == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance == Alliance.WHITE)) ||
                      (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance == Alliance.BLACK)) {
                
                if(board.getTile(possibleDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnPossibleTile = board.getTile(possibleDestinationCoordinate).getPiece();
                    
                    if (this.pieceAlliance != pieceOnPossibleTile.getPieceAlliance()) {
                        
                        if (BoardUtils.EIGHTH_ROW[possibleDestinationCoordinate] ||
                            BoardUtils.FIRST_ROW[possibleDestinationCoordinate]) {
                            
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, possibleDestinationCoordinate, pieceOnPossibleTile),
                                    new Queen(possibleDestinationCoordinate, this.pieceAlliance)));
                        }
                        else {
                            legalMoves.add(new PawnAttackMove(board, this, possibleDestinationCoordinate, pieceOnPossibleTile));
                        }
                    }
                } 
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovingPiece().getPieceAlliance(), this.isFirstMove);
    }

    @Override
    public String toString(){
        return PieceType.PAWN.toString();
    }
}

