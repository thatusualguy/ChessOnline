/*
 * Copyright 2015 Thomas Hoffmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.thatusualguy.chessonline.ui.games.board.pieces;

import java.util.LinkedList;
import java.util.List;

import io.github.thatusualguy.chessonline.ui.games.board.Board;
import io.github.thatusualguy.chessonline.ui.games.board.Coordinate;
import io.github.thatusualguy.chessonline.ui.games.board.Game;

/**
 * Just like a "normal" pawn, but can only move from top to down
 */
public class DownPawn extends Piece {
    public DownPawn(final Coordinate p, final String o) {
        super(p, o);
    }

    @Override
    public List<Coordinate> getPossiblePositions() {
        List<Coordinate> re = new LinkedList<Coordinate>();
        Coordinate c;
        int x = position.x;
        int y = position.y;
        c = new Coordinate(x, y - 1);
        if (c.isValid() && Board.getPiece(c) == null) {
            re.add(c);
        }
        // can move two squares at the beginning
        // (only if no other piece stands 1 before us)
        if (((Game.match.mode == Game.MODE_2_PLAYER_2_SIDES && y == 6) || (y == 10)) &&
                Board.getPiece(c) == null) {
            c = new Coordinate(x, y - 2);
            if (c.isValid() && Board.getPiece(c) == null) {
                re.add(c);
            }
        }

        // check if we can attack another piece
        c = new Coordinate(x + 1, y - 1);
        if (c.isValid() && Board.getPiece(c) != null && !sameTeam(c)) {
            re.add(c);
        }
        c = new Coordinate(x - 1, y - 1);
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
