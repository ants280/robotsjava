import javax.swing.JLabel;

/**
 * A game that allows safe teleports.
 */
public class SafeTeleportsGame extends Game
{
	private int safeTeleports;
	private JLabel safeTeleportsLabel;
	/**
	 * Creates a new SafeTeleportsGame.
	 */
	public SafeTeleportsGame()
	{
		super();
		this.safeTeleportsLabel = new JLabel("Safe Teleports: 0");
	}

	/**
	 * Puts the robots on the board.  Same as Game's  void setNumBots(int), but the number of bots is capped at SIDE * SIDE / 2.  This prevents the Player from having no possible safe teleports because of the amount of SafeTeleports.
	 */
	protected void setNumBots(int numBots)
	{
		if(numBots > SIDE * SIDE / 2)
		{
			super.setNumBots(SIDE * SIDE / 2);
		}
		else
		{
			super.setNumBots(numBots);
		}
	}

	/**
 	 * Same as Game's void makeMove(Direction), but includes definition for safe teleports.
	 *
	 * @param dir The Direction to move.
	 */
	public void makeMove(Direction dir)
	{
		if(human.isAlive() && dir == Direction.SAFE && safeTeleports > 0)
		{
			safeTeleports--;
			int row, col;
			do
			{
				row = generator.nextInt(SIDE);
				col = generator.nextInt(SIDE);
			}
			while(!super.isValid(board[row][col], Direction.SAME));
			human.changePositionTo(row, col);
			board[row][col] = human;
			updateBoard();
			safeTeleportsLabel.setText("Safe Teleports: " + safeTeleports);
		}
		else if(dir == Direction.SAFE) //&& safeTeleports <= 0
		{
			super.makeMove(Direction.RANDOM);
		}
		else
		{
			super.makeMove(dir);
		}
	}

	/**
	 * Increases the number of available safe teleports when the level is increased. 
	 */
	public void increaseLevel()
	{
		safeTeleports++;
		super.increaseLevel();
		if(safeTeleportsLabel != null)
		{
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
