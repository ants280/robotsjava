import javax.swing.JLabel;

/**
 * A game that allows safe teleports.
 */
public class SafeTeleportsGame extends Game
{
	private int safeTeleports;
	private JLabel safeTeleportsLabel;
	private boolean hasSafeTeleports;

	/**
	 * Method to tell if the game has safe teleports.
	 *
	 * @return True if the game has safe teleports.  Otherwise, false.
	 */
	public boolean isSafeTeleportsGame() { return hasSafeTeleports; }
	/**
	 * Creates a new SafeTeleportsGame.
	 *
	 * @param hasSafeTeleports Whether or not the game has safe teleports.
	 */
	public SafeTeleportsGame(boolean hasSafeTeleports)
	{
		super();
		this.hasSafeTeleports = hasSafeTeleports;
		if(hasSafeTeleports)
		{
			safeTeleportsLabel = new JLabel("Safe Teleports: 0");
		}
	}

	/**
 	 * Same as Game's void makeMove(Direction), but includes definition for safe teleports.
	 *
	 * @param dir The Direction to move.
	 * @return True if the move is sucessful, otherwise, false.
	 */
	public boolean makeMove(Direction dir)
	{
		if(this.hasSafeTeleports && human.isAlive() && dir == Direction.SAFE && safeTeleports > 0)
		{
			safeTeleports--;
			int row, col;
			do
			{
				row = generator.nextInt(ROWS);
				col = generator.nextInt(COLS);
			}
			while(!isValid(board[row][col], Direction.SAME));
			human.changePositionTo(row, col);
			board[row][col] = human;
			updateBoard();
			safeTeleportsLabel.setText("Safe Teleports: " + safeTeleports);
			return true;
		}
		else if(this.hasSafeTeleports && dir == Direction.SAFE) //&& safeTeleports <= 0
		{
			return super.makeMove(Direction.RANDOM);
		}
		else
		{
			return super.makeMove(dir);
		}
	}

	/**
	 * Increases the number of available safe teleports when the level is increased. 
	 */
	public void increaseLevel()
	{
		super.increaseLevel();
		if(this.hasSafeTeleports)
		{
			safeTeleports++;
			safeTeleportsLabel.setText("Safe Teleports: " + safeTeleports);
		}
	}

	/**
	 * Resets the board.  Also sets the number of safe teleports to zero.
	 */
	public void resetBoard()
	{
		safeTeleports = 0;
		super.resetBoard();
	}

	/**
	 * Gets the label for the number of safe teleports.
	 */
	public JLabel getSafeTeleportsLabel()
	{
		return safeTeleportsLabel;
	}
}
