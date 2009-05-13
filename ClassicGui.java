/**
 * The classic version of the game.  Only Robots and random teleporting.
 */
public class ClassicGui extends Gui
{
	/**
	 * Constructor for the Gui.  Everything set to default values.
	 */
	public ClassicGui()
	{
		super(new Game());
	}

	/**
	 * Must be implemented from RobotGui.  Tells what type of Game is being played.
	 */
	public String getGameType()
	{
		return "Classic";
	}
}
