package io.github.thatusualguy.chessonline.vm;

import io.github.thatusualguy.chessonline.grpc.ChessOnline;

public enum ChessColor {
	Random,
	Black,
	White;

	public static ChessColor fromGrpc(ChessOnline.color chosedColor) {
		switch (chosedColor) {
			case UNRECOGNIZED:
			case color_unknown:
			case color_black:
				return ChessColor.Black;
			case color_white:
				return ChessColor.White;
		}
		return Black;
	}
}
