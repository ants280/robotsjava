/**
 * A default spot in the grid.  All objects on the grid extend Location.
 */
public class Location
{
	/**
	 * Tells whether the Location is poisonous to the Player.
	 *
	 * @return True for is poisonous, otherwise, false.
	 */
	public boolean isEnemy() { return false; }

	/**
	 * Returns the value returned of the Location for printing.
	 *
	 * @deprecated   Printing is now graphical.
	 */
	public char value() { return ' '; }

	/**
	 * The row of the Location.
	 */
	protected int m_row;
	
	/**
	 * The column of the Location.
	 */
	protected int m_col;

	/**
	 * @return The Location's row.
	 */
	public int getRow() { return m_row; }
	
	/**
	 * @return The Location's column.
	 */
	public int getCol() { return m_col; }

	/**
	 * Prints out the Location and it's row and column.
	 * 
	 * @deprecated Use for debugging purposes only.
	 */
	public String toString() { return this.getClass().getName() + " at " + m_row + ", " + m_col; }
	
	/**
	 * Updates the row and column of the Location in the specified Direction.
	 *
	 * @param dir The Direction to update the Location's row and column.
	 * @return The location with its row and column fields updated
	 */
	public Location updatePos(Direction dir)
	{
		switch(dir)
		{
		
			case SW : m_row++; m_col--;	break;
			case S : m_row++;			break;
			case SE : m_row++; m_col++;	break;
			case W : m_col--;			break;
			case SAME: case CONTINUOUS:	break;
			case E : m_col++;			break;
			case NW : m_row--; m_col--;	break;
			case N : m_row--;			break;
			case NE : m_row--; m_col++;	break;
			case RANDOM : 
				if(this instanceof Player)
				{
					((Player)this).teleport();
				}
				break;
			default : break; // PLUS
		}
		return this;
	}

	/**
	 * Constructor for a Location.
	 *
	 * @param row The row of the new Location.
	 * @param col The column of the new Location.
	 */
	public Location(int row, int col) { m_row = row; m_col = col; }

	/**
	 * Copy constructor. Creates a new Location equal to the one specified.
	 *
	 * @param loc The Location to create the new one out of.
	 */
	public Location(Location loc) { m_row = loc.getRow(); m_col = loc.getCol(); }
}
