package com.chessgame.model.board;

import com.chessgame.model.pieces.Piece;
import com.chessgame.model.pieces.Queen;
import com.chessgame.model.pieces.Bishop;
import com.chessgame.model.pieces.Rook;
import com.chessgame.model.pieces.Knight;
import com.chessgame.model.pieces.King;
import com.chessgame.model.pieces.Pawn;
import com.chessgame.model.Alliance;
import com.chessgame.model.player.BlackPlayer;
import com.chessgame.model.player.Player;
import com.chessgame.model.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.*;


public class Board {

    private final List<Tile> tilesOnTheBoard;
    
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;    
    
    private Board (final Builder builder) {
        
        this.tilesOnTheBoard = createChessBoard(builder);

        this.whitePieces = calculatePiecesOnBoard(this.tilesOnTheBoard, Alliance.WHITE);
        this.blackPieces = calculatePiecesOnBoard(this.tilesOnTheBoard, Alliance.BLACK);

        this.whitePlayer = new WhitePlayer (this, setPossibleMoves(this.whitePieces), setPossibleMoves(this.blackPieces));
        this.blackPlayer = new BlackPlayer (this, setPossibleMoves(this.whitePieces), setPossibleMoves(this.blackPieces));
        this.currentPlayer = builder.nextPlayer.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }
    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }
    
    public Tile getTile(int tileCoordinate) {
        return tilesOnTheBoard.get(tileCoordinate);
    }

    private Collection<Move> setPossibleMoves(final Collection<Piece> pieces) {
        
        final List<Move> possibleMoves = new ArrayList<>();
        
        for (final Piece piece : pieces) {
            possibleMoves.addAll(piece.setPossibleMoves(this));
        }
                
        return ImmutableList.copyOf(possibleMoves);
    }

    private static Collection<Piece> calculatePiecesOnBoard(final List<Tile> tilesOnTheBoard, final Alliance alliance) {
        
        final List<Piece> piecesOnBoard = new ArrayList<>();
        
        for (Tile tile : tilesOnTheBoard) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceAlliance() == alliance) {
                    piecesOnBoard.add(piece);
                }
            }
        }
        
        return ImmutableList.copyOf(piecesOnBoard);
    }
    
    public static List<Tile> createChessBoard(final Builder builder) {
        
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardSetup.get(i));
        }
        
        return ImmutableList.copyOf(tiles);
    }

        public static Board createStandardBoard() {
            
        final Builder builder = new Builder();

        builder.setPiece(new Rook(0,Alliance.BLACK));
        builder.setPiece(new Knight(1,Alliance.BLACK));
        builder.setPiece(new Bishop(2,Alliance.BLACK));
        builder.setPiece(new Queen(3,Alliance.BLACK));
        builder.setPiece(new King(4,Alliance.BLACK));
        builder.setPiece(new Rook(7,Alliance.BLACK));
        builder.setPiece(new Knight(6,Alliance.BLACK));
        builder.setPiece(new Bishop(5,Alliance.BLACK));
                
        for (int i = 8,  j = 48; i < 16; i++, j++) {
            builder.setPiece(new Pawn(i, Alliance.BLACK, true));
            builder.setPiece(new Pawn(j, Alliance.WHITE, true));
        }

        builder.setPiece(new Rook(56,Alliance.WHITE));
        builder.setPiece(new Knight(57,Alliance.WHITE));
        builder.setPiece(new Bishop(58,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE));
        builder.setPiece(new Queen(59,Alliance.WHITE));
        builder.setPiece(new Bishop(61,Alliance.WHITE));
        builder.setPiece(new Knight(62,Alliance.WHITE));
        builder.setPiece(new Rook(63,Alliance.WHITE));
        
        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }
   
    public static class Builder {

        Map<Integer,Piece> boardSetup;
        Alliance nextPlayer;
        Move transitionMove;

        public Builder() {
            this.boardSetup = new HashMap<>();
        }

        public Builder setPiece(Piece piece) {
            this.boardSetup.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(Alliance nextMoveMaker) {
            this.nextPlayer = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
        
        public Builder setMoveTransition(Move transitionMove) {
            this.transitionMove = transitionMove;
            return this;
        }
    }
}
