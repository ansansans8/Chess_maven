package com.chessgame.model.board;

import com.chessgame.model.board.Board.Builder;
import com.chessgame.model.pieces.Pawn;
import com.chessgame.model.pieces.Piece;

public abstract class Move {

    protected final Board board;
    protected final int destinationCoordinate;
    protected final Piece movingPiece;
    protected final boolean isFirstMove;

    private Move(final Board board,
                 final Piece pieceMoved,
                 final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movingPiece = pieceMoved;
        this.isFirstMove = pieceMoved.isFirstMove();
    }

    private Move(final Board board,
                 final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movingPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        
        int result = 1;
        
        result = 31 * result + this.destinationCoordinate;
        result = 31 * result + this.movingPiece.hashCode();
        result = 31 * result + this.movingPiece.getPiecePosition();
        result = result + (isFirstMove ? 1 : 0);
        
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
        
        final Move otherMove = (Move) other;
        
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
               getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
               getMovingPiece().equals(otherMove.getMovingPiece());
    }

    public Board getBoard() {
        return this.board;
    }

    public int getCurrentCoordinate() {
        return this.movingPiece.getPiecePosition();
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public Piece getMovingPiece() {
        return this.movingPiece;
    }

    public boolean isAttack() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Board execute() {
        
        final Board.Builder builder = new Builder();
        
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movingPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        
        this.movingPiece.setFirstMove(false);
        builder.setPiece(this.movingPiece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
        builder.setMoveTransition(this);
        
        return builder.build();
    }
    
    public static class AttackMove extends Move {

        private final Piece attackedPiece;

        public AttackMove(final Board board,
                   final Piece pieceMoved,
                   final int destinationCoordinate,
                   final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate);
            this.attackedPiece = pieceAttacked;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }
    }
    
    public static class StandardMove extends Move {

        public StandardMove(final Board board,
                         final Piece pieceMoved,
                         final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof StandardMove && super.equals(other);
        }
    }
    
    public static class PawnStandardMove
            extends Move {

        public PawnStandardMove(final Board board,
                        final Piece pieceMoved,
                        final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnStandardMove && super.equals(other);
        }
    }
    
    public static class PawnAttackMove
            extends AttackMove {

        public PawnAttackMove(final Board board,
                              final Piece pieceMoved,
                              final int destinationCoordinate,
                              final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }
    }

    public static class PawnPromotion extends PawnStandardMove {

        final Move move;
        final Pawn promotedPawn;
        final Piece promotionPiece;

        public PawnPromotion(final Move move,
                             final Piece promotionPiece) {
            super(move.getBoard(), move.getMovingPiece(), move.getDestinationCoordinate());
            this.move = move;
            this.promotedPawn = (Pawn) move.getMovingPiece();
            this.promotionPiece = promotionPiece;
        }

        @Override
        public int hashCode() {
            return move.hashCode() + (31 * promotedPawn.hashCode());
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnPromotion && (super.equals(other));
        }

        @Override
        public Board execute() {
            
            final Board pawnMovedBoard = this.move.execute();
            final Board.Builder builder = new Builder();
            
            for (final Piece piece : pawnMovedBoard.getCurrentPlayer().getActivePieces()) {
                if (!this.promotedPawn.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : pawnMovedBoard.getCurrentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            
            builder.setPiece(this.promotionPiece.movePiece(this));
            builder.setMoveMaker(pawnMovedBoard.getCurrentPlayer().getAlliance());
            builder.setMoveTransition(this);
            
            return builder.build();
        }
    }

    private static class NullMove extends Move {

        private NullMove() {
            super(null, -1);
        }

        @Override
        public int getCurrentCoordinate() {
            return -1;
        }

        @Override
        public int getDestinationCoordinate() {
            return -1;
        }

        @Override
        public Board execute() {
            throw new RuntimeException("NULL MOVE CANNOT BE EXECUTED");
        }
    }

    public static class MoveFactory {

        private static final Move NULL_MOVE = new NullMove();

        public static Move getNullMove() {
            return NULL_MOVE;
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}