package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import dataStructures.AdjacencyListGraph;
import dataStructures.AdjacencyMatrixGraph;
import dataStructures.IGraph;
import model.Ghost.State;


public class Game {
	public final static String GRAPH_RESOURCE = "resources/map.txt";
	public final static byte POINTS_PER_DOT = 30;
	public final static byte POINTS_PER_ENERGIZER = 70;
	private  Coordinate leftTileOfTheTunel;

	private  Coordinate rightTileOfTheTunel;

	private Coordinate bonusTile;

	private IGraph<Coordinate> map;
	private ArrayList<Level> levels;
	private int currentLevel;
	private ArrayList<Coordinate> coordinates;
	private HashMap<Coordinate, Food> food;
	private boolean runningLinux;

	private Pacman pacman;

	private Ghost inky;
	private Ghost pinky;
	private Ghost blinky;
	private Ghost clyde;

	private int score;

	public Game() throws IOException {
		runningLinux = System.getProperty("os.name").equals("Linux");
		setGameToInitialState();
		currentLevel = 0;
		leftTileOfTheTunel = coordinates.get(96);
		rightTileOfTheTunel = coordinates.get(97);
	}

	private void initCharacters() {
		Coordinate tile = coordinates.get(45);
		double xCoord = tile.getX()+15;
		if(!runningLinux) {
			xCoord -= 5; //TODO modificar hasta obtener buena pinta en MacOSX
		}
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

	private void initLevels() {
		levels = new ArrayList<>();
		//levels.add(new Level(stage, bonus, pacmanSpeed, pacmanEatingDotsSpeed, ghostsSpeed, ghostsTunelSpeed, cruiseElroyDotsLeft1, cruiseElroySpeed1, cruiseElroyDotsLeft2, cruiseElroySpeed2, pacmanWithEnergizerSpeed, pacmanWithEnergizerEatingDotsSpeed, frightGhostsSpeed, frightTime))
		//1
		levels.add(new Level(1, Level.CHERRIES, 0.8, 0.71, 0.75, 0.4, 20, 0.8, 10, 0.85, 0.9, 0.79, 0.5, 6000));
		//2
		levels.add(new Level(2, Level.STRAWBERRY, 0.9, 0.79, 0.85, 0.45, 30, 0.9, 15, 0.95, 0.95, 0.83, 0.55, 5000));
		//3
		levels.add(new Level(3, Level.PEACH, 0.9, 0.79, 0.85, 0.45, 40, 0.9, 20, 0.95, 0.95, 0.83, 0.55, 4000));
		//4
		levels.add(new Level(4, Level.PEACH, 0.9, 0.79, 0.85, 0.45, 40, 0.9, 20, 0.95, 0.95, 0.83, 0.55, 3000));
		//5
		levels.add(new Level(5, Level.APPLE, 1, 0.87, 0.95, 0.5, 40, 1, 20, 1.05, 1, 0.87, 0.6, 2000));
		//6
		levels.add(new Level(6, Level.APPLE, 1, 0.87, 0.95, 0.5, 50, 1, 25, 1.05, 1, 0.87, 0.6, 5000));
		//7
		levels.add(new Level(7, Level.MELON, 1, 0.87, 0.95, 0.5, 50, 1, 25, 1.05, 1, 0.87, 0.6, 2000));
		//8
		levels.add(new Level(8, Level.MELON, 1, 0.87, 0.95, 0.5, 50, 1, 25, 1.05, 1, 0.87, 0.6, 2000));
		//9
		levels.add(new Level(9, Level.GALAXIAN, 1, 0.87, 0.95, 0.5, 60, 1, 30, 1.05, 1, 0.87, 0.6, 1000));
		//10
		levels.add(new Level(10, Level.GALAXIAN, 1, 0.87, 0.95, 0.5, 60, 1, 30, 1.05, 1, 0.87, 0.6, 5000));
		//11
		levels.add(new Level(11, Level.BELL, 1, 0.87, 0.95, 0.5, 60, 1, 30, 1.05, 1, 0.87, 0.6, 2000));
		//12
		levels.add(new Level(12, Level.BELL, 1, 0.87, 0.95, 0.5, 80, 1, 40, 1.05, 1, 0.87, 0.6, 1000));
		//13 TODO de aqui hasta el 18 son variantes de lo mismo, del 19 en adelante son los mismos
		levels.add(new Level(12, Level.KEYS, 1, 0.87, 0.95, 0.5, 80, 1, 40, 1.05, 1, 0.87, 0.6, 1000));
	}

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

	public void setGameToInitialState() throws IOException {
		initGraph();
		initLevels();
		for(Coordinate coor : coordinates) {
			food.put(coor, new Food(Food.PACDOT, true));
			getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()+1);
		}
		food.put(coordinates.get(1), new Food(Food.ENERGIZER, true));
		food.put(coordinates.get(88), new Food(Food.ENERGIZER, true));
		food.put(coordinates.get(6), new Food(Food.ENERGIZER, true));
		food.put(coordinates.get(93), new Food(Food.ENERGIZER, true));

		//no food around the ghosts' house
		food.get(coordinates.get(31)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(32)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(33)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(42)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(43)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(52)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(53)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(61)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(62)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(63)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(98)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(99)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(100)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(101)).setType(Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);

		//no food in the tunnel
		food.get(coordinates.get(4)).setType(Food.NOTHING);		getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(12)).setType(Food.NOTHING);		getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(82)).setType(Food.NOTHING);		getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(91)).setType(Food.NOTHING);		getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(96)).setType(Food.NOTHING);		getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.get(coordinates.get(97)).setType(Food.NOTHING);		getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);

		Ghost.state = State.SCATTERED;
		initCharacters();
		bonusTile = new Coordinate(pacman.getPosX(), coordinates.get(43).getY(), false, false, false, false);
		food.put(bonusTile, new Food(Food.BONUS, true)); //TODO debe iniciar false y cambiar en algun momento del juego para que pacman lo coma

		searchGhostTarget(blinky);
		searchGhostTarget(inky);
		searchGhostTarget(pinky);
		searchGhostTarget(clyde);
	}

	public void movePacman() {
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
				score += POINTS_PER_ENERGIZER;
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						blinky.setFrightened(false);
						inky.setFrightened(false);
						pinky.setFrightened(false);
						clyde.setFrightened(false);
					}
				};
				Timer timer = new Timer("Timer");
				long delay = getCurrentLevel().getFrightTime();
				timer.schedule(task, delay);
				break;
			case Food.PACDOT:
				getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
				score += POINTS_PER_DOT;
				break;
			default:
				//c:
				break;
			}

			food.get(pacman.getPosition()).setType(Food.NOTHING); //pacman ate
		} else if(pacman.getPosX() == bonusTile.getX() && pacman.getPosY() == bonusTile.getY() && food.get(bonusTile).getNotEaten().get()) {
			score += getCurrentLevel().getBonusScore();
			food.get(bonusTile).setType(Food.NOTHING);
			food.get(bonusTile).setNotEaten(false);
		}
	}

	public void searchBlinkyTarget() {
		switch(Ghost.state) {
		case CHASE:
			blinky.setTarget(pacman.getPosition());
			break;
		case SCATTERED:
			if(!blinky.getTarget().equals(coordinates.get(68))) {
				blinky.setTarget(coordinates.get(68));
			} else {
				blinky.setTarget(coordinates.get(89));
			}
		}
	}

	public void searchInkyTarget() {
		switch(Ghost.state) {
		case CHASE:
			break;
		case SCATTERED:
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

	public void searchPinkyTarget() {
		switch(Ghost.state) {
		case CHASE:
			break;
		case SCATTERED:
			if(!pinky.getTarget().equals(coordinates.get(17))) {
				pinky.setTarget(coordinates.get(17));
			} else {
				pinky.setTarget(coordinates.get(2));
			}
			break;
		}
	}

	public void searchClydeTarget() {
		switch(Ghost.state) {
		case CHASE:
			break;
		case SCATTERED:
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

	public void moveGhost(Ghost ghost) {
		int difX = (int)Math.abs(ghost.getPosX() - pacman.getPosX());
		int difY = (int)Math.abs(ghost.getPosY() - pacman.getPosY());

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(inky.isFrightened());
		System.out.println(pinky.isFrightened());
		System.out.println(blinky.isFrightened());
		System.out.println(clyde.isFrightened());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
		if(difX <= 2 && difY <= 2) {
			if(ghost.isFrightened()) {
				//TODO ghost dies, aumentar puntaje cuando sepa cuanto vale cada fantasma comido
				//FIXME arreglar
				//TODO si se quita el setposx y y se dania pero se esta viendo como un cambio brusco en la interfaz
				ghost.setFrightened(false);
				ghost.setGoingHome(true);
				searchGhostTarget(ghost);
				System.out.println("muere fantasma muere >:v!!!");
			} else {
				//TODO pacman dies
				System.out.println("muere pacman muere >:v!!!");
			}
		}
		if(!ghost.getPath().isEmpty()) {
			Coordinate next = ghost.getPath().get(0);
			if(ghost.getPosX() == next.getX() && ghost.getPosY() == next.getY()) {
				ghost.setPosition(ghost.getPath().remove(0));
				determineDirection(ghost);
			}
		} else {
			ghost.setPosX(ghost.getPosition().getX());
			ghost.setPosY(ghost.getPosition().getY());
			searchGhostTarget(ghost);
		}
		switch(ghost.getDirection()) {
		case DOWN:
			ghost.setPosY(ghost.getPosY()+1);
			break;
		case LEFT:
			ghost.setPosX(ghost.getPosX()-1);
			break;
		case RIGHT:
			ghost.setPosX(ghost.getPosX()+1);
			break;
		case UP:
			ghost.setPosY(ghost.getPosY()-1);
			break;
		}
	}

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

	private void determineDirection(Ghost ghost) {
		if(!ghost.getPath().isEmpty()) {
			Coordinate current = ghost.getPosition();
			Coordinate next = ghost.getPath().get(0);
			if(next.getX() > current.getX()) {
				ghost.setDirection(Direction.RIGHT);
			} else if(next.getX() < current.getX()) {
				ghost.setDirection(Direction.LEFT);
			} else if(next.getY() > current.getY()) {
				ghost.setDirection(Direction.DOWN);
			} else {
				ghost.setDirection(Direction.UP);
			} 
		}
	}

	public boolean isEatingDots() {
		byte type = food.get(pacman.getPosition()).getType();
		return type == Food.PACDOT || type == Food.ENERGIZER;
	}

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

	public IGraph<Coordinate> getMap() {
		return map;
	}

	public void setMap(IGraph<Coordinate> map) {
		this.map = map;
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}

	public Level getCurrentLevel() {
		return levels.get(currentLevel);
	}

	public Ghost getInky() {
		return inky;
	}

	public Ghost getPinky() {
		return pinky;
	}

	public Ghost getBlinky() {
		return blinky;
	}

	public Ghost getClyde() {
		return clyde;
	}

	public Pacman getPacman() {
		return pacman;
	}

	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}

	public HashMap<Coordinate, Food> getFood() {
		return food;
	}

	public Coordinate getLeftTileOfTheTunel() {
		return leftTileOfTheTunel;
	}

	public Coordinate getRightTileOfTheTunel() {
		return rightTileOfTheTunel;
	}

	public Coordinate getBonusTile() {
		return bonusTile;
	}

	public int getScore() {
		return score;
	}

	public boolean atLeastAFrightenedGhost() {
		return blinky.isFrightened() || inky.isFrightened() || pinky.isFrightened() || clyde.isFrightened();
	}

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
}
