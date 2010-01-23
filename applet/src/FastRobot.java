/**
 * A fast robot.  Fast robots move like a robot that moves two times each turn.
 */
public class FastRobot extends Robot
{

	/**
	 * Prints out the Player and it's row and column.
	 * 
	 * @deprecated Use for debugging purposes only.
	 */
	public char value() { return '0'; }

	/**
	 * Constructor for a FastRobot.
	 *
	 * @param row The row of the new Wreck.
	 * @param col The column of the new Wreck.
	 */
	public FastRobot(int row, int col) { super(row, col); }
}
