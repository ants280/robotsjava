package com.ants280.robots.pieces;

/**
 * A Wreck, or junk-heap that is created when robots collide.  A robot dies if it runs into a Wreck.
 */
public class Wreck extends Location
{
	/**
	 * Determines if the Wreck just did some splat!'n.  A "splat"" happens when the player pushes the Wreck onto a Robot.
	 */
	private boolean splat;

	/**
	 * Used to tell if the Wreck just splat!'d a Robot
	 *
	 * @return True if the Wreck just splat!'d a Robot. Otherwise, False.
	 */
	public boolean justSplat()
	{
		return splat;
	}

	/**
	 * Used to trigger the Wreck's splat! property.  Either sets or unsets it.
	 */
	public void triggerSplat()
	{
		splat = !splat;
	}

	/**
	 * For telling the Player that it is an enemy.
	 * 
	 * @return True.
	 */
	public boolean isEnemy() { return true; }

	/**
	 * Prints out the Wreck's Textual Image.
	 * 
	 * @deprecated Use for debugging purposes only.
	 */
	public char value() { return '*'; }

	/**
	 * Constructor for a Wreck.
	 *
	 * @param row The row of the new Wreck.
	 * @param col The column of the new Wreck.
	 */
	public Wreck(int row, int col)
	{
		super(row, col);
	}

	/**
	 * Constructor for a Wreck.
	 *
	 * @param loc The Location to create the Wreck out of.
	 */
	public Wreck(Location loc)
	{
		this(loc.getRow(), loc.getCol());
	}
}
