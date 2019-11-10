package model;

import java.util.TimerTask;

import model.Ghost.State;

public class Sequence {
	
	/**
	 */
	private int toChase1;
	/**
	 */
	private int toScatter1;
	/**
	 */
	private int toChase2;
	/**
	 */
	private int toScatter2;
	/**
	 */
	private int toChase3;
	/**
	 */
	private int toScatter3;
	/**
	 */
	private int toChaseForEver;
	
	/**
	 * @param toChase1
	 * @param toScatter1
	 * @param toChase2
	 * @param toScatter2
	 * @param toChase3
	 * @param toScatter3
	 * @param toChaseForEver
	 */
	public Sequence(int toChase1, int toScatter1, int toChase2, int toScatter2, int toChase3, int toScatter3,
			int toChaseForEver) {
		super();
		this.toChase1 = toChase1;
		this.toScatter1 = toScatter1;
		this.toChase2 = toChase2;
		this.toScatter2 = toScatter2;
		this.toChase3 = toChase3;
		this.toScatter3 = toScatter3;
		this.toChaseForEver = toChaseForEver;
	}
	/**
	 * @return
	 */
	public int getToChase1() {
		return toChase1;
	}

	public void setToChase1(int toChase1) {
		this.toChase1 = toChase1;
	}
	/**
	 * @return
	 */
	public int getToScatter1() {
		return getToChase1() + toScatter1;
	}

	public void setToScatter1(int toScatter1) {
		this.toScatter1 = toScatter1 + 5000; //4000 is the delay in the controller
	}
	/**
	 * @return
	 */
	public int getToChase2() {
		return getToScatter1() + toChase2;
	}

	public void setToChase2(int toChase2) {
		this.toChase2 = toChase2;
	}
	/**
	 * @return
	 */
	public int getToScatter2() {
		return getToChase2() + toScatter2;
	}

	public void setToScatter2(int toScatter2) {
		this.toScatter2 = toScatter2;
	}
	/**
	 * @return
	 */
	public int getToChase3() {
		return getToScatter2() + toChase3;
	}

	public void setToChase3(int toChase3) {
		this.toChase3 = toChase3;
	}
	/**
	 * @return
	 */
	public int getToScatter3() {
		return getToChase3() + toScatter3;
	}

	public void setToScatter3(int toScatter3) {
		this.toScatter3 = toScatter3;
	}
	/**
	 * @return
	 */
	public int getToChaseForEver() {
		return getToScatter3() + toChaseForEver;
	}

	public void setToChaseForEver(int toChaseForEver) {
		this.toChaseForEver = toChaseForEver;
	}
	/**
	 * @return
	 */
	public TimerTask getToChaseTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				Ghost.state = State.CHASE;
			}
		};
	}
	/**
	 * @return
	 */
	public TimerTask getToScatterTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				Ghost.state = State.SCATTER;
			}
		};
	}
}
