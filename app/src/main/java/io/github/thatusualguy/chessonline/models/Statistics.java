package io.github.thatusualguy.chessonline.models;

public class Statistics {
	public Stat elo;
	public Stat wins;

	public static class Stat{
		public int value;
		public String info;

		public Stat(int value, String info){
			this.value = value;
			this.info = info;
		}
	}

}


