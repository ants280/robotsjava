package Com.Ants280.Robots.Pieces;

/**
 * The game piece the human moves.  A Player is the only piece the human moves to "trick the robots into colliding with themselves/running into a wreck.
 */
public class Player extends Location
{
	/**
	 * The Player is not an enemy
	 * @return False.
	 */
	public boolean isEnemy() { return false; }
	
	/**
	 * Prints out the Player's textual image.  The image varies depending on if the player is alive or not.
	 * 
	 * @deprecated Use for debugging purposes only.
	 */
	public char value()
	{
		return alive ? '#' : 'X';
	}
	
	/**
	 * Tells if the player is alive or not.
	 */
	private boolean alive;

	/**
	 * Instructs the player to die. Usually ends the game.
	 */	
	public void die()
	{
		alive = false;
	}
	
	/**
	 * Tells if the Player is alive.
	 *
	 * @return True if the player is alive, otherwise false.
	 */
	public boolean isAlive()
	{
		return alive;
	}
 	
	/**
	 * Changes the Player's Location.
	 *
	 * @param row The row to position the player at.
	 * @param col The column to position the player at.
	 */
	public void changePositionTo(int row, int col)
	{
		m_row = row;
		m_col = col;
	}

	/**
	 * Constructs a Player.
	 *
	 * @param row The row to set as the Player's row.
	 * @param col The column to set as the Player's column.
	 */
	public Player(int row, int col)
	{
		super(row, col);
		
		alive = true;
	}

	/**
	 * Creates a Player out of another Player.  For testing to see if move is Valid or the Player should die.
	 *
	 * @param other The Player to make the copy off of.
	 */
	public Player(Player other)
	{
		super(other.getRow(), other.getCol());

		alive = true;
	}
}
