/**
 * A robots game with safe teleports.  On each level completion the player is granted a teleport that will not result in death.  The player can still teleport randomly by pressing '*' even when he/she has available safe teleports.
 */
public class FastRobotsGui extends SafeTeleportsGui
{
	private static final long serialVersionUID = 863928061894353221L;

	/**
	 * Creates a game with fast robots.
	 *
	 * @param game The type of game to play.  Must be a type of SafeTeleportsGame.
	 */
	public FastRobotsGui(Game game)
	{
		super(game);
	}

	/**
	 * Must be implemented from RobotGUI.  Tells what type of Game is being played.
	 */
	public String getGameType()
	{
		return "Fast robots" + safeTeleportsTitle();
	}
}

