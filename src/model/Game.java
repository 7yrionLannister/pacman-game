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
import model.Pacman.Direction;


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

	private Pacman pacman;

	private Ghost inky;
	private Ghost pinky;
	private Ghost blinky;
	private Ghost clyde;

	public Game() throws IOException {
		initGraph();
		setGameToInitialState();
		initLevels();
		currentLevel = 0;
		leftTileOfTheTunel = coordinates.get(96);
		rightTileOfTheTunel = coordinates.get(97);
	}

	private void initCharacters() {
		Coordinate pacmanC = coordinates.get(45);
		pacman = new Pacman(pacmanC, pacmanC.getX()+20, pacmanC.getY());
		inky = new Ghost(new Coordinate(153,156,false,false,false,false));
		pinky = new Ghost(new Coordinate(153,156,false,false,false,false));
		blinky = new Ghost(new Coordinate(153,156,false,false,false,false));
		clyde = new Ghost(new Coordinate(153,156,false,false,false,false));
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
			Coordinate toAdd = new Coordinate(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]), Boolean.parseBoolean(coord[2]), Boolean.parseBoolean(coord[3]), Boolean.parseBoolean(coord[4]), Boolean.parseBoolean(coord[5]));
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

	public void setGameToInitialState() {
		for(Coordinate coor : coordinates) {
			food.put(coor, Food.PACDOT);
		}
		food.put(coordinates.get(2), Food.ENERGIZER);
		food.put(coordinates.get(89), Food.ENERGIZER);
		food.put(coordinates.get(7), Food.ENERGIZER);
		food.put(coordinates.get(94), Food.ENERGIZER);
		
		//no food around the ghosts' house
		food.put(coordinates.get(33), Food.NOTHING);
		food.put(coordinates.get(34), Food.NOTHING);
		food.put(coordinates.get(35), Food.NOTHING);
		food.put(coordinates.get(43), Food.NOTHING);
		food.put(coordinates.get(44), Food.NOTHING);
		food.put(coordinates.get(53), Food.NOTHING);
		food.put(coordinates.get(54), Food.NOTHING);
		food.put(coordinates.get(62), Food.NOTHING);
		food.put(coordinates.get(63), Food.NOTHING);
		food.put(coordinates.get(64), Food.NOTHING);
		
		//no food in the tunnel
		food.put(coordinates.get(4), Food.NOTHING);
		food.put(coordinates.get(12), Food.NOTHING);
		food.put(coordinates.get(82), Food.NOTHING);
		food.put(coordinates.get(91), Food.NOTHING);

		initCharacters();
	}

	public void movePacman() {
		//System.out.println((Game.coordinates.indexOf(position)+1));
		food.put(pacman.getPosition(), Food.NOTHING); //pacman eats
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
				adj.sort(new Comparator<Coordinate>() {
					@Override
					public int compare(Coordinate o1, Coordinate o2) {
						return Double.compare(o1.getX(), o2.getX());
					}
				});
				Coordinate pos = adj.get(0);
				for(Coordinate neighbor : map.getAdjacent(pacman.getPosition())) {
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
				adj.sort(new Comparator<Coordinate>() {
					@Override
					public int compare(Coordinate o1, Coordinate o2) {
						return Double.compare(o1.getX(), o2.getX());
					}
				});
				Coordinate pos = adj.get(0);
				for(Coordinate neighbor : map.getAdjacent(pacman.getPosition())) {
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
		}
	}

	public void moveBlinky() {
		
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
}
