/**
 * A class whose sole purpose is to house the main.
 */
public class Program
{
	/**
	 * Runs the RobotGui
	 */
	public static void main(String[] args)
	{
		new SafeTeleportsGui(new SafeTeleportsGame(false)).setVisible(true);
	}
}
