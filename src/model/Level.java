package model;

public class Level {

	/**It represents a bonus fruit for the game.
	 */
	public final static String CHERRIES = "cherry";
	/**It represents a bonus fruit for the game.
	 */
	public final static String STRAWBERRY = "strawberry";
	/**It represents a bonus fruit for the game.
	 */
	public final static String PEACH = "peach";
	/**It represents a bonus fruit for the game.
	 */
	public final static String APPLE = "apple";
	/**It represents a bonus fruit for the game.
	 */
	public final static String MELON = "melon";
	/**It represents a bonus object for the game.
	 */
	public final static String GALAXIAN = "galaxian";
	/**It represents a bonus object for the game.
	 */
	public final static String BELL = "bell";
	/**It represents a bonus object for the game.
	 */
	public final static String KEYS = "keys";
	
	/**It represents the score when Pacman eats one ghost.
	 */
	public final static int ONE_GHOST_SCORE = 200;
	/**It represents the score when Pacman eats two ghosts consecutively.
	 */
	public final static int TWO_GHOSTS_SCORE = 400;
	/**It represents the score when Pacman eats three ghosts consecutively.
	 */
	public final static int THREE_GHOSTS_SCORE = 800;
	/**It represents the score when Pacman eats four ghosts consecutively.
	 */
	public final static int FOUR_GHOSTS_SCORE = 1600;
	
	/**It represents the actual stage. 
	 */
	private int stage;
	/**It represents the bonus points.
	 */
	private String bonus;
	/**It represents the actual Pacman speed in the maze. 
	 */
	private int pacmanSpeed;
	/**It represents the actual Pacman speed in the maze when it is eating pacdocts. 
	 */
	private int pacmanEatingDotsSpeed;
	/**It represents the ghosts speed in the game in a determinate stage.
	 */
	private int ghostsSpeed;
	/**It represents the ghosts speed when it is trying to pass along the tunnel.					
	 */
	private int ghostsTunelSpeed;
	/**It represents the number of first dots remaining at left.
	 */
	private int cruiseElroyDotsLeft1;
	/**It represents the first speed associated with the first dots remaining at left.
	 */
	private int cruiseElroySpeed1;
	/**It represents the number of second dots remaining at left.
	 */
	private int cruiseElroyDotsLeft2;
	/**It represents the second speed associated with the second dots remaining at left.
	 */
	private int cruiseElroySpeed2;
	/**It represents Pacman speed when he eats an energizer.
	 */
	private int pacmanWithEnergizerSpeed;
	/**It represents Pacman speed when he eats an energizer and besides dots.
	 */
	private int pacmanWithEnergizerEatingDotsSpeed;
	/**It represents the ghosts speed when they are frightened by the energizer eating. 
	 */
	private int frightGhostsSpeed;
	/**It represents the period during which the ghosts are frightened.
	 */
	private int frightTime; 
	/**It represents if the one ghost is frightened or not.
	 */
	private boolean frightened;
	/**It represents the remaining left dots in the maze.
	 */
	private int dotsLeft;

	/**Creates a level with a determinate stage, characters and time lapses.
	 * @param stage is an integer that represents the actual stage. 
	 * @param bonus is an integer that represents the bonus points.
	 * @param pacmanSpeed is an integer that represents the actual Pacman speed in the maze. 
	 * @param pacmanEatingDotsSpeed is an integer that represents the actual Pacman speed in the maze when it is eating pacdocts. 
	 * @param ghostsSpeed is an integer that represents the ghosts speed in the game in a determinate stage.
	 * @param ghostsTunelSpeed is an integer that represents the ghosts speed when it is trying to pass along the tunnel.
	 * @param cruiseElroyDotsLeft1 is an integer that represents the number of first dots remaining at left.
	 * @param cruiseElroySpeed1 is an integer that represents the first speed associated with the first dots remaining at left.
	 * @param cruiseElroyDotsLeft2 is an integer that represents the number of second dots remaining at left.
	 * @param cruiseElroySpeed2 is an integer that represents the second speed associated with the second dots remaining at left.
	 * @param pacmanWithEnergizerSpeed is an integer that represents Pacman speed when he eats an energizer.
	 * @param pacmanWithEnergizerEatingDotsSpeed is an integer that represents Pacman speed when he eats an energizer and besides dots.
	 * @param frightGhostsSpeed is an integer that represents the ghosts speed when they are frightened by the energizer eating. 
	 * @param frightTime is an integer that represents the period during which the ghosts are frightened.
	 */
	public Level(int stage, String bonus, int pacmanSpeed, int pacmanEatingDotsSpeed, int ghostsSpeed,
			int ghostsTunelSpeed, int cruiseElroyDotsLeft1, int cruiseElroySpeed1, int cruiseElroyDotsLeft2,
			int cruiseElroySpeed2, int pacmanWithEnergizerSpeed, int pacmanWithEnergizerEatingDotsSpeed,
			int frightGhostsSpeed, int frightTime) {
		this.stage = stage;
		this.bonus = bonus;
		this.pacmanSpeed = pacmanSpeed;
		this.pacmanEatingDotsSpeed = pacmanEatingDotsSpeed;
		this.ghostsSpeed = ghostsSpeed;
		this.ghostsTunelSpeed = ghostsTunelSpeed;
		this.cruiseElroyDotsLeft1 = cruiseElroyDotsLeft1;
		this.cruiseElroySpeed1 = cruiseElroySpeed1;
		this.cruiseElroyDotsLeft2 = cruiseElroyDotsLeft2;
		this.cruiseElroySpeed2 = cruiseElroySpeed2;
		this.pacmanWithEnergizerSpeed = pacmanWithEnergizerSpeed;
		this.pacmanWithEnergizerEatingDotsSpeed = pacmanWithEnergizerEatingDotsSpeed;
		this.frightGhostsSpeed = frightGhostsSpeed;
		this.frightTime = frightTime;
		setFrightened(false);
	}
	/**Allows to obtain the bonus points as an integer.
	 * @return An Integer that represents the bonus points.
	 */
	public int getBonusScore() {
		int bs = 0;
		switch(bonus) {
		case CHERRIES:
			bs = 100;
			break;
		case STRAWBERRY:
			bs = 300;
			break;
		case PEACH:
			bs = 500;
			break;
		case APPLE:
			bs = 700;
			break;
		case MELON:
			bs = 1000;
			break;
		case GALAXIAN:
			bs = 2000;
			break;
		case BELL:
			bs = 3000;
			break;
		case KEYS:
			bs = 5000;
			break;
		}
		return bs;
	}
	/**Allows to obtain an integer that represents the actual stage. 
	 * @return an integer that represents the actual stage. 
	 */
	public int getStage() {
		return stage;
	}
	/**Allows to set an integer that represents the actual stage. 
	 * @param stage is an integer that represents the actual stage. 
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
	/**Allows to obtain a string that represents the bonus points.
	 * @return a string that represents the bonus points.
	 */
	public String getBonus() {
		return bonus;
	}
	/**Allows to set a string that represents the bonus points.
	 * @param bonus is a string that represents the bonus points.
	 */
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	/**Allows to obtain an integer that represents the actual Pacman speed in the maze. 
	 * @return an integer that represents the actual Pacman speed in the maze.
	 */
	public int getPacmanSpeed() {
		return pacmanSpeed;
	}
	/**Allows to set an integer that represents the actual Pacman speed in the maze.
	 * @param pacmanSpeed is an integer that represents the actual Pacman speed in the maze.
	 */
	public void setPacmanSpeed(int pacmanSpeed) {
		this.pacmanSpeed = pacmanSpeed;
	}
	/**Allows to obtain an integer that represents the actual Pacman speed in the maze when it is eating pacdocts. 
	 * @return an integer that represents the actual Pacman speed in the maze when it is eating pacdocts.
	 */
	public int getPacmanEatingDotsSpeed() {
		return pacmanEatingDotsSpeed;
	}
	/**Allows to set an integer that represents the actual Pacman speed in the maze when it is eating pacdocts. 
	 * @param pacmanEatingDotsSpeed is an integer that represents the actual Pacman speed in the maze when it is eating pacdocts.
	 */
	public void setPacmanEatingDotsSpeed(int pacmanEatingDotsSpeed) {
		this.pacmanEatingDotsSpeed = pacmanEatingDotsSpeed;
	}
	/**Allows to obtain an integer that represents the ghosts speed in the game in a determinate stage.
	 * @return an integer that represents the ghosts speed in the game in a determinate stage.
	 */
	public int getGhostsSpeed() {
		return ghostsSpeed;
	}
	/**Allows to set an integer that represents the ghosts speed in the game in a determinate stage.
	 * @param ghostsSpeed is an integer that represents the ghosts speed in the game in a determinate stage.
	 */
	public void setGhostsSpeed(int ghostsSpeed) {
		this.ghostsSpeed = ghostsSpeed;
	}
	/**Allows to obtain an integer that represents the ghosts speed when it is trying to pass along the tunnel.
	 * @return an integer that represents the ghosts speed when it is trying to pass along the tunnel.
	 */
	public int getGhostsTunelSpeed() {
		return ghostsTunelSpeed;
	}
	/**Allows to set an integer that represents the ghosts speed when it is trying to pass along the tunnel.
	 * @param ghostsTunelSpeed is an integer that represents the ghosts speed when it is trying to pass along the tunnel.
	 */
	public void setGhostsTunelSpeed(int ghostsTunelSpeed) {
		this.ghostsTunelSpeed = ghostsTunelSpeed;
	}
	/**Allows to obtain an integer that represents the number of first dots remaining at left.
	 * @return an integer that represents the number of first dots remaining at left.
	 */
	public int getCruiseElroyDotsLeft1() {
		return cruiseElroyDotsLeft1;
	}
	/**Allows to set an integer that represents the number of first dots remaining at left.
	 * @param cruiseElroyDotsLeft1 is an integer that represents the number of first dots remaining at left.
	 */
	public void setCruiseElroyDotsLeft1(int cruiseElroyDotsLeft1) {
		this.cruiseElroyDotsLeft1 = cruiseElroyDotsLeft1;
	}
	/**Allows to obtain an integer that represents the first speed associated with the first dots remaining at left.
	 * @return an integer that represents the first speed associated with the first dots remaining at left.
	 */
	public int getCruiseElroySpeed1() {
		return cruiseElroySpeed1;
	}
	/**Allows to set an integer that represents the first speed associated with the first dots remaining at left.
	 * @param cruiseElroySpeed1 is an integer that represents the first speed associated with the first dots remaining at left.
	 */
	public void setCruiseElroySpeed1(int cruiseElroySpeed1) {
		this.cruiseElroySpeed1 = cruiseElroySpeed1;
	}
	/**Allows to obtain an integer that represents the number of second dots remaining at left.
	 * @return an integer that represents the number of second dots remaining at left.
	 */
	public int getCruiseElroyDotsLeft2() {
		return cruiseElroyDotsLeft2;
	}
	/**Allows to set an integer that represents the number of second dots remaining at left.
	 * @param cruiseElroyDotsLeft2 is an integer that represents the number of second dots remaining at left.
	 */
	public void setCruiseElroyDotsLeft2(int cruiseElroyDotsLeft2) {
		this.cruiseElroyDotsLeft2 = cruiseElroyDotsLeft2;
	}
	/**Allows to obtain an integer that represents the second speed associated with the second dots remaining at left.
	 * @return an integer that represents the second speed associated with the second dots remaining at left.
	 */
	public int getCruiseElroySpeed2() {
		return cruiseElroySpeed2;
	}
	/**Allows to set an integer that represents the second speed associated with the second dots remaining at left.
	 * @param cruiseElroySpeed2 is an integer that represents the second speed associated with the second dots remaining at left.
	 */
	public void setCruiseElroySpeed2(int cruiseElroySpeed2) {
		this.cruiseElroySpeed2 = cruiseElroySpeed2;
	}
	/**Allows to obtain an integer that represents Pacman speed when he eats an energizer.
	 * @return an integer that represents Pacman speed when he eats an energizer.
	 */
	public int getPacmanWithEnergizerSpeed() {
		return pacmanWithEnergizerSpeed;
	}
	/**Allows to set an integer that represents Pacman speed when he eats an energizer.
	 * @param pacmanWithEnergizerSpeed is an integer that represents Pacman speed when he eats an energizer.
	 */
	public void setPacmanWithEnergizerSpeed(int pacmanWithEnergizerSpeed) {
		this.pacmanWithEnergizerSpeed = pacmanWithEnergizerSpeed;
	}
	/**Allows to obtain an integer that represents Pacman speed when he eats an energizer and besides dots.
	 * @return an integer that represents Pacman speed when he eats an energizer and besides dots.
	 */
	public int getPacmanWithEnergizerEatingDotsSpeed() {
		return pacmanWithEnergizerEatingDotsSpeed;
	}
	/**Allows to set an integer that represents Pacman speed when he eats an energizer and besides dots.
	 * @param pacmanWithEnergizerEatingDotsSpeed is an integer that represents Pacman speed when he eats an energizer and besides dots.
	 */
	public void setPacmanWithEnergizerEatingDotsSpeed(int pacmanWithEnergizerEatingDotsSpeed) {
		this.pacmanWithEnergizerEatingDotsSpeed = pacmanWithEnergizerEatingDotsSpeed;
	}
	/**Allows to obtain an integer that represents the ghosts speed when they are frightened by the energizer eating.
	 * @return an integer that represents the ghosts speed when they are frightened by the energizer eating.
	 */
	public int getFrightGhostsSpeed() {
		return frightGhostsSpeed;
	}
	/**Allows to set an integer that represents the ghosts speed when they are frightened by the energizer eating.
	 * @param frightGhostsSpeed is an integer that represents the ghosts speed when they are frightened by the energizer eating.
	 */
	public void setFrightGhostsSpeed(int frightGhostsSpeed) {
		this.frightGhostsSpeed = frightGhostsSpeed;
	}
	/**Allows to obtain an integer that represents the period during which the ghosts are frightened.
	 * @return an integer that represents the period during which the ghosts are frightened.
	 */
	public int getFrightTime() {
		return frightTime;
	}
	/**Allows to set an integer that represents the period during which the ghosts are frightened.
	 * @param frightTime is an integer that represents the period during which the ghosts are frightened.
	 */
	public void setFrightTime(int frightTime) {
		this.frightTime = frightTime;
	}
	/**Allows to obtain a boolean that represents if the one ghost is frightened or not.
	 * @return a boolean that represents if the one ghost is frightened or not.
	 */
	public boolean isFrightened() {
		return frightened;
	}
	/**Allows to set a boolean that represents if the one ghost is frightened or not.
	 * @param frightened is a boolean that represents if the one ghost is frightened or not.
	 */
	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
	}
	/**Allows to obtain an integer that represents the remaining left dots in the maze.
	 * @return an integer that represents the remaining left dots in the maze
	 */
	public int getDotsLeft() {
		return dotsLeft;
	}
	/**Allows to set an integer that represents the remaining left dots in the maze.
	 * @param dotsLeft is an integer that represents the remaining left dots in the maze
	 */
	public void setDotsLeft(int dotsLeft) {
		this.dotsLeft = dotsLeft;
	}
	/**This clones the actual level to be used later.
	 * @return a Level that represents the clone of the actual one.
	 */
	@Override
	public Level clone() {
		return new Level(stage, bonus, pacmanSpeed, pacmanEatingDotsSpeed, ghostsSpeed, ghostsTunelSpeed, cruiseElroyDotsLeft1, cruiseElroySpeed1, cruiseElroyDotsLeft2, cruiseElroySpeed2, pacmanWithEnergizerSpeed, pacmanWithEnergizerEatingDotsSpeed, frightGhostsSpeed, frightTime);
	}
}
