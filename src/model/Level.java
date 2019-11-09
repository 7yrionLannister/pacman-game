package model;

public class Level {
	//bonus fruits
	public final static String CHERRIES = "resources/sprites/food/bonus/cherry.png";
	public final static String STRAWBERRY = "resources/sprites/food/bonus/strawberry.png";
	public final static String PEACH = "resources/sprites/food/bonus/peach.png";
	public final static String APPLE = "resources/sprites/food/bonus/apple.png";
	public final static String MELON = "resources/sprites/food/bonus/melon.png";
	public final static String GALAXIAN = "resources/sprites/food/bonus/galaxian.png";
	public final static String BELL = "resources/sprites/food/bonus/bell.png";
	public final static String KEYS = "resources/sprites/food/bonus/keys.png";
	
	public final static int ONE_GHOST_SCORE = 200;
	public final static int TWO_GHOSTS_SCORE = 400;
	public final static int THREE_GHOSTS_SCORE = 800;
	public final static int FOUR_GHOSTS_SCORE = 1600;
	
	private int stage;
	private String bonus;
	private int pacmanSpeed;
	private int pacmanEatingDotsSpeed;
	private int ghostsSpeed;
	private int ghostsTunelSpeed;
	private int cruiseElroyDotsLeft1;
	private int cruiseElroySpeed1;
	private int cruiseElroyDotsLeft2;
	private int cruiseElroySpeed2;
	private int pacmanWithEnergizerSpeed;
	private int pacmanWithEnergizerEatingDotsSpeed;
	private int frightGhostsSpeed;
	private int frightTime; 
	private boolean frightened;
	private int dotsLeft;

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

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public int getPacmanSpeed() {
		return pacmanSpeed;
	}

	public void setPacmanSpeed(int pacmanSpeed) {
		this.pacmanSpeed = pacmanSpeed;
	}

	public int getPacmanEatingDotsSpeed() {
		return pacmanEatingDotsSpeed;
	}

	public void setPacmanEatingDotsSpeed(int pacmanEatingDotsSpeed) {
		this.pacmanEatingDotsSpeed = pacmanEatingDotsSpeed;
	}

	public int getGhostsSpeed() {
		return ghostsSpeed;
	}

	public void setGhostsSpeed(int ghostsSpeed) {
		this.ghostsSpeed = ghostsSpeed;
	}

	public int getGhostsTunelSpeed() {
		return ghostsTunelSpeed;
	}

	public void setGhostsTunelSpeed(int ghostsTunelSpeed) {
		this.ghostsTunelSpeed = ghostsTunelSpeed;
	}

	public int getCruiseElroyDotsLeft1() {
		return cruiseElroyDotsLeft1;
	}

	public void setCruiseElroyDotsLeft1(int cruiseElroyDotsLeft1) {
		this.cruiseElroyDotsLeft1 = cruiseElroyDotsLeft1;
	}

	public int getCruiseElroySpeed1() {
		return cruiseElroySpeed1;
	}

	public void setCruiseElroySpeed1(int cruiseElroySpeed1) {
		this.cruiseElroySpeed1 = cruiseElroySpeed1;
	}

	public int getCruiseElroyDotsLeft2() {
		return cruiseElroyDotsLeft2;
	}

	public void setCruiseElroyDotsLeft2(int cruiseElroyDotsLeft2) {
		this.cruiseElroyDotsLeft2 = cruiseElroyDotsLeft2;
	}

	public int getCruiseElroySpeed2() {
		return cruiseElroySpeed2;
	}

	public void setCruiseElroySpeed2(int cruiseElroySpeed2) {
		this.cruiseElroySpeed2 = cruiseElroySpeed2;
	}

	public int getPacmanWithEnergizerSpeed() {
		return pacmanWithEnergizerSpeed;
	}

	public void setPacmanWithEnergizerSpeed(int pacmanWithEnergizerSpeed) {
		this.pacmanWithEnergizerSpeed = pacmanWithEnergizerSpeed;
	}

	public int getPacmanWithEnergizerEatingDotsSpeed() {
		return pacmanWithEnergizerEatingDotsSpeed;
	}

	public void setPacmanWithEnergizerEatingDotsSpeed(int pacmanWithEnergizerEatingDotsSpeed) {
		this.pacmanWithEnergizerEatingDotsSpeed = pacmanWithEnergizerEatingDotsSpeed;
	}

	public int getFrightGhostsSpeed() {
		return frightGhostsSpeed;
	}

	public void setFrightGhostsSpeed(int frightGhostsSpeed) {
		this.frightGhostsSpeed = frightGhostsSpeed;
	}

	public int getFrightTime() {
		return frightTime;
	}

	public void setFrightTime(int frightTime) {
		this.frightTime = frightTime;
	}

	public boolean isFrightened() {
		return frightened;
	}

	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
	}

	public int getDotsLeft() {
		return dotsLeft;
	}

	public void setDotsLeft(int dotsLeft) {
		this.dotsLeft = dotsLeft;
	}
	
	@Override
	public Level clone() {
		return new Level(stage, bonus, pacmanSpeed, pacmanEatingDotsSpeed, ghostsSpeed, ghostsTunelSpeed, cruiseElroyDotsLeft1, cruiseElroySpeed1, cruiseElroyDotsLeft2, cruiseElroySpeed2, pacmanWithEnergizerSpeed, pacmanWithEnergizerEatingDotsSpeed, frightGhostsSpeed, frightTime);
	}
}
