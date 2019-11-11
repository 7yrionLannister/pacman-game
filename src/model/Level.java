package model;

public class Level {

	/**It represents a bonus fruit for the game.
	 */
	public final static String CHERRIES = "resources/sprites/food/bonus/cherry.png";
	/**It represents a bonus fruit for the game.
	 */
	public final static String STRAWBERRY = "resources/sprites/food/bonus/strawberry.png";
	/**It represents a bonus fruit for the game.
	 */
	public final static String PEACH = "resources/sprites/food/bonus/peach.png";
	/**It represents a bonus fruit for the game.
	 */
	public final static String APPLE = "resources/sprites/food/bonus/apple.png";
	/**It represents a bonus fruit for the game.
	 */
	public final static String MELON = "resources/sprites/food/bonus/melon.png";
	/**It represents a bonus object for the game.
	 */
	public final static String GALAXIAN = "resources/sprites/food/bonus/galaxian.png";
	/**It represents a bonus object for the game.
	 */
	public final static String BELL = "resources/sprites/food/bonus/bell.png";
	/**It represents a bonus object for the game.
	 */
	public final static String KEYS = "resources/sprites/food/bonus/keys.png";
	
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
	/**
	 */
	private int ghostsSpeed;
	/**
	 */
	private int ghostsTunelSpeed;
	/**
	 */
	private int cruiseElroyDotsLeft1;
	/**
	 */
	private int cruiseElroySpeed1;
	/**
	 */
	private int cruiseElroyDotsLeft2;
	/**
	 */
	private int cruiseElroySpeed2;
	/**
	 */
	private int pacmanWithEnergizerSpeed;
	/**
	 */
	private int pacmanWithEnergizerEatingDotsSpeed;
	/**
	 */
	private int frightGhostsSpeed;
	/**
	 */
	private int frightTime; 
	/**
	 */
	private boolean frightened;
	/**
	 */
	private int dotsLeft;

	/**
	 * @param stage
	 * @param bonus
	 * @param pacmanSpeed
	 * @param pacmanEatingDotsSpeed
	 * @param ghostsSpeed
	 * @param ghostsTunelSpeed
	 * @param cruiseElroyDotsLeft1
	 * @param cruiseElroySpeed1
	 * @param cruiseElroyDotsLeft2
	 * @param cruiseElroySpeed2
	 * @param pacmanWithEnergizerSpeed
	 * @param pacmanWithEnergizerEatingDotsSpeed
	 * @param frightGhostsSpeed
	 * @param frightTime
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
	/**
	 * @return
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
	/**
	 * @return
	 */
	public int getStage() {
		return stage;
	}
	/**
	 * @param stage
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
	/**
	 * @return
	 */
	public String getBonus() {
		return bonus;
	}
	/**
	 * @param bonus
	 */
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	/**
	 * @return
	 */
	public int getPacmanSpeed() {
		return pacmanSpeed;
	}
	/**
	 * @param pacmanSpeed
	 */
	public void setPacmanSpeed(int pacmanSpeed) {
		this.pacmanSpeed = pacmanSpeed;
	}
	/**
	 * @return
	 */
	public int getPacmanEatingDotsSpeed() {
		return pacmanEatingDotsSpeed;
	}
	/**
	 * @param pacmanEatingDotsSpeed
	 */
	public void setPacmanEatingDotsSpeed(int pacmanEatingDotsSpeed) {
		this.pacmanEatingDotsSpeed = pacmanEatingDotsSpeed;
	}
	/**
	 * @return
	 */
	public int getGhostsSpeed() {
		return ghostsSpeed;
	}
	/**
	 * @param ghostsSpeed
	 */
	public void setGhostsSpeed(int ghostsSpeed) {
		this.ghostsSpeed = ghostsSpeed;
	}
	/**
	 * @return
	 */
	public int getGhostsTunelSpeed() {
		return ghostsTunelSpeed;
	}
	/**
	 * @param ghostsTunelSpeed
	 */
	public void setGhostsTunelSpeed(int ghostsTunelSpeed) {
		this.ghostsTunelSpeed = ghostsTunelSpeed;
	}
	/**
	 * @return
	 */
	public int getCruiseElroyDotsLeft1() {
		return cruiseElroyDotsLeft1;
	}
	/**
	 * @param cruiseElroyDotsLeft1
	 */
	public void setCruiseElroyDotsLeft1(int cruiseElroyDotsLeft1) {
		this.cruiseElroyDotsLeft1 = cruiseElroyDotsLeft1;
	}
	/**
	 * @return
	 */
	public int getCruiseElroySpeed1() {
		return cruiseElroySpeed1;
	}
	/**
	 * @param cruiseElroySpeed1
	 */
	public void setCruiseElroySpeed1(int cruiseElroySpeed1) {
		this.cruiseElroySpeed1 = cruiseElroySpeed1;
	}
	/**
	 * @return
	 */
	public int getCruiseElroyDotsLeft2() {
		return cruiseElroyDotsLeft2;
	}
	/**
	 * @param cruiseElroyDotsLeft2
	 */
	public void setCruiseElroyDotsLeft2(int cruiseElroyDotsLeft2) {
		this.cruiseElroyDotsLeft2 = cruiseElroyDotsLeft2;
	}
	/**
	 * @return
	 */
	public int getCruiseElroySpeed2() {
		return cruiseElroySpeed2;
	}
	/**
	 * @param cruiseElroySpeed2
	 */
	public void setCruiseElroySpeed2(int cruiseElroySpeed2) {
		this.cruiseElroySpeed2 = cruiseElroySpeed2;
	}
	/**
	 * @return
	 */
	public int getPacmanWithEnergizerSpeed() {
		return pacmanWithEnergizerSpeed;
	}
	/**
	 * @param pacmanWithEnergizerSpeed
	 */
	public void setPacmanWithEnergizerSpeed(int pacmanWithEnergizerSpeed) {
		this.pacmanWithEnergizerSpeed = pacmanWithEnergizerSpeed;
	}
	/**
	 * @return
	 */
	public int getPacmanWithEnergizerEatingDotsSpeed() {
		return pacmanWithEnergizerEatingDotsSpeed;
	}
	/**
	 * @param pacmanWithEnergizerEatingDotsSpeed
	 */
	public void setPacmanWithEnergizerEatingDotsSpeed(int pacmanWithEnergizerEatingDotsSpeed) {
		this.pacmanWithEnergizerEatingDotsSpeed = pacmanWithEnergizerEatingDotsSpeed;
	}
	/**
	 * @return
	 */
	public int getFrightGhostsSpeed() {
		return frightGhostsSpeed;
	}
	/**
	 * @param frightGhostsSpeed
	 */
	public void setFrightGhostsSpeed(int frightGhostsSpeed) {
		this.frightGhostsSpeed = frightGhostsSpeed;
	}
	/**
	 * @return
	 */
	public int getFrightTime() {
		return frightTime;
	}
	/**
	 * @param frightTime
	 */
	public void setFrightTime(int frightTime) {
		this.frightTime = frightTime;
	}
	/**
	 * @return
	 */
	public boolean isFrightened() {
		return frightened;
	}
	/**
	 * @param frightened
	 */
	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
	}
	/**
	 * @return
	 */
	public int getDotsLeft() {
		return dotsLeft;
	}
	/**
	 * @param dotsLeft
	 */
	public void setDotsLeft(int dotsLeft) {
		this.dotsLeft = dotsLeft;
	}
	/**
	 * @return
	 */
	@Override
	public Level clone() {
		return new Level(stage, bonus, pacmanSpeed, pacmanEatingDotsSpeed, ghostsSpeed, ghostsTunelSpeed, cruiseElroyDotsLeft1, cruiseElroySpeed1, cruiseElroyDotsLeft2, cruiseElroySpeed2, pacmanWithEnergizerSpeed, pacmanWithEnergizerEatingDotsSpeed, frightGhostsSpeed, frightTime);
	}
}
