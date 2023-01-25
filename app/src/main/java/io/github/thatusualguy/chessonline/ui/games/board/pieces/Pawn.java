
package io.github.thatusualguy.chessonline.ui.games.board.pieces;

import java.util.LinkedList;
import java.util.List;

import io.github.thatusualguy.chessonline.ui.games.board.Board;
import io.github.thatusualguy.chessonline.ui.games.board.Coordinate;

public class Pawn extends Piece {
    public Pawn(final Coordinate p, final String o) {
        super(p, o);
    }

    @Override
    public List<Coordinate> getPossiblePositions() {
        List<Coordinate> re = new LinkedList<Coordinate>();
        Coordinate c;
        int x = position.x;
        int y = position.y;
        c = new Coordinate(x, y + 1);
        if (c.isValid() && Board.getPiece(c) == null) {
            re.add(c);
        }
        // can move two squares at the beginning
        // (only if no other piece stands 1 before us)
        if (y == 1 && Board.getPiece(c) == null) {
            c = new Coordinate(x, y + 2);
            if (c.isValid() && Board.getPiece(c) == null) {
                re.add(c);
            }
        }

        // check if we can attack another piece
        c = new Coordinate(x + 1, y + 1);
        if (c.isValid() && Board.getPiece(c) != null && !sameTeam(c)) {
            re.add(c);
        }
        c = new Coordinate(x - 1, y + 1);
        if (c.isValid() && Board.getPiece(c) != null && !sameTeam(c)) {
            re.add(c);
        }
        return re;
    }

    @Override
    public String getString() {
        return "\u265F";
    }
}
