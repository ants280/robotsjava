/**
 * A Robot.  A Robot moves toward the player on each player move.  A robot moves by increasing/increasing it's row and column by 1 to be on the same position as the player on the board.
 */
public class Robot extends Location
{
	/**
	 * For telling the Player that it is an enemy.
	 * 
	 * @return True.
	 */
	public boolean isEnemy() { return true; }

	/**
	 * Prints ouf the Player and it's row and column.
	 * 
	 * @deprecated Use for debugging porpuses only.
	 */
	public char value() { return '1'; }

	/**
	 * Constructor for a Robot.
	 *
	 * @param row The row of the new Wreck.
	 * @param col The column of the new Wreck.
	 */
	public Robot(int row, int col) { super(row, col); }
}
