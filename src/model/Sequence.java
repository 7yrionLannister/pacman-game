package model;

import java.util.TimerTask;

import model.Ghost.State;

public class Sequence {
	
	/**It represents the number of times of the first chase.
	 */
	private int toChase1;
	/**It represents the number of times of the first scatter.
	 */
	private int toScatter1;
	/**It represents the number of times of the second chase.
	 */
	private int toChase2;
	/**It represents the number of times of the second scatter.
	 */
	private int toScatter2;
	/**It represents the number of times of the third chase.
	 */
	private int toChase3;
	/**It represents the number of times of the third scatter.
	 */
	private int toScatter3;
	/**It represents the number of times of the next chases.
	 */
	private int toChaseForEver;
	
	/**Creates a sequence with different chases and scatters.
	 * @param toChase1 is an integer that represents the number of times of the first chase.
	 * @param toScatter1 is an integer that represents the number of times of the first scatter.
	 * @param toChase2 is an integer that represents the number of times of the second chase.
	 * @param toScatter2 is an integer that represents the number of times of the second scatter.
	 * @param toChase3 is an integer that represents the number of times of the third chase.
	 * @param toScatter3 is an integer that represents the number of times of the third scatter.
	 * @param toChaseForEver is an integer that represents the number of times of the next chases.
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
	/**Allows to obtain an integer that represents the number of times of the first chase.
	 * @return an integer that represents the number of times of the first chase.
	 */ 
	public int getToChase1() {
		return toChase1;
	}
	/**Allows to set an integer that represents the number of times of the first chase.
	 * @param toChase1 is an integer that represents the number of times of the first chase.
	 */
	public void setToChase1(int toChase1) {
		this.toChase1 = toChase1;
	}
	/**Allows to obtain an integer that represents the number of times of the first scatter.
	 * @return an integer that represents the number of times of the first scatter.
	 */
	public int getToScatter1() {
		return getToChase1() + toScatter1;
	}
	/**Allows to set an integer that represents the number of times of the first scatter.
	 * @param toScatter1 is an integer that represents the number of times of the first scatter.
	 */
	public void setToScatter1(int toScatter1) {
		this.toScatter1 = toScatter1 + 5000; //4000 is the delay in the controller
	}
	/**Allows to obtain an integer that represents the number of times of the second chase.
	 * @return an integer that represents the number of times of the second chase.
	 */
	public int getToChase2() {
		return getToScatter1() + toChase2;
	}
	/**Allows to set an integer that represents the number of times of the second chase.
	 * @param toChase2 is an integer that represents the number of times of the second chase.
	 */
	public void setToChase2(int toChase2) {
		this.toChase2 = toChase2;
	}
	/**Allows to obtain an integer that represents the number of times of the second scatter.
	 * @return an integer that represents the number of times of the second scatter.
	 */
	public int getToScatter2() {
		return getToChase2() + toScatter2;
	}
	/**Allows to set an integer that represents the number of times of the second scatter.
	 * @param toScatter2 is an integer that represents the number of times of the second scatter.
	 */
	public void setToScatter2(int toScatter2) {
		this.toScatter2 = toScatter2;
	}
	/**Allows to obtain an integer that represents the number of times of the third chase.
	 * @return an integer that represents the number of times of the third chase.
	 */
	public int getToChase3() {
		return getToScatter2() + toChase3;
	}
	/**Allows to set an integer that represents the number of times of the third chase.
	 * @param toChase3 is an integer that represents the number of times of the third chase.
	 */
	public void setToChase3(int toChase3) {
		this.toChase3 = toChase3;
	}
	/**Allows to obtain an integer that represents the number of times of the third scatter.
	 * @return an integer that represents the number of times of the third scatter.
	 */
	public int getToScatter3() {
		return getToChase3() + toScatter3;
	}
	/**Allows to set an integer that represents the number of times of the third scatter.
	 * @param toScatter3 is an integer that represents the number of times of the third scatter.
	 */
	public void setToScatter3(int toScatter3) {
		this.toScatter3 = toScatter3;
	}
	/**Allows to obtain an integer that represents the number of times of the next chases.
	 * @return an integer that represents the number of times of the next chases.
	 */
	public int getToChaseForEver() {
		return getToScatter3() + toChaseForEver;
	}
	/**Allows to set an integer that represents the number of times of the next chases.
	 * @param toChaseForEver is an integer that represents the number of times of the next chases.
	 */
	public void setToChaseForEver(int toChaseForEver) {
		this.toChaseForEver = toChaseForEver;
	}
	/**Allows to obtain the chase timer task for the game.
	 * @return a timer task that represents the chase timer task for the game.
	 */
	public TimerTask getToChaseTimerTask() {
		return new TimerTask() {
			@Override
			public void run() {
				Ghost.state = State.CHASE;
			}
		};
	}
	/**Allows to obtain the scatter timer task for the game.
	 * @return a timer task that represents the scatter timer task for the game.
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
