package io.github.thatusualguy.chessonline.models;


public class WaitingGame {
	public String id;
	public long creation_time;
	public String author;
	public int elo;
	public ChessColor color;


	public WaitingGame(String id, long creation_time, String author, int elo, ChessColor color) {
		this.id = id;
		this.creation_time = creation_time;
		this.author = author;
		this.elo = elo;
		this.color = color;
	}
}
