package io.github.thatusualguy.chessonline.ui.games.board;

import android.util.Pair;

import io.github.thatusualguy.chessonline.ui.games.board.pieces.Bishop;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.DownPawn;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.King;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.Knight;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.LeftPawn;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.Pawn;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.Piece;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.Queen;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.RightPawn;
import io.github.thatusualguy.chessonline.ui.games.board.pieces.Rook;


public class Board {

    private Board() {
    }

    private static Piece[][] BOARD;

    public static boolean extendedBoard; // true, if 12x12 board, false if 8x8

    /**
     * Remove all pieces belonging to the given player
     *
     * @param playerId the player id who's pieces should be removed
     */
    public static void removePlayer(final String playerId) {
        for (int x = 0; x < (extendedBoard ? 12 : 8); x++) {
            for (int y = 0; y < (extendedBoard ? 12 : 8); y++) {
                if (BOARD[x][y] != null && playerId.equals(BOARD[x][y].getPlayerId())) {
                    BOARD[x][y] = null;
                }
            }
        }
    }

    /**
     * Move a piece
     *
     * @param old_pos old position of the piece
     * @param new_pos new position
     * @return false, if that move is not legal
     */
    public static boolean move(final Coordinate old_pos, final Coordinate new_pos) {
        if (!Game.myTurn()) return false;

        if (!new_pos.isValid()) return false; // not a valid new position

        Piece p = BOARD[old_pos.x][old_pos.y];
        if (!p.getPossiblePositions().contains(new_pos)) return false; // not possible to move there

        Piece target = BOARD[new_pos.x][new_pos.y];

        // move the piece
        BOARD[new_pos.x][new_pos.y] = BOARD[old_pos.x][old_pos.y];
        BOARD[old_pos.x][old_pos.y] = null;
        p.position = new_pos;

        Game.getPlayer(Game.currentPlayer()).lastMove =
                new Pair<Coordinate, Coordinate>(old_pos, new_pos);

        if (target != null && target instanceof King && Game.removePlayer(target.getPlayerId())) {
            // game ended
            Game.over();
        } else {
            Game.moved();
        }
        return true;
    }

    /**
     * Gets a piece from the board
     *
     * @param c the coordinate of the piece to get
     * @return the piece or null, if there is none at the given coordinate
     */
    public static Piece getPiece(final Coordinate c) {
        return BOARD[c.x][c.y];
    }

    /**
     * Loads the game board from the given data
     *
     * @param data containing information about the state of the game
     */
    public static void load(final String data, int match_mode) {
        String[] pieceData;
        Coordinate c;
        extendedBoard = match_mode != Game.MODE_2_PLAYER_2_SIDES;
        BOARD = extendedBoard ? new Piece[12][12] : new Piece[8][8];
        for (String piece : data.split(";")) {
            pieceData = piece.split(",");
            c = new Coordinate(Integer.parseInt(pieceData[0]), Integer.parseInt(pieceData[1]),
                    getRotation());
            switch (pieceData[3]) {
                case "Bishop":
                    BOARD[c.x][c.y] = new Bishop(c, pieceData[2]);
                    break;
                case "King":
                    BOARD[c.x][c.y] = new King(c, pieceData[2]);
                    break;
                case "Knight":
                    BOARD[c.x][c.y] = new Knight(c, pieceData[2]);
                    break;
                case "Pawn":
                    BOARD[c.x][c.y] = new Pawn(c, pieceData[2]);
                    break;
                case "Queen":
                    BOARD[c.x][c.y] = new Queen(c, pieceData[2]);
                    break;
                case "Rook":
                    BOARD[c.x][c.y] = new Rook(c, pieceData[2]);
                    break;
                case "LeftPawn":
                    BOARD[c.x][c.y] = new LeftPawn(c, pieceData[2]);
                    break;
                case "RightPawn":
                    BOARD[c.x][c.y] = new RightPawn(c, pieceData[2]);
                    break;
                case "DownPawn":
                    BOARD[c.x][c.y] = new DownPawn(c, pieceData[2]);
                    break;
            }
        }
    }

    /**
     * Get a string representation of this board
     *
     * @return the board, represented as a string
     */
    public static String getString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < (extendedBoard ? 12 : 8); x++) {
            for (int y = 0; y < (extendedBoard ? 12 : 8); y++) {
                if (BOARD[x][y] != null) {
                    sb.append(BOARD[x][y].toString());
                    sb.append(";");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Sets the player at the top or the bottom up
     *
     * @param x_begin  x-coordinate of the left-most piece
     * @param y_pawns  y-coordinate of the rows of pawns
     * @param y_others y-coordinate of the rows of other pieces
     * @param owner    player.id who owns these pieces
     */
    private static void setupPlayerTopBottom(int x_begin, int y_pawns, int y_others, final String owner) {
        for (int x = x_begin; x < x_begin + 8; x++) {
            BOARD[x][y_pawns] = Game.match.isLocal && (y_pawns == 6 || y_pawns == 10) ?
                    new DownPawn(new Coordinate(x, y_pawns), owner) :
                    new Pawn(new Coordinate(x, y_pawns), owner);
        }
        BOARD[x_begin][y_others] = new Rook(new Coordinate(x_begin, y_others), owner);
        BOARD[x_begin + 1][y_others] = new Knight(new Coordinate(x_begin + 1, y_others), owner);
        BOARD[x_begin + 2][y_others] = new Bishop(new Coordinate(x_begin + 2, y_others), owner);
        BOARD[x_begin + 3][y_others] = new Queen(new Coordinate(x_begin + 3, y_others), owner);
        BOARD[x_begin + 4][y_others] = new King(new Coordinate(x_begin + 4, y_others), owner);
        BOARD[x_begin + 5][y_others] = new Bishop(new Coordinate(x_begin + 5, y_others), owner);
        BOARD[x_begin + 6][y_others] = new Knight(new Coordinate(x_begin + 6, y_others), owner);
        BOARD[x_begin + 7][y_others] = new Rook(new Coordinate(x_begin + 7, y_others), owner);
    }

    /**
     * Sets the player at the left or the right up
     *
     * @param x_pawns  x-coordinate of the columns of pawns
     * @param x_others x-coordinate of the columns of other pieces
     * @param owner    player.id who owns these pieces
     */
    private static void setupPlayerLeftRight(int x_pawns, int x_others, final String owner) {
        for (int y = 2; y < 10; y++) {
            if (Game.match.isLocal) {
                BOARD[x_pawns][y] =
                        x_pawns == 1 ? new RightPawn(new Coordinate(x_pawns, y), owner) :
                                new LeftPawn(new Coordinate(x_pawns, y), owner);
            } else {
                BOARD[x_pawns][y] = Game.match.mode == Game.MODE_2_PLAYER_4_SIDES ?
                        new LeftPawn(new Coordinate(x_pawns, y), owner) :
                        new Pawn(new Coordinate(x_pawns, y), owner);
            }
        }
        BOARD[x_others][2] = new Rook(new Coordinate(x_others, 2), owner);
        BOARD[x_others][3] = new Knight(new Coordinate(x_others, 3), owner);
        BOARD[x_others][4] = new Bishop(new Coordinate(x_others, 4), owner);
        BOARD[x_others][5] = new King(new Coordinate(x_others, 5), owner);
        BOARD[x_others][6] = new Queen(new Coordinate(x_others, 6), owner);
        BOARD[x_others][7] = new Bishop(new Coordinate(x_others, 7), owner);
        BOARD[x_others][8] = new Knight(new Coordinate(x_others, 8), owner);
        BOARD[x_others][9] = new Rook(new Coordinate(x_others, 9), owner);
    }

    /**
     * Initialize a new game
     *
     * @param players the players
     */
    public static void newGame(final Player[] players) {
        if (Game.match.mode == Game.MODE_2_PLAYER_2_SIDES) {
            BOARD = new Piece[8][8];
            extendedBoard = false;

            // setup player 1 (bottom)
            setupPlayerTopBottom(0, 1, 0, players[0].id);

            // setup player 2 (top)
            setupPlayerTopBottom(0, 6, 7, players[1].id);
        } else {
            BOARD = new Piece[12][12];
            extendedBoard = true;

            // setup player 1 (bottom)
            setupPlayerTopBottom(2, 1, 0, players[0].id);

            // setup player 2 (right)
            setupPlayerLeftRight(10, 11,
                    Game.match.mode == Game.MODE_2_PLAYER_4_SIDES ? players[0].id : players[1].id);

            // setup player 3 (top)
            setupPlayerTopBottom(2, 10, 11,
                    Game.match.mode == Game.MODE_2_PLAYER_4_SIDES ? players[1].id : players[2].id);

            // setup player 4 (left)
            setupPlayerLeftRight(1, 0,
                    Game.match.mode == Game.MODE_2_PLAYER_4_SIDES ? players[1].id : players[3].id);
        }
    }

    /**
     * Gets the number of rotations the board should do, so this players start position is at the bottom of the board
     *
     * @return number of rotations necessary to have this players start position at the bottom
     */
    public static int getRotation() {
        if (Game.match.isLocal) return 0;
        for (int i = 0; i < 4; i++) {
            if (Game.players[i].id.equals(Game.myPlayerId))
                return Game.players.length > 2 ? i : i * 2;
        }
        return 0;
    }

}
