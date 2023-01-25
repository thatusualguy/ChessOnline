
package io.github.thatusualguy.chessonline.ui.games.board;

import android.util.Pair;

public class Player {

    public final String id;
    public final int team;
    public final int color;
    public final String name;
    public Pair<Coordinate, Coordinate> lastMove;

    public Player(final String i, int t, int c, final String n) {
        id = i;
        team = t;
        color = c;
        name = n;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Player && ((Player) other).id.equals(id);
    }
}
