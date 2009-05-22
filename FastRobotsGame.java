import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * A fast robots game with no safe teleports.
 */
public class FastRobotsGame extends SafeTeleportsGame
{
	private Image fastRobotImage;

	//Tells void updateBoard(int, int) wether or not to only update fast robot's position.
	private boolean fastOnly;

	/**
	 * Creates a new FastRobtsGame.
	 *
	 * @param hasSafeTeleports Whether or not the game has safe teleports.

	 */
	public FastRobotsGame(boolean hasSafeTeleports)
	{
		super(hasSafeTeleports);
	}
	/**
	 * Initializes the images of the painted Locations.
	 */
	protected void initializeImages()
	{
		try
		{
			fastRobotImage = ImageIO.read(new File("FastRobot.jpg"));
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		super.initializeImages();
	}

	/**
	 * Adds the FastRobotImage to the group to be returned.
	 *
	 * @param row The row of the location on the board to get the image for
	 * @param col The column of the location on the board to get the image for.
	 * @return The image of the board's row and column.
	 */
	protected Image getImage(int row, int col)
	{
		if(board[row][col] instanceof FastRobot)
		{
			return fastRobotImage;
		}
		return super.getImage(row, col);
	}

	/**
	 * Sees if it is safe for the specified Location to move in the specified Direction.  If the Direction is CONTINOUS or RANDOM, true will be returned. Very much mimics Game's boolean isValid(Location, dir).
	 *
	 * @param testLocation The starting Location.
	 * @param dir The Direction to see if is valid for the testLocation to move to.
	 */
	protected boolean isValid(Location testLocation, Direction dir)
	{
		if(dir == Direction.RANDOM || dir == Direction.CONTINOUS)
		{
			return true;
		}
		Location desiredLocation = new Location(testLocation);
		desiredLocation = desiredLocation.updatePos(dir);
		if(isValid(desiredLocation))
		{
			if(board[desiredLocation.getRow()][desiredLocation.getCol()].isEnemy())
			{
				return false;
			}
			for(Location botSpot : locsAround(desiredLocation))
			{
				if(botSpot instanceof Robot)
				{
					return false;
				}
		// Only the next segment of code differs from  Game's isValid(Location, Direction)
				for(Location fastSpot : locsAround(botSpot))
				{
					if(fastSpot instanceof FastRobot)
					{
						return false;
					}
				}
		// End original code.
			}
			return true;
		}
		return false;
	}

	/**
	 * Moves the player in the specified Direction.
	 *
	 * @param dir The Direction to move.
	 */
	public void makeMove(Direction dir)
	{
		super.makeMove(dir);

		// Makes the fast robots move again.
		if(isValid(human, dir))
		{			// Makes the fast robots move again.
			fastOnly = true;
			updateBoard();
			fastOnly = false;
		}
	}

	/**
	 * Used for updating the board. Called for each location. Saves the new piece to a location on the tempBoard.  If the selected piece lands on another piece on the tempBoard, A Wreck should created in the boardSpot with the boardSpot's location.
	 *
	 * @param boardRow The row of the piece being moved.
	 * @param boardCol The column of the piece being moved.
	 * @return Returns 1 if the piece to move dies.  Returns 2 if lands on another piece.  Otherwise returns 0.
	 */
	protected int updateBoard(int boardRow, int boardCol)
	{
		Location boardSpot = board[boardRow][boardCol];
		if(!fastOnly || boardSpot instanceof FastRobot)
		{
			return super.updateBoard(boardRow, boardCol);
		}
		else
		{
			int numToReturn = 0;
			if(tempBoard[boardRow][boardCol] instanceof FastRobot)
			{
				if(boardSpot instanceof Robot)
				{
					tempBoard[boardRow][boardCol] = new Wreck(boardRow, boardCol);
					numToReturn = 2;
				}
				else if(boardSpot instanceof Wreck)
				{
					tempBoard[boardRow][boardCol] = boardSpot;
					numToReturn = 1;
				}
				else if(boardSpot instanceof Player)
				{
					((Player)boardSpot).die();
					tempBoard[boardRow][boardCol] = boardSpot;
					numToReturn = 1;
				}
			}
			return numToReturn;
		}
	}

	/**
	 * Adds normal robots on every number or a fast bot on odd numbers. Only adds fast robots if the number of robots to add is greater than 2.
	 *
	 * @param row The row to add the robot to.
	 * @param col The column to add the robot to.
	 * @param pos The nth enemy being added.
	 */
	protected void addEnemy(int row, int col, int pos)
	{
		board[row][col] = new FastRobot(row, col);
		/*
		if(pos + 1 > 2 && pos % 2 == 1)
		{
			board[row][col] = new FastRobot(row, col);
		}
		else //Adds a robot
		{
			super.addEnemy(row, col, pos);
		}
		*/
	}
}
