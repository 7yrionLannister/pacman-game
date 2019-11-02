package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import dataStructures.AdjacencyListGraph;
import dataStructures.AdjacencyMatrixGraph;
import dataStructures.IGraph;
import model.Ghost.State;


public class Game {
	public final static String GRAPH_RESOURCE = "resources/map.txt";
	public static enum Food {
		NOTHING, PACDOT, ENERGIZER, BONUS;
	}
	private  Coordinate leftTileOfTheTunel;

	private  Coordinate rightTileOfTheTunel;

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
		//TODO esta linea esta siendo usada para probar que blinky siga una ruta
		//TODO tenerlo en cuenta y eliminar todo lo asociado a esta linea al termianr la prueba
		searchTargets();
	}

	private void initCharacters() {
		Coordinate tile = coordinates.get(45);
		double xCoord = tile.getX()+20; //TODO modificado para que se vea bien en mac asi que si da problemas en linux revisar bien
		if(!runningLinux) {
			xCoord -= 5;
		}
		pacman = new Pacman(tile, xCoord, tile.getY());
		tile = coordinates.get(42);
		blinky = new Ghost(tile, "blinky", xCoord, tile.getY());
		blinky.setDirection(Direction.LEFT);
		blinky.setTarget(coordinates.get(68));
		tile = new Coordinate(xCoord, tile.getY()+27,false,false,false,false);
		pinky = new Ghost(tile, "pinky", tile.getX(), tile.getY()+5);
		pinky.setDirection(Direction.UP);
		pinky.setTarget(coordinates.get(17));
		tile = new Coordinate(xCoord-20, tile.getY(),false,false,false,false);
		inky = new Ghost(tile, "inky", tile.getX(), tile.getY());
		inky.setDirection(Direction.DOWN);
		inky.setTarget(coordinates.get(77));
		tile = new Coordinate(xCoord+20, tile.getY(),false,false,false,false);
		clyde = new Ghost(tile, "clyde", tile.getX(), tile.getY());
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
		//map = new AdjacencyMatrixGraph<>(96, true);
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
			food.put(coor, Food.PACDOT); 
			getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()+1);
		}
		food.put(coordinates.get(1), Food.ENERGIZER);
		food.put(coordinates.get(88), Food.ENERGIZER);
		food.put(coordinates.get(6), Food.ENERGIZER);
		food.put(coordinates.get(93), Food.ENERGIZER);

		//no food around the ghosts' house
		food.put(coordinates.get(33), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(34), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(35), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(43), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(44), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(53), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(54), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(62), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(63), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(64), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);

		//no food in the tunnel
		food.put(coordinates.get(4), Food.NOTHING);		getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(12), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(82), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(91), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(96), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
		food.put(coordinates.get(97), Food.NOTHING);	getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);

		Ghost.state = State.SCATTERED;
		initCharacters();
	}

	public void movePacman() {
		switch(food.get(pacman.getPosition())) {
		case BONUS:
			score += getCurrentLevel().getBonusScore();
			break;
		case ENERGIZER:
			//TODO poner timer y timertask que le den la cantidad de tiempo que pacman tendra para comer a los fantasmas
			//TODO que se ejecute solo una vez, algo asi como para contar el tiempo y luego todo vuelve al estado anterior
			getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
			break;
		case PACDOT:
			getCurrentLevel().setDotsLeft(getCurrentLevel().getDotsLeft()-1);
			break;
		default:
			//c:
			break;
		}

		food.put(pacman.getPosition(), Food.NOTHING); //pacman ate

		moveCharacter(pacman);
		
		if(pacman.getPosY() == pacman.getPosition().getY() && pacman.getPosX() == pacman.getPosition().getX()) {
			if(((pacman.getPosition().hasRightTile() && pacman.getRequestedDirection() == Direction.RIGHT) || (pacman.getPosition().hasLeftTile() && pacman.getRequestedDirection() == Direction.LEFT)) || ((pacman.getPosition().hasUpTile() && pacman.getRequestedDirection() == Direction.UP) || (pacman.getPosition().hasDownTile() && pacman.getRequestedDirection() == Direction.DOWN))) {
				pacman.setDirection(pacman.getRequestedDirection());
			}
		}
	}

	private void moveCharacter(Character character) {
		switch(character.getDirection()) {
		case DOWN:
			character.setPosY(character.getPosY()+1);
			if(character.getPosition().hasDownTile() && character.getPosY() == character.getPosition().getY()+1) {
				for(Coordinate neighbor : map.getAdjacent(character.getPosition())) {
					if(neighbor.getX() == character.getPosition().getX() && neighbor.getY() > character.getPosY()) {
						character.setPosition(neighbor);
						break;
					}
				}
			} 
			if(!character.getPosition().hasDownTile() && character.getPosY() > character.getPosition().getY()){
				character.setPosY(character.getPosY()-1);
			}
			break;
		case LEFT:
			character.setPosX(character.getPosX()-1);
			if(!character.getPosition().equals(rightTileOfTheTunel)) {
				if(character.getPosition().hasLeftTile() && character.getPosX() == character.getPosition().getX()-1) {
					if(character.getPosition().equals(leftTileOfTheTunel)) {
						character.setPosition(rightTileOfTheTunel);
						character.setPosX(character.getPosition().getX());
						character.setPosY(character.getPosition().getY());
					} else {
						for(Coordinate neighbor : map.getAdjacent(character.getPosition())) {
							if(neighbor.getY() == character.getPosition().getY() && neighbor.getX() < character.getPosX()) {
								character.setPosition(neighbor);
								break;
							}
						}
					}
				}
			} else if(character.getPosX() < character.getPosition().getX()) {
				ArrayList<Coordinate> adj = map.getAdjacent(character.getPosition());
				adj.sort(new Comparator<Coordinate>() {
					@Override
					public int compare(Coordinate o1, Coordinate o2) {
						return Double.compare(o1.getX(), o2.getX());
					}
				});
				Coordinate pos = adj.get(0);
				for(Coordinate neighbor : map.getAdjacent(character.getPosition())) {
					if(neighbor.getX() > pos.getX()) { 
						pos = neighbor;
					}
				}
				character.setPosition(pos);
			}

			if(!character.getPosition().hasLeftTile() && character.getPosX() < character.getPosition().getX()) {
				character.setPosX(character.getPosX()+1);
			}
			break;
		case RIGHT:
			character.setPosX(character.getPosX()+1);
			if(!character.getPosition().equals(leftTileOfTheTunel)) {
				if(character.getPosition().hasRightTile() && character.getPosX() == character.getPosition().getX()+1) {
					if(character.getPosition().equals(rightTileOfTheTunel)) {
						character.setPosition(leftTileOfTheTunel);
						character.setPosX(character.getPosition().getX());
						character.setPosY(character.getPosition().getY());
					} else {
						for(Coordinate neighbor : map.getAdjacent(character.getPosition())) {
							if(neighbor.getY() == character.getPosition().getY() && neighbor.getX() > character.getPosX()) {
								character.setPosition(neighbor);
								break;
							}
						}
					}
				}
			} else if(character.getPosX() > character.getPosition().getX()) {
				ArrayList<Coordinate> adj = map.getAdjacent(character.getPosition());
				adj.sort(new Comparator<Coordinate>() {
					@Override
					public int compare(Coordinate o1, Coordinate o2) {
						return Double.compare(o1.getX(), o2.getX());
					}
				});
				Coordinate pos = adj.get(0);
				for(Coordinate neighbor : map.getAdjacent(character.getPosition())) {
					if(neighbor.getX() < pos.getX()) { 
						pos = neighbor;
					}
				}
				character.setPosition(pos);
			}

			if(!character.getPosition().hasRightTile() && character.getPosX() > character.getPosition().getX()) {
				character.setPosX(character.getPosX()-1);
			}
			break;
		case UP:
			character.setPosY(character.getPosY()-1);
			if(character.getPosition().hasUpTile() && character.getPosY() == character.getPosition().getY()-1) {
				for(Coordinate neighbor : map.getAdjacent(character.getPosition())) {
					if(neighbor.getX() == character.getPosition().getX() && neighbor.getY() < character.getPosY()) {
						character.setPosition(neighbor);
						break;
					}
				}
			} 
			if(!character.getPosition().hasUpTile() && character.getPosY() < character.getPosition().getY()) {
				character.setPosY(character.getPosY()+1);
			}
			break;
		}
	}

	public void searchTargets() {
		//TODO implementar
		switch(Ghost.state) {
		case CHASE:
			blinky.setTarget(pacman.getPosition());
			break;
		case SCATTERED:
			//BLINKY
			if(!blinky.getTarget().equals(coordinates.get(68))) {
				blinky.setTarget(coordinates.get(68));
			} else {
				blinky.setTarget(coordinates.get(89));
			}
			blinky.setPath(map.getPath(blinky.getPosition(), blinky.getTarget()));
			//PINKY
			if(!pinky.getTarget().equals(coordinates.get(17))) {
				pinky.setTarget(coordinates.get(17));
			} else {
				pinky.setTarget(coordinates.get(2));
			}
			if(map.containsVertex(pinky.getPosition())) {
				pinky.setPath(map.getPath(pinky.getPosition(), pinky.getTarget()));
			}
			//INKY
			if(inky.getTarget().equals(coordinates.get(56))) {
				inky.setTarget(coordinates.get(77));
			} else if(inky.getTarget().equals(coordinates.get(77))) {
				inky.setTarget(coordinates.get(86));
			} else {
				inky.setTarget(coordinates.get(56));
			}
			if(map.containsVertex(inky.getPosition())) {
				inky.setPath(map.getPath(inky.getPosition(), inky.getTarget()));
			}
			//CLYDE
			if(clyde.getTarget().equals(coordinates.get(46))) {
				clyde.setTarget(coordinates.get(26));
			} else if(inky.getTarget().equals(coordinates.get(26))) {
				clyde.setTarget(coordinates.get(16));
			} else {
				clyde.setTarget(coordinates.get(46));
			}
			if(map.containsVertex(clyde.getPosition())) {
				clyde.setPath(map.getPath(clyde.getPosition(), clyde.getTarget()));
			}
			break;
		}
		//Choose a random target for any frightened ghost
		if(blinky.isFrightened()) {
			blinky.setTarget(coordinates.get((int)(Math.random()*coordinates.size())));
		}
		if(inky.isFrightened()) {
			inky.setTarget(coordinates.get((int)(Math.random()*coordinates.size())));
		}
		if(clyde.isFrightened()) {
			clyde.setTarget(coordinates.get((int)(Math.random()*coordinates.size())));
		}
		if(pinky.isFrightened()) {
			pinky.setTarget(coordinates.get((int)(Math.random()*coordinates.size())));
		}
	}

	public void moveBlinky() {
		moveCharacter(blinky);
		//FIXME no es que esta mal, es que no se ha implementado, debes cambiar la direccion si la siguiente casilla en el path da una vuelta
		//
		//TODO el siguiente if es una de las pruebas
		if(!blinky.getPath().isEmpty()) {
			Coordinate next = blinky.getPath().remove(0); 
			blinky.setPosX(next.getX());
			blinky.setPosY(next.getY());
		}
	}

	public boolean isEatingDots() {
		return !food.get(pacman.getPosition()).equals(Food.NOTHING);
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

	public int getScore() {
		return score;
	}
}
