package io.github.thatusualguy.chessonline.models;


import java.util.Date;


public class HistoryGame {
	public String id;
	public String player_1;
	public String player_2;
	public int old_opponent_elo;
	public int elo_change;
	public String winner_id;
	public Date creation_time;
	
	public HistoryGame(String id, String player_1, String player_2, int old_opponent_elo, int elo_change, String winner_id, Date creation_time){
		this.id = id;
		this.player_1 = player_1;
		this.player_2 = player_2;
		this.old_opponent_elo = old_opponent_elo;
		this.elo_change = elo_change;
		this.winner_id = winner_id;
		this.creation_time = creation_time;
	}
}
