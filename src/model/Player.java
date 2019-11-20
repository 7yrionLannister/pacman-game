package model;

import java.io.Serializable;


public class Player implements Serializable, Comparable<Player> {
	
	/**It represents the player position in the leader board.
	 */
	private String rank;
	/**It represents the score of the player in the game.
	 */
	private int score;
	/**It represents the stage where the player died.
	 */
	private int stage;
	/**It represents the name of the player.
	 */
	private String name;
	
	/**It creates a player to add in the leader board.
	 * @param rank a String that represents the player position in the leader board.
	 * @param score an Integer that represents the score of the player in the game.
	 * @param stage an Integer that represents the stage where the player died.
	 * @param name a String that represents the name of the player.
	 */
	public Player(String rank, int score, int stage, String name) {
		this.rank = rank;
		this.score = score;
		this.stage = stage;
		this.name = name;
	}
	/**It allows to obtain a String that represents the player position in the leader board. 
	 * @return a String that represents the player position in the leader board.
	 */
	public String getRank() {
		return rank;
	}
	/**It allows to set a String that represents the player position in the leader board. 
	 * @param rank is a String that represents the player position in the leader board.
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
	/**It allows to obtain an Integer that represents the score of the player in the game.
	 * @return an Integer that represents the score of the player in the game.
	 */
	public int getScore() {
		return score;
	}
	/**It allows to set an Integer that represents the score of the player in the game.
	 * @param score is an Integer that represents the score of the player in the game.
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**It allows to obtain an Integer that represents the stage where the player died.
	 * @return an Integer that represents the stage where the player died.
	 */
	public int getStage() {
		return stage;
	}
	/**It allows to set an Integer that represents the stage where the player died.
	 * @param stage is an Integer that represents the stage where the player died.
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
	/**It allows to obtain a String that represents the name of the player.
	 * @return a String that represents the name of the player.
	 */
	public String getName() {
		return name;
	}
	/**It allows to set a String that represents the name of the player.
	 * @param name is a String that represents the name of the player.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**It allows to compare two players by their score.
	 * @param p1 is another Player that will be compare with the actual one.
	 * @return an Integer showing which player has more or less score. 
	 */
	@Override
	public int compareTo(Player p1) {
		return 	Integer.compare(p1.getScore(), score);
	}
}
