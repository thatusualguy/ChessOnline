
package io.github.thatusualguy.chessonline.ui.games.board.pieces;

import java.util.LinkedList;
import java.util.List;

import io.github.thatusualguy.chessonline.ui.games.board.Coordinate;

public class Knight extends Piece {

    public Knight(final Coordinate p, final String o) {
        super(p, o);
    }

    @Override
    public List<Coordinate> getPossiblePositions() {
        List<Coordinate> re = new LinkedList<Coordinate>();
        Coordinate c = new Coordinate(position.x + 2, position.y + 1);
        if (c.isValid() && !sameTeam(c)) re.add(c);

        c = new Coordinate(position.x + 2, position.y - 1);
        if (c.isValid() && !sameTeam(c)) re.add(c);

        c = new Coordinate(position.x - 2, position.y + 1);
        if (c.isValid() && !sameTeam(c)) re.add(c);

        c = new Coordinate(position.x - 2, position.y - 1);
        if (c.isValid() && !sameTeam(c)) re.add(c);

        c = new Coordinate(position.x + 1, position.y + 2);
        if (c.isValid() && !sameTeam(c)) re.add(c);

        c = new Coordinate(position.x - 1, position.y + 2);
        if (c.isValid() && !sameTeam(c)) re.add(c);

        c = new Coordinate(position.x + 1, position.y - 2);
        if (c.isValid() && !sameTeam(c)) re.add(c);

        c = new Coordinate(position.x - 1, position.y - 2);
        if (c.isValid() && !sameTeam(c)) re.add(c);

        return re;
    }

    @Override
    public String getString() {
        return "\u265E";
    }
}
