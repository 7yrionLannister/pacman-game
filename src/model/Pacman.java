package model;

import dataStructures.IGraph;

public class Pacman {
	public static enum Direction {LEFT, RIGHT, UP, DOWN}

	private Coordinate position;
	private double posX;
	private double posY;
	private Direction direction;
	private Direction requestedDirection;
	private IGraph<Coordinate> map;

	public Pacman(Coordinate position, IGraph<Coordinate> map, double posX, double posY) {
		this.position = position;
		this.map = map;
		this.posX = posX;
		this.posY = posY;
		direction = Direction.LEFT;
	}

	public void moveForward() {
		System.out.println((Game.coordinates.indexOf(position)+1));
		switch(direction) {
		case DOWN:
			posY++;
			if(position.hasDownTile() || posY < position.getY()) {
				for(Coordinate neighbor : map.getAdjacent(position)) {
					if(neighbor.getX() == position.getX()) {
						if(neighbor.getY() > posY) {
							position = neighbor;
							break;
						}
					}
				}
			} else {
				posY--;
			}
			break;
		case LEFT:
			posX--;
			if(position.hasLeftTile() && posX == position.getX()-1) {
				for(Coordinate neighbor : map.getAdjacent(position)) {
					if(neighbor.getY() == position.getY() && neighbor.getX() < posX) {
						position = neighbor;
						break;
					}
				}
			} 
			if(!position.hasLeftTile() && posX < position.getX()) {
				posX++;System.out.println("ddd");
			}
			break;
		case RIGHT:
			posX++;
			if(position.hasRightTile() && posX == position.getX()+1) {
				for(Coordinate neighbor : map.getAdjacent(position)) {
					if(neighbor.getY() == position.getY() && neighbor.getX() > posX) {
						position = neighbor;
						break;
					}
				}
			} 
			if(!position.hasRightTile() && posX > position.getX()){
				posX--;
			}
			break;
		case UP:
			posY--;
			if(position.hasUpTile() || posY > position.getY()) {
				for(Coordinate neighbor : map.getAdjacent(position)) {
					if(neighbor.getX() == position.getX()) {
						if(neighbor.getY() < posY) {
							position = neighbor;
							break;
						}
					}
				}
			} else {
				posY++;
			}
			break;
		}
		if((posY == position.getY() && (requestedDirection == Direction.RIGHT || requestedDirection == Direction.LEFT)) || (posX == position.getX() && (requestedDirection == Direction.UP || requestedDirection == Direction.DOWN))) {
			direction = requestedDirection;
		}
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		requestedDirection = direction;
	}
}
