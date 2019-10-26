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

	public final static double REFERENCE_SPEED = 100;
	
	private int stage;
	private String bonus;
	private double pacmanSpeed;
	private double pacmanEatingDotsSpeed;
	private double ghostsSpeed;
	private double ghostsTunelSpeed;
	private int cruiseElroyDotsLeft1;
	private double cruiseElroySpeed1;
	private int cruiseElroyDotsLeft2;
	private double cruiseElroySpeed2;
	private double pacmanWithEnergizerSpeed;
	private double pacmanWithEnergizerEatingDotsSpeed;
	private double frightGhostsSpeed;
	private int frightTime; 

	//speeds are values between 0 and 1 representing the percentage of the global reference speed
	public Level(int stage, String bonus, double pacmanSpeed, double pacmanEatingDotsSpeed, double ghostsSpeed,
			double ghostsTunelSpeed, int cruiseElroyDotsLeft1, double cruiseElroySpeed1, int cruiseElroyDotsLeft2,
			double cruiseElroySpeed2, double pacmanWithEnergizerSpeed, double pacmanWithEnergizerEatingDotsSpeed,
			double frightGhostsSpeed, int frightTime) {
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

	public double getPacmanSpeed() {
		return pacmanSpeed;
	}

	public void setPacmanSpeed(double pacmanSpeed) {
		this.pacmanSpeed = pacmanSpeed;
	}

	public double getPacmanEatingDotsSpeed() {
		return pacmanEatingDotsSpeed;
	}

	public void setPacmanEatingDotsSpeed(double pacmanEatingDotsSpeed) {
		this.pacmanEatingDotsSpeed = pacmanEatingDotsSpeed;
	}

	public double getGhostsSpeed() {
		return ghostsSpeed;
	}

	public void setGhostsSpeed(double ghostsSpeed) {
		this.ghostsSpeed = ghostsSpeed;
	}

	public double getGhostsTunelSpeed() {
		return ghostsTunelSpeed;
	}

	public void setGhostsTunelSpeed(double ghostsTunelSpeed) {
		this.ghostsTunelSpeed = ghostsTunelSpeed;
	}

	public int getCruiseElroyDotsLeft1() {
		return cruiseElroyDotsLeft1;
	}

	public void setCruiseElroyDotsLeft1(int cruiseElroyDotsLeft1) {
		this.cruiseElroyDotsLeft1 = cruiseElroyDotsLeft1;
	}

	public double getCruiseElroySpeed1() {
		return cruiseElroySpeed1;
	}

	public void setCruiseElroySpeed1(double cruiseElroySpeed1) {
		this.cruiseElroySpeed1 = cruiseElroySpeed1;
	}

	public int getCruiseElroyDotsLeft2() {
		return cruiseElroyDotsLeft2;
	}

	public void setCruiseElroyDotsLeft2(int cruiseElroyDotsLeft2) {
		this.cruiseElroyDotsLeft2 = cruiseElroyDotsLeft2;
	}

	public double getCruiseElroySpeed2() {
		return cruiseElroySpeed2;
	}

	public void setCruiseElroySpeed2(double cruiseElroySpeed2) {
		this.cruiseElroySpeed2 = cruiseElroySpeed2;
	}

	public double getPacmanWithEnergizerSpeed() {
		return pacmanWithEnergizerSpeed;
	}

	public void setPacmanWithEnergizerSpeed(double pacmanWithEnergizerSpeed) {
		this.pacmanWithEnergizerSpeed = pacmanWithEnergizerSpeed;
	}

	public double getPacmanWithEnergizerEatingDotsSpeed() {
		return pacmanWithEnergizerEatingDotsSpeed;
	}

	public void setPacmanWithEnergizerEatingDotsSpeed(double pacmanWithEnergizerEatingDotsSpeed) {
		this.pacmanWithEnergizerEatingDotsSpeed = pacmanWithEnergizerEatingDotsSpeed;
	}

	public double getFrightGhostsSpeed() {
		return frightGhostsSpeed;
	}

	public void setFrightGhostsSpeed(double frightGhostsSpeed) {
		this.frightGhostsSpeed = frightGhostsSpeed;
	}

	public int getFrightTime() {
		return frightTime;
	}

	public void setFrightTime(int frightTime) {
		this.frightTime = frightTime;
	}
}
