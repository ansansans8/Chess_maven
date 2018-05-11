package com.chessgame.model.player;

import com.chessgame.model.Alliance;
import com.chessgame.model.board.Board;
import com.chessgame.model.board.Move;
import com.chessgame.model.pieces.Piece;
import java.util.Collection;

public class WhitePlayer extends Player {
    public WhitePlayer (final Board board,
                        final Collection<Move> whiteStandardLegalMoves,
                        final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }
}