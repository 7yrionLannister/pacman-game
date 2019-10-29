package model;

import java.util.ArrayList;

public class Ghost {
	private boolean frightened;
	private Coordinate position;
	private Coordinate target;
	private ArrayList<Coordinate> path;
	
	public Ghost(Coordinate position) {
		this.position = position;
	}
	
	public boolean isFrightened() {
		return frightened;
	}
	
	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
	}
	
	public Coordinate getPosition() {
		return position;
	}
	
	public void setPosition(Coordinate position) {
		this.position = position;
	}
	
	public Coordinate getTarget() {
		return target;
	}
	
	public void setTarget(Coordinate target) {
		this.target = target;
	}
	
	public ArrayList<Coordinate> getPath() {
		return path;
	}
	
	public void setPath(ArrayList<Coordinate> path) {
		this.path = path;
	}
}
