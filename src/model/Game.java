package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dataStructures.AdjacencyListGraph;
import dataStructures.AdjacencyMatrixGraph;
import dataStructures.IGraph;


public class Game {
	public final static String GRAPH_RESOURCE = "resources/map.txt";
	private IGraph<Coordinate> map;
	private ArrayList<Level> levels;
	private int currentLevel;
	
	private Pacman pacman;
	
	private Ghost inky;
	private Ghost pinky;
	private Ghost blinky;
	private Ghost clyde;
	
	public Game() throws IOException {
		initGraph();
		initLevels();
		initCharacters();
	}

	private void initCharacters() {
		pacman = new Pacman();
		inky = new Inky(new Coordinate(153,156,false,false,false,false));
		pinky = new Pinky(new Coordinate(153,156,false,false,false,false));
		blinky = new Blinky(new Coordinate(153,156,false,false,false,false));
		clyde = new Clyde(new Coordinate(153,156,false,false,false,false));
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
		
		map = new AdjacencyListGraph<>(true);
		//map = new AdjacencyMatrixGraph<>(96, true);
		ArrayList<Coordinate> coordinates = new ArrayList<>();
		
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

	public int getCurrentLevel() {
		return currentLevel;
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
}
