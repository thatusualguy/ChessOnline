

package io.github.thatusualguy.chessonline.ui.games.board.pieces;

import java.util.List;

import io.github.thatusualguy.chessonline.ui.games.board.Coordinate;

public class Queen extends Piece {

	public Queen(final Coordinate p, final String o) {
		super(p, o);
	}

	@Override
	public List<Coordinate> getPossiblePositions() {
		List<Coordinate> re = Rook.moveStraight(this);
		re.addAll(Bishop.moveDiagonal(this));
		return re;
	}

	@Override
	public String getString() {
		return "\u265B";
	}
}
