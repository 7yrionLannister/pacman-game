package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;
import dataStructures.AdjacencyListGraph;
import dataStructures.IGraph;
import model.Ghost.State;

public class Game {
	
	/**
	 */
	public final static String GRAPH_RESOURCE = "resources/map.txt";
	/**
	 */
	public final static byte POINTS_PER_DOT = 30;
	/**
	 */
	public final static byte POINTS_PER_ENERGIZER = 70;
	/**
	 */
	public static int POINTS_EXTRA_LIVE = 5000;
	/**
	 */
	private int initialNumberOfDots;
	
	/**
	 */
	private  Coordinate leftTileOfTheTunel;
	/**
	 */
	private  Coordinate rightTileOfTheTunel;
	/**
	 */
	private Coordinate bonusTile;
	
	/**
	 */
	private IGraph<Coordinate> map;
	/**
	 */
	private ArrayList<Level> levels;
	/**
	 */
	private int currentStage;
	/**
	 */
	private ArrayList<Coordinate> coordinates;
	/**
	 */
	private HashMap<Coordinate, Food> food;
	/**
	 */
	private boolean runningLinux;
	
	/**
	 */
	private Pacman pacman;

	/**
	 */
	private Ghost inky;
	/**
	 */
	private Ghost pinky;
	/**
	 */
	private Ghost blinky;
	/**
	 */
	private Ghost clyde;
	
	/**
	 */
	private int score;
	/**
	 */
	private long frightenedCountdown;
	/**
	 */
	private int ghostsEaten;
	
	/**
	 */
	private Timer toggleModesTimer;
	/**
	 */
	private Sequence firstLevelSequence;
	/**
	 */
	private Sequence levelsTwoToFourSequence;
	/**
	 */
	private Sequence fifthLevelAndAbove;
	
	/**Creates a game starting all the initial levels, characters, maze, coordinates with respect to a determinate sequence.  
	 */
	public Game() throws IOException {
		runningLinux = System.getProperty("os.name").equals("Linux");

		firstLevelSequence = new Sequence(7000, 20000, 7000, 20000, 5000, 20000, 5000);
		levelsTwoToFourSequence = new Sequence(7000, 20000, 7000, 20000, 5000, 240000, 6000);
		fifthLevelAndAbove = new Sequence(5000, 20000, 5000, 20000, 5000, 300000, 4000);

		initLevels();
		initGraph();
		initCharacters();
		initCoordinates();
		toggleModesTimer = new Timer("toggle modes");
		setCharactersToInitialTiles();
	}
	
	/**
	 */
	private void initCharacters() {
		Coordinate tile = coordinates.get(45);
		double xCoord = tile.getX()+15;
		pacman = new Pacman(tile, xCoord, tile.getY());
		tile = coordinates.get(99);
		pinky = new Ghost(tile, tile, "pinky", tile.getX(), tile.getY()+7);
		pinky.setDirection(Direction.UP);
		pinky.setTarget(coordinates.get(17));
		tile = coordinates.get(98);
		blinky = new Ghost(tile, pinky.getHouse(), "blinky", xCoord, tile.getY());
		blinky.setDirection(Direction.LEFT);
		blinky.setTarget(coordinates.get(68));
		tile = coordinates.get(100);
		inky = new Ghost(tile, tile, "inky", tile.getX(), tile.getY());
		inky.setDirection(Direction.DOWN);
		inky.setTarget(coordinates.get(77));
		tile = coordinates.get(101);
		clyde = new Ghost(tile, tile, "clyde", tile.getX(), tile.getY());
		clyde.setDirection(Direction.DOWN);
		clyde.setTarget(coordinates.get(26));
	}
	
	/**
	 */
	private void initLevels() {
		levels = new ArrayList<>();
		//levels.add(new Level(stage, bonus, pacmanSpeed, pacmanEatingDotsSpeed, ghostsSpeed, ghostsTunelSpeed, cruiseElroyDotsLeft1, cruiseElroySpeed1, cruiseElroyDotsLeft2, cruiseElroySpeed2, pacmanWithEnergizerSpeed, pacmanWithEnergizerEatingDotsSpeed, frightGhostsSpeed, frightTime))
		//1
		Level level = new Level(1, Level.CHERRIES, 17, 20, 19, 35, 20, 17, 10, 16, 17, 18, 28, 6000);
		levels.add(level);
		//2
		level = new Level(2, Level.STRAWBERRY, 16, 18, 16, 31, 30, 16, 15, 15, 15, 17, 25, 5000);
		levels.add(level);
		//3
		level = level.clone();
		level.setStage(3);
		level.setBonus(Level.PEACH);
		level.setCruiseElroyDotsLeft1(40);
		level.setCruiseElroyDotsLeft2(20);
		level.setFrightTime(4000);
		levels.add(level);
		//4
		level = level.clone();
		level.setStage(4);
		level.setFrightTime(3000);
		levels.add(level);
		//5
		level = new Level(5, Level.APPLE, 14, 16, 15, 28, 40, 14, 20, 13, 14, 16, 23, 2000);
		levels.add(level);
		//6
		level = level.clone();
		level.setStage(6);
		level.setCruiseElroyDotsLeft1(50);
		level.setCruiseElroyDotsLeft2(25);
		level.setFrightTime(5000);
		levels.add(level);
		//7
		level = level.clone();
		level.setStage(7);
		level.setBonus(Level.MELON);
		level.setFrightTime(2000);
		levels.add(level);
		//8
		level = level.clone();
		level.setStage(8);
		levels.add(level);
		//9
		level = level.clone();
		level.setStage(9);
		level.setBonus(Level.GALAXIAN);
		level.setCruiseElroyDotsLeft1(60);
		level.setCruiseElroyDotsLeft2(30);
		level.setFrightTime(1000);
		levels.add(level);
		//10
		level = level.clone();
		level.setStage(10);
		level.setFrightTime(5000);
		levels.add(level);
		//11
		level = level.clone();
		level.setStage(11);
		level.setBonus(Level.BELL);
		level.setFrightTime(2000);
		levels.add(level);
		//12
		level = level.clone();
		level.setStage(12);
		level.setCruiseElroyDotsLeft1(80);
		level.setCruiseElroyDotsLeft2(40);
		level.setFrightTime(1000);
		levels.add(level);
		//13
		level = level.clone();
		level.setStage(13);
		level.setBonus(Level.KEYS);
		levels.add(level);
		//14
		level = level.clone();
		level.setStage(14);
		level.setFrightTime(3000);
		levels.add(level);
		//15
		level = level.clone();
		level.setStage(15);
		level.setCruiseElroyDotsLeft1(100);
		level.setCruiseElroyDotsLeft2(50);
		level.setFrightTime(1000);
		levels.add(level);
		//16
		level = level.clone();
		level.setStage(16);
		levels.add(level);
		//17
		level = level.clone();
		level.setStage(17);
		levels.add(level);
		//18
		level = level.clone();
		level.setStage(18);
		levels.add(level);
		//19
		level = level.clone();
		level.setStage(19);
		level.setCruiseElroyDotsLeft1(120);
		level.setCruiseElroyDotsLeft2(60);
		levels.add(level);
		//20
		level = level.clone();
		level.setStage(20);
		levels.add(level);
		//21
		level = level.clone();
		level.setStage(21);
		level.setPacmanSpeed(15);
		level.setPacmanEatingDotsSpeed(18);
		level.setCruiseElroyDotsLeft2(60);
		levels.add(level);
		//The other levels have the same parameters as level 21	
	}
	
	/**
	 */
	private void initGraph() throws IOException {
		FileReader fr = new FileReader(new File(GRAPH_RESOURCE));
		BufferedReader br = new BufferedReader(fr);

		map = new AdjacencyListGraph<Coordinate>(true);
		//map = new AdjacencyMatrixGraph<>(102, true);
		coordinates = new ArrayList<>();
		food = new HashMap<>();
		String line = br.readLine();
		while(!line.equalsIgnoreCase("edges:")) {
			String[] coord = line.split(",");
			double xCoord = Double.parseDouble(coord[0]);
			if(!runningLinux) { 
				xCoord += 5;
			}
			Coordinate toAdd = new Coordinate(xCoord, Double.parseDouble(coord[1]), Boolean.parseBoolean(coord[2]), Boolean.parseBoolean(coord[3]), Boolean.parseBoolean(coord[4]), Boolean.parseBoolean(coord[5]));
			coordinates.add(toAdd);
			map.insertVertex(toAdd);
			line = br.readLine();
		}
		line = br.readLine();
		while(line != null) {
			String[] sdw = line.split(",");
			map.link(coordinates.get(Integer.parseInt(sdw[0])-1), coordinates.get(Integer.parseInt(sdw[1])-1), Integer.parseInt(sdw[2]));
			line = br.readLine();
		}
		map.FloydWarshall();
		fr.close();
		br.close();
	}
	
	/**
	 */
	private void initCoordinates() {
		for(Coordinate coor : coordinates) {
			food.put(coor, new Food(Food.PACDOT, true));
			initialNumberOfDots++;
		}
		food.get(coordinates.get(1)).setType(Food.ENERGIZER);
		food.get(coordinates.get(88)).setType(Food.ENERGIZER);
		food.get(coordinates.get(6)).setType(Food.ENERGIZER);
		food.get(coordinates.get(93)).setType(Food.ENERGIZER);

		//no food around the ghosts' house
		food.get(coordinates.get(31)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(32)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(33)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(42)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(43)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(52)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(53)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(61)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(62)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(63)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(98)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(99)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(100)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(101)).setType(Food.NOTHING);	initialNumberOfDots--;

		//no food in the tunnel
		food.get(coordinates.get(4)).setType(Food.NOTHING);		initialNumberOfDots--;
		food.get(coordinates.get(12)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(82)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(91)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(96)).setType(Food.NOTHING);	initialNumberOfDots--;
		food.get(coordinates.get(97)).setType(Food.NOTHING);	initialNumberOfDots--;

		leftTileOfTheTunel = coordinates.get(96);
		rightTileOfTheTunel = coordinates.get(97);
		bonusTile = coordinates.get(102);
		food.put(bonusTile, new Food(Food.BONUS, false));	initialNumberOfDots--;
		
		getCurrentLevel().setDotsLeft(initialNumberOfDots);
	}
	
	/**
	 */
	public void setCharactersToInitialTiles() {
		Ghost.state = State.SCATTER;
		toggleModesTimer.cancel();
		toggleModesTimer.purge();

		Coordinate tile = coordinates.get(45);
		double xCoord = tile.getX()+15;
		pacman.setPosition(tile);
		pacman.setPosX(xCoord);
		pacman.setPosY(tile.getY());
		pacman.setDirection(Direction.LEFT);
		tile = coordinates.get(99);
		pinky.setPosition(tile);
		pinky.setPosX(tile.getX());
		pinky.setPosY(tile.getY()+7);
		pinky.setDirection(Direction.UP);
		pinky.setTarget(coordinates.get(17));
		tile = coordinates.get(98);
		blinky.setPosition(tile);
		blinky.setPosX(xCoord);
		blinky.setPosY(tile.getY());
		blinky.setDirection(Direction.LEFT);
		blinky.setTarget(coordinates.get(68));
		tile = coordinates.get(100);
		inky.setPosition(tile);
		inky.setPosX(tile.getX());
		inky.setPosY(tile.getY());
		inky.setDirection(Direction.DOWN);
		inky.setTarget(coordinates.get(77));
		tile = coordinates.get(101);
		clyde.setPosition(tile);
		clyde.setPosX(tile.getX());
		clyde.setPosY(tile.getY());
		clyde.setDirection(Direction.DOWN);
		clyde.setTarget(coordinates.get(26));

		searchGhostTarget(blinky);
		searchGhostTarget(inky);
		searchGhostTarget(pinky);
		searchGhostTarget(clyde);
	}
	
	/**
	 */
	public void startSequence() {
		toggleModesTimer.cancel();
		toggleModesTimer.purge();
		toggleModesTimer = new Timer("toggle modes");
		Sequence current = getCurrentSequence();
		toggleModesTimer.schedule(current.getToChaseTimerTask(), current.getToChase1());
		toggleModesTimer.schedule(current.getToScatterTimerTask(), current.getToScatter1());
		toggleModesTimer.schedule(current.getToChaseTimerTask(), current.getToChase2());
		toggleModesTimer.schedule(current.getToScatterTimerTask(), current.getToScatter2());
		toggleModesTimer.schedule(current.getToChaseTimerTask(), current.getToChase3());
		toggleModesTimer.schedule(current.getToScatterTimerTask(), current.getToScatter3());
		toggleModesTimer.schedule(current.getToChaseTimerTask(), current.getToChaseForEver());
	}
	/**
	 * @return
	 */
	private Sequence getCurrentSequence() {
		Level currentLvl = getCurrentLevel();
		Sequence toReturn = null;
		if(currentLvl.getStage() == 1) {
			toReturn = firstLevelSequence;
		} else if(currentLvl.getStage() < 5) {
			toReturn = levelsTwoToFourSequence;
		} else {
			toReturn = fifthLevelAndAbove;
		}
		return toReturn;
	}
	/**
	 */
	public void restartMap() {
		food.forEach(new BiConsumer<Coordinate, Food>() {
			@Override
			public void accept(Coordinate t, Food u) {
				u.setType(Food.PACDOT);
				u.setNotEaten(true);
			}
		});
		Food f = food.get(coordinates.get(1));
		f.setType(Food.ENERGIZER);	f.setNotEaten(true);
		f = food.get(coordinates.get(88));
		f.setType(Food.ENERGIZER);	f.setNotEaten(true);
		f = food.get(coordinates.get(6));
		f.setType(Food.ENERGIZER);	f.setNotEaten(true);
		f = food.get(coordinates.get(93));
		f.setType(Food.ENERGIZER);	f.setNotEaten(true);

		//no food around the ghosts' house
		food.get(coordinates.get(31)).setType(Food.NOTHING);
		food.get(coordinates.get(32)).setType(Food.NOTHING);
		food.get(coordinates.get(33)).setType(Food.NOTHING);
		food.get(coordinates.get(42)).setType(Food.NOTHING);
		food.get(coordinates.get(43)).setType(Food.NOTHING);
		food.get(coordinates.get(52)).setType(Food.NOTHING);
		food.get(coordinates.get(53)).setType(Food.NOTHING);
		food.get(coordinates.get(61)).setType(Food.NOTHING);
		food.get(coordinates.get(62)).setType(Food.NOTHING);
		food.get(coordinates.get(63)).setType(Food.NOTHING);
		food.get(coordinates.get(98)).setType(Food.NOTHING);
		food.get(coordinates.get(99)).setType(Food.NOTHING);
		food.get(coordinates.get(100)).setType(Food.NOTHING);
		food.get(coordinates.get(101)).setType(Food.NOTHING);

		//no food in the tunnel
		food.get(coordinates.get(4)).setType(Food.NOTHING);
		food.get(coordinates.get(12)).setType(Food.NOTHING);
		food.get(coordinates.get(82)).setType(Food.NOTHING);
		food.get(coordinates.get(91)).setType(Food.NOTHING);
		food.get(coordinates.get(96)).setType(Food.NOTHING);
		food.get(coordinates.get(97)).setType(Food.NOTHING);

		food.get(bonusTile).setType(Food.BONUS);
		food.get(bonusTile).setNotEaten(false);
		
		getCurrentLevel().setDotsLeft(initialNumberOfDots);
	}
	/**
	 */
	public void movePacman() {
		move();
		if(pacman.getPosY() == pacman.getPosition().getY() && pacman.getPosX() == pacman.getPosition().getX()) {
			if(((pacman.getPosition().hasRightTile() && pacman.getRequestedDirection() == Direction.RIGHT) || (pacman.getPosition().hasLeftTile() && pacman.getRequestedDirection() == Direction.LEFT)) || ((pacman.getPosition().hasUpTile() && pacman.getRequestedDirection() == Direction.UP) || (pacman.getPosition().hasDownTile() && pacman.getRequestedDirection() == Direction.DOWN))) {
				pacman.setDirection(pacman.getRequestedDirection());
			}

			switch(food.get(pacman.getPosition()).getType()) {
			case Food.ENERGIZER:
				blinky.setFrightened(true);
				inky.setFrightened(true);
				pinky.setFrightened(true);
				clyde.setFrightened(true);

				getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
				setScore(getScore() + POINTS_PER_ENERGIZER);
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						ghostsEaten = 0;
						blinky.setFrightened(false);
						inky.setFrightened(false);
						pinky.setFrightened(false);
						clyde.setFrightened(false);
					}
				};
				Timer timer = new Timer("Timer");
				frightenedCountdown = getCurrentLevel().getFrightTime();
				timer.schedule(task, frightenedCountdown);
				break;
			case Food.PACDOT:
				getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
				setScore(getScore() + POINTS_PER_DOT);
				break;
			case Food.BONUS:
				if(food.get(pacman.getPosition()).getNotEaten().get()) {
					setScore(getScore() + getCurrentLevel().getBonusScore());
				}
				break;
			}

			food.get(pacman.getPosition()).setType(Food.NOTHING); //pacman ate
			if(getCurrentLevel().getDotsLeft() == initialNumberOfDots/2) { //bonus at half way
				food.get(bonusTile).setNotEaten(true);
			}
		}
	}
	/**
	 */
	private void move() {
		switch(pacman.getDirection()) {
		case DOWN:
			pacman.setPosY(pacman.getPosY()+1);
			if(pacman.getPosition().hasDownTile() && pacman.getPosY() == pacman.getPosition().getY()+1) {
				for(Coordinate neighbor : map.getAdjacent(pacman.getPosition())) {
					if(neighbor.getX() == pacman.getPosition().getX() && neighbor.getY() > pacman.getPosY()) {
						pacman.setPosition(neighbor);
						break;
					}
				}
			} 
			if(!pacman.getPosition().hasDownTile() && pacman.getPosY() > pacman.getPosition().getY()){
				pacman.setPosY(pacman.getPosY()-1);
			}
			break;
		case LEFT:
			pacman.setPosX(pacman.getPosX()-1);
			if(!pacman.getPosition().equals(rightTileOfTheTunel)) {
				if(pacman.getPosition().hasLeftTile() && pacman.getPosX() == pacman.getPosition().getX()-1) {
					if(pacman.getPosition().equals(leftTileOfTheTunel)) {
						pacman.setPosition(rightTileOfTheTunel);
						pacman.setPosX(pacman.getPosition().getX());
						pacman.setPosY(pacman.getPosition().getY());
					} else {
						for(Coordinate neighbor : map.getAdjacent(pacman.getPosition())) {
							if(neighbor.getY() == pacman.getPosition().getY() && neighbor.getX() < pacman.getPosX()) {
								pacman.setPosition(neighbor);
								break;
							}
						}
					}
				}
			} else if(pacman.getPosX() < pacman.getPosition().getX()) {
				ArrayList<Coordinate> adj = map.getAdjacent(pacman.getPosition());
				Coordinate pos = adj.get(0);
				for(Coordinate neighbor : adj) {
					if(neighbor.getX() > pos.getX()) { 
						pos = neighbor;
					}
				}
				pacman.setPosition(pos);
			}

			if(!pacman.getPosition().hasLeftTile() && pacman.getPosX() < pacman.getPosition().getX()) {
				pacman.setPosX(pacman.getPosX()+1);
			}
			break;
		case RIGHT:
			pacman.setPosX(pacman.getPosX()+1);
			if(!pacman.getPosition().equals(leftTileOfTheTunel)) {
				if(pacman.getPosition().hasRightTile() && pacman.getPosX() == pacman.getPosition().getX()+1) {
					if(pacman.getPosition().equals(rightTileOfTheTunel)) {
						pacman.setPosition(leftTileOfTheTunel);
						pacman.setPosX(pacman.getPosition().getX());
						pacman.setPosY(pacman.getPosition().getY());
					} else {
						for(Coordinate neighbor : map.getAdjacent(pacman.getPosition())) {
							if(neighbor.getY() == pacman.getPosition().getY() && neighbor.getX() > pacman.getPosX()) {
								pacman.setPosition(neighbor);
								break;
							}
						}
					}
				}
			} else if(pacman.getPosX() > pacman.getPosition().getX()) {
				ArrayList<Coordinate> adj = map.getAdjacent(pacman.getPosition());
				Coordinate pos = adj.get(0);
				for(Coordinate neighbor : adj) {
					if(neighbor.getX() < pos.getX()) { 
						pos = neighbor;
					}
				}
				pacman.setPosition(pos);
			}

			if(!pacman.getPosition().hasRightTile() && pacman.getPosX() > pacman.getPosition().getX()) {
				pacman.setPosX(pacman.getPosX()-1);
			}
			break;
		case UP:
			pacman.setPosY(pacman.getPosY()-1);
			if(pacman.getPosition().hasUpTile() && pacman.getPosY() == pacman.getPosition().getY()-1) {
				for(Coordinate neighbor : map.getAdjacent(pacman.getPosition())) {
					if(neighbor.getX() == pacman.getPosition().getX() && neighbor.getY() < pacman.getPosY()) {
						pacman.setPosition(neighbor);
						break;
					}
				}
			} 
			if(!pacman.getPosition().hasUpTile() && pacman.getPosY() < pacman.getPosition().getY()) {
				pacman.setPosY(pacman.getPosY()+1);
			}
			break;
		}
	}
	/**
	 */
	public void searchBlinkyTarget() {
		switch(Ghost.state) {
		case CHASE:
			blinky.setTarget(pacman.getPosition());
			break;
		case SCATTER:
			Level lvl = getCurrentLevel();
			if(lvl.getDotsLeft() <= lvl.getCruiseElroyDotsLeft1()) { //Aggressive even when his brothers are in scatter mode but (TODO) he waits for Clyde to reach its scatter tile before pursuing Pacman in the first scatter period
				blinky.setTarget(pacman.getPosition());
			} else if(blinky.getTarget().equals(coordinates.get(70))) {
				blinky.setTarget(coordinates.get(79));
			} else if(blinky.getTarget().equals(coordinates.get(79))) {
				blinky.setTarget(coordinates.get(89));
			} else {
				blinky.setTarget(coordinates.get(70));
			}
		}
	}
	/**
	 */
	public void searchInkyTarget() {
		switch(Ghost.state) {
		case CHASE:
			ArrayList<Coordinate> adj = map.getAdjacent(pacman.getPosition());
			adj = map.getAdjacent(adj.get((int)(Math.random()*adj.size())));
			adj = map.getAdjacent(adj.get((int)(Math.random()*adj.size())));
			adj = map.getAdjacent(adj.get((int)(Math.random()*adj.size())));
			adj = map.getAdjacent(adj.get((int)(Math.random()*adj.size())));
			inky.setTarget(adj.get((int)(Math.random()*adj.size())));
			break;
		case SCATTER:
			if(inky.getTarget().equals(coordinates.get(56))) {
				inky.setTarget(coordinates.get(77));
			} else if(inky.getTarget().equals(coordinates.get(77))) {
				inky.setTarget(coordinates.get(86));
			} else {
				inky.setTarget(coordinates.get(56));
			}
			break;
		}
	}
	/**
	 */
	public void searchPinkyTarget() {
		switch(Ghost.state) {
		case CHASE:
			ArrayList<Coordinate> adj = map.getAdjacent(pacman.getPosition());
			adj = map.getAdjacent(adj.get((int)(Math.random()*adj.size())));
			adj = map.getAdjacent(adj.get((int)(Math.random()*adj.size())));
			adj = map.getAdjacent(adj.get((int)(Math.random()*adj.size())));
			adj = map.getAdjacent(adj.get((int)(Math.random()*adj.size())));
			pinky.setTarget(adj.get((int)(Math.random()*adj.size())));
			break;
		case SCATTER:
			if(pinky.getTarget().equals(coordinates.get(9))) {
				pinky.setTarget(coordinates.get(2));
			} else if(pinky.getTarget().equals(coordinates.get(2))){
				pinky.setTarget(coordinates.get(19));
			} else {
				pinky.setTarget(coordinates.get(9));
			}
			break;
		}
	}
	/**
	 */
	public void searchClydeTarget() {
		switch(Ghost.state) {
		case CHASE:
			if(map.getDistance(clyde.getPosition(), pacman.getPosition()) <= 3) {
				if(clyde.getTarget().equals(coordinates.get(46))) {
					clyde.setTarget(coordinates.get(26));
				} else if(clyde.getTarget().equals(coordinates.get(26))) {
					clyde.setTarget(coordinates.get(16));
				} else {
					clyde.setTarget(coordinates.get(46));
				}
			} else {
				clyde.setTarget(pacman.getPosition());
			}
			break;
		case SCATTER:
			if(clyde.getTarget().equals(coordinates.get(46))) {
				clyde.setTarget(coordinates.get(26));
			} else if(clyde.getTarget().equals(coordinates.get(26))) {
				clyde.setTarget(coordinates.get(16));
			} else {
				clyde.setTarget(coordinates.get(46));
			}
			break;
		}
	}
	/**
	 * @param ghost
	 */
	public void moveGhost(Ghost ghost) {
		int difX = (int)Math.abs(ghost.getPosX() - pacman.getPosX());
		int difY = (int)Math.abs(ghost.getPosY() - pacman.getPosY());
		if(difX <= 2 && difY <= 2) {
			if(ghost.isFrightened()) {
				ghostsEaten++;
				if(ghostsEaten == 1) {
					score += Level.ONE_GHOST_SCORE;
				} else if(ghostsEaten == 2) {
					score += Level.TWO_GHOSTS_SCORE;
				} else if(ghostsEaten == 3) {
					score += Level.THREE_GHOSTS_SCORE;
				} else {
					score += Level.FOUR_GHOSTS_SCORE;
				}
				ghost.setFrightened(false);
				ghost.setGoingHome(true);
				searchGhostTarget(ghost);
			} else if(!ghost.isGoingHome().get()){
				//TODO pacman dies
				pacman.setLives(pacman.getLives()-1);
				blinky.setPosition(coordinates.get(98));
				pinky.setPosition(pinky.getHouse());
				inky.setPosition(inky.getHouse());
				clyde.setPosition(clyde.getHouse());
				Ghost.state = State.SCATTER;
				pacman.setDying(true);
			}
		}
		if(!ghost.getPath().isEmpty()) {
			Coordinate next = ghost.getPath().get(0);
			if(ghost.getPosX() == next.getX() && ghost.getPosY() == next.getY()) {
				ghost.setPosition(ghost.getPath().remove(0));
				determineDirection(ghost);
				if(Ghost.state == State.CHASE) {
					searchGhostTarget(ghost);
				}
			} else if((ghost.getPosition().equals(leftTileOfTheTunel) && ghost.getPath().get(0).equals(rightTileOfTheTunel))
					|| (ghost.getPosition().equals(rightTileOfTheTunel) && ghost.getPath().get(0).equals(leftTileOfTheTunel))) {
				ghost.setPosition(ghost.getPath().remove(0));
				determineDirection(ghost);
				ghost.setPosX(ghost.getPosition().getX());
				ghost.setPosY(ghost.getPosition().getY());
			}
		} else {
			searchGhostTarget(ghost);
		}
		switch(ghost.getDirection()) {
		case DOWN:
			ghost.setPosY(ghost.getPosY()+1);
			if(ghost.getPath().isEmpty() && ghost.getPosY() > ghost.getPosition().getY()){
				ghost.setPosY(ghost.getPosY()-1);
			}
			ghost.setPosX(ghost.getPosition().getX());
			break;
		case LEFT:
			ghost.setPosX(ghost.getPosX()-1);
			ghost.setPosY(ghost.getPosition().getY());
			if(ghost.getPath().isEmpty() && ghost.getPosX() < ghost.getPosition().getX()){
				ghost.setPosX(ghost.getPosX()+1);
			}
			break;
		case RIGHT:
			ghost.setPosX(ghost.getPosX()+1);
			ghost.setPosY(ghost.getPosition().getY());
			if(ghost.getPath().isEmpty() && ghost.getPosX() > ghost.getPosition().getX()){
				ghost.setPosX(ghost.getPosX()-1);
			}
			break;
		case UP:
			ghost.setPosY(ghost.getPosY()-1);
			if(ghost.getPath().isEmpty() && ghost.getPosY() < ghost.getPosition().getY()){
				ghost.setPosY(ghost.getPosY()+1);
			}
			ghost.setPosX(ghost.getPosition().getX());
			break;
		}
	}
	/**
	 * @param ghost
	 */
	public void searchGhostTarget(Ghost ghost) {
		String name = ghost.getName();
		if(name.equalsIgnoreCase("blinky")) {
			searchBlinkyTarget();
		} else if(name.equalsIgnoreCase("inky")) {
			searchInkyTarget();
		} else if(name.equalsIgnoreCase("pinky")) {
			searchPinkyTarget();
		} else {
			searchClydeTarget();
		}

		if(ghost.isFrightened()) {
			ghost.setTarget(coordinates.get((int)(Math.random()*(coordinates.size()-3))));
		} else if(ghost.isGoingHome().get()) {
			ghost.setTarget(ghost.getHouse());
			ghost.setPosX(ghost.getPosition().getX());
			ghost.setPosY(ghost.getPosition().getY());
		}
		ghost.setPath(map.getPath(ghost.getPosition(), ghost.getTarget()));
		ghost.getPath().remove(0);
		determineDirection(ghost);
	}
	/**
	 * @param ghost
	 */
	private void determineDirection(Ghost ghost) {
		if(!ghost.getPath().isEmpty()) {
			Coordinate current = ghost.getPosition();
			Coordinate next = ghost.getPath().get(0);
			if((current.equals(rightTileOfTheTunel) && next.equals(leftTileOfTheTunel))
					|| (next.getX() > current.getX())) {
				ghost.setDirection(Direction.RIGHT);
			} else if((current.equals(leftTileOfTheTunel) && next.equals(rightTileOfTheTunel))
					|| (next.getX() < current.getX())) {
				ghost.setDirection(Direction.LEFT);
			} else if(next.getY() > current.getY()) {
				ghost.setDirection(Direction.DOWN);
			} else {
				ghost.setDirection(Direction.UP);
			} 
		}
	}
	/**
	 */
	public boolean isEatingDots() {
		byte type = food.get(pacman.getPosition()).getType();
		return type == Food.PACDOT || type == Food.ENERGIZER;
	}
	/**
	 * @param ghost
	 * @return
	 */
	public boolean isInTheTunnel(Ghost ghost) {
		Coordinate pos = ghost.getPosition();
		boolean tunnel =  pos.equals(coordinates.get(96));
		tunnel |= pos.equals(coordinates.get(97));
		tunnel |= pos.equals(coordinates.get(4));
		tunnel |= pos.equals(coordinates.get(12));
		tunnel |= pos.equals(coordinates.get(82));
		tunnel |= pos.equals(coordinates.get(91));
		return tunnel;
	}
	/**
	 * @return
	 */
	public IGraph<Coordinate> getMap() {
		return map;
	}
	/**
	 * @param map
	 */
	public void setMap(IGraph<Coordinate> map) {
		this.map = map;
	}
	/**
	 * @return
	 */
	public ArrayList<Level> getLevels() {
		return levels;
	}
	/**
	 * @param levels
	 */
	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}
	/**
	 * @return
	 */
	public Level getCurrentLevel() {
		if(currentStage < levels.size()) {
			return levels.get(currentStage);
		} else {
			return levels.get(levels.size()-1);
		}
	}
	/**
	 * @return
	 */
	public int getCurrentStage() {
		return currentStage;
	}
	/**
	 * @param stage
	 */
	public void setCurrentStage(int stage) {
		currentStage = stage;
		Level c = getCurrentLevel();
		c.setDotsLeft(initialNumberOfDots);
	}
	/**
	 * @return
	 */
	public Ghost getInky() {
		return inky;
	}
	/**
	 * @return
	 */
	public Ghost getPinky() {
		return pinky;
	}
	/**
	 * @return
	 */
	public Ghost getBlinky() {
		return blinky;
	}
	/**
	 * @return
	 */
	public Ghost getClyde() {
		return clyde;
	}
	/**
	 * @return
	 */
	public Pacman getPacman() {
		return pacman;
	}
	/**
	 * @return
	 */
	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}
	/**
	 * @return
	 */
	public HashMap<Coordinate, Food> getFood() {
		return food;
	}
	/**
	 * @return
	 */
	public Coordinate getLeftTileOfTheTunel() {
		return leftTileOfTheTunel;
	}
	/**
	 * @return
	 */
	public Coordinate getRightTileOfTheTunel() {
		return rightTileOfTheTunel;
	}
	/**
	 * @return
	 */
	public Coordinate getBonusTile() {
		return bonusTile;
	}
	/**
	 * @return
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
		if(score >= POINTS_EXTRA_LIVE && pacman.getLives() < 5) {
			pacman.setLives(pacman.getLives()+1);
			POINTS_EXTRA_LIVE += 5000;
		}
	}
	/**
	 * @return
	 */
	public boolean atLeastAFrightenedGhost() {
		return blinky.isFrightened() || inky.isFrightened() || pinky.isFrightened() || clyde.isFrightened();
	}
	/**
	 * @param name
	 * @return
	 */
	//if name does not match anything then return clyde
	public Ghost getGhost(String name) {
		if(name.equalsIgnoreCase(blinky.getName())) {
			return blinky;
		} else if(name.equalsIgnoreCase(inky.getName())) {
			return inky;
		} else if(name.equalsIgnoreCase(pinky.getName())) {
			return pinky;
		} else {
			return clyde;
		}
	}
	/**
	 * @return
	 */
	public long getFrightenedCountdown() {
		return frightenedCountdown;
	}
	/**
	 * @param timeForWarning
	 */
	public void setFrightenedCountdown(long timeForWarning) {
		this.frightenedCountdown = timeForWarning;
	}
	/**
	 * @return
	 */
	public int getGhostsEaten() {
		return ghostsEaten;
	}
	/**
	 * @param ghostsEaten
	 */
	public void setGhostsEaten(int ghostsEaten) {
		this.ghostsEaten = ghostsEaten;
	}
	/**
	 * @return
	 */
	public boolean isRunningLinux() {
		return runningLinux;
	}
	/**
	 * @return
	 */
	public int getInitialNumberOfDots() {
		return initialNumberOfDots;
	}
	/**
	 * @return
	 */
	public boolean allGhostsInTheirHouse() {
		return inky.isAtHome() && clyde.isAtHome() && pinky.isAtHome();
	}
}
