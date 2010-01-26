package Pieces;

import java.util.Random;

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
	 * Prints out the Player and it's row and column.
	 * 
	 * @deprecated Use for debugging purposes only.
	 */
	public char value() { return m_dead ? 'X' : '#'; }
	
	private final int ROWS;

	private final int COLS;
	
	private Random generator;
	
	private boolean m_dead;

	/**
	 * Instructs the player to die. Usually ends the game.
	 */	
	public void die() { m_dead = true; }
	
	/**
	 * Tells if the Player is alive.
	 *
	 * @return True if the player is alive, otherwise false.
	 */
	public boolean isAlive() { return !m_dead; }
 	
	/**
	 * Returns the Players random number generator.
	 */
	public Random getGenerator() { return generator; }

	/**
	 * Returns the board width.
	 */
	public int getWidth() { return ROWS; }

	/**
	 * Returns the board height.
	 */
	public int getHeight() { return COLS; }

	/**
	 * Changes the Player's row and column to less than (0-inclusive) the specified value from construction.
	 */
	public void teleport()
	{
		int row, col;
		row = generator.nextInt(ROWS);
		col = generator.nextInt(COLS);

		this.changePositionTo(row, col);
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
	 * @param gen The random number generator to use to teleport the player.
	 * @param rows The number of rows on the playing grid.
	 * @param cols The number of columns on the playing grid.
	 */
	public Player(int row, int col, Random gen, final int rows, final int cols)
	{
		super(row, col);
		
		generator = gen;
		ROWS = rows;
		COLS = cols;
		m_dead = false;
	}

	/**
	 * Creates a Player out of another Player.  For testing to see if move is Valid or the Player should die.
	 *
	 * @param other The Player to make the copy off of.
	 */
	public Player(Player other)
	{
		super(other.getRow(), other.getCol());
		
		this.generator = other.getGenerator();
		this.ROWS = other.getWidth();
		this.COLS = other.getHeight();
		
		m_dead = false;
	}
}
