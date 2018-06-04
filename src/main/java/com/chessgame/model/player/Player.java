package com.chessgame.model.player;

import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.Move;
import com.chessgame.model.pieces.King;
import com.chessgame.model.pieces.Piece;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> possibleMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> possibleMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.possibleMoves = possibleMoves;
        this.isInCheck = !calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
        
    }
    
    private King establishKing() {
        
        for (Piece piece : getActivePieces()) {
            if (piece.getPieceType() == Piece.PieceType.KING ){
                return (King) piece;
            }
        }
        
        throw new RuntimeException("W/O King it is not a legit board");
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentMoves) {
        
        List<Move> attackMoves = new ArrayList<>();

        for (Move move : opponentMoves){
            if (piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        
    return ImmutableList.copyOf(attackMoves);
    }
    
    public King getPlayerKing() {
        return playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return possibleMoves;
    }

    public boolean isMoveLegal(final Move move) {
        return this.possibleMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean inInStaleMate() {
        return !isInCheck&&!hasEscapeMoves();
    }

    protected boolean hasEscapeMoves() {
        
        for (Move move: this.possibleMoves){
            MoveTransition transition = makeMove(move);
            if (transition.getMovesStatus().isDone()){
                 return true;
            }
        }
        return false;
    }

    public boolean isCastled(){
        return false;
    }

    public MoveTransition makeMove(Move move){

        if(!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        
        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacked = Player.calculateAttacksOnTile(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.getCurrentPlayer().getLegalMoves());

        if (!kingAttacked.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
}