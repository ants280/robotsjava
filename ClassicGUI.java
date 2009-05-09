/**
 * The classic version of the game.  Only Robots and random teleporting.
 */
public class ClassicGUI extends RobotGUI
{
	/**
	 * Constructor for the GUI.  Everything set to default values.
	 */
	public ClassicGUI()
	{
		super(new Game());
	}

	/**
	 * Must be implemented from RobotGUI.  Tells what type of Game is being played.
	 */
	public String getGameType()
	{
		return "Classic";
	}
}
