package com.chessgame.model.player;

import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.Move;
import com.chessgame.model.pieces.Piece;
import java.util.Collection;


public class BlackPlayer extends Player {
    public BlackPlayer (final Board board,
                        final Collection<Move> whiteStandardLegalMoves,
                        final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }
}