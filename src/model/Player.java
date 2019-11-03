package model;

import java.io.Serializable;

public class Player implements Serializable {
	
	/**
	 */
	private String rank;
	/**
	 */
	private int score;
	/**
	 */
	private int stage;
	/**
	 */
	private String name;
	
	/**
	 * @param rank
	 * @param score
	 * @param stage
	 * @param name
	 */
	public Player(String rank, int score, int stage, String name) {
		this.rank = rank;
		this.score = score;
		this.stage = stage;
		this.name = name;
	}
	/** 
	 * @return
	 */
	public String getRank() {
		return rank;
	}
	/**
	 * @param rank
	 */
	public void setRank(String rank) {
		this.rank = rank;
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
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
