
package io.github.thatusualguy.chessonline.ui.games.board.pieces;

import java.util.LinkedList;
import java.util.List;

import io.github.thatusualguy.chessonline.ui.games.board.Board;
import io.github.thatusualguy.chessonline.ui.games.board.Coordinate;

public class Bishop extends io.github.thatusualguy.chessonline.ui.games.board.pieces.Piece {

    public Bishop(final Coordinate p, final String o) {
        super(p, o);
    }

    @Override
    public List<Coordinate> getPossiblePositions() {
        return moveDiagonal(this);
    }

    /**
     * Get a list of possible positions, if the piece can only move diagonal from its current position
     *
     * @param p the piece
     * @return a list of possible positions
     */
    public static List<Coordinate> moveDiagonal(final Piece p) {
        List<Coordinate> re = new LinkedList<Coordinate>();
        int x = p.position.x + 1;
        int y = p.position.y + 1;
        Coordinate c = new Coordinate(x, y);

        // move to top right
        while (c.isValid() && Board.getPiece(c) == null) {
            re.add(c);
            y++;
            x++;
            c = new Coordinate(x, y);
        }
        if (c.isValid() && !p.sameTeam(c)) {
            re.add(c);
        }

        // move to bottom right
        x = p.position.x + 1;
        y = p.position.y - 1;
        c = new Coordinate(x, y);
        while (c.isValid() && Board.getPiece(c) == null) {
            re.add(c);
            y--;
            x++;
            c = new Coordinate(x, y);
        }
        if (c.isValid() && !p.sameTeam(c)) {
            re.add(c);
        }

        // move top left
        x = p.position.x - 1;
        y = p.position.y + 1;
        c = new Coordinate(x, y);
        while (c.isValid() && Board.getPiece(c) == null) {
            re.add(c);
            x--;
            y++;
            c = new Coordinate(x, y);
        }
        if (c.isValid() && !p.sameTeam(c)) {
            re.add(c);
        }

        // move bottom left
        x = p.position.x - 1;
        y = p.position.y - 1;
        c = new Coordinate(x, y);
        while (c.isValid() && Board.getPiece(c) == null) {
            re.add(c);
            x--;
            y--;
            c = new Coordinate(x, y);
        }
        if (c.isValid() && !p.sameTeam(c)) {
            re.add(c);
        }

        return re;
    }

    @Override
    public String getString() {
        return "\u265D";
    }
}
