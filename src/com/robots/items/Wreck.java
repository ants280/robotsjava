package com.robots.items;

/**
 * A Wreck, or junk-heap that is created when robots collide.  A robot dies if it runs into a Wreck.
 */
public class Wreck extends Location
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
	public char value() { return '*'; }

	/**
	 * Constructor for a Wreck.
	 *
	 * @param row The row of the new Wreck.
	 * @param col The column of the new Wreck.
	 */
	public Wreck(int row, int col) { super(row, col); }
}
