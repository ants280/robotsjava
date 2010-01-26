/**
 * A class whose sole purpose is to house the main.
 */
public class Program
{
	/**
	 * Runs the RobotGui
	 *
	 * @param args The arguments for the game.  Not used.
	 */
	public static void main(String[] args)
	{
		Gui gui = new Gui(new Game());
		gui.setVisible(true);
	}
}
