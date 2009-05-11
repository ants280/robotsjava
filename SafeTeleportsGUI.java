import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import javax.swing.JLabel;

/**
 * A robots game with safe teleports.  On each level completion the player is granted a teleport that will not result in death.  The player can still teleport randomly by pressing '*' even when he/she has available safe teleports.
 */
public class SafeTeleportsGUI extends Gui
{
	/**
	 * Creates a Robots game with safe teleports.
	 */
	public SafeTeleportsGUI()
	{
		super(new SafeTeleportsGame());
		this.addLabel(((SafeTeleportsGame)game).getSafeTeleportsLabel());
	}

	/**
	 * Must be implemented from RobotGUI.  Tells what type of Game is being played.
	 */
	public String getGameType()
	{
		return "Safe teleports";
	}

	/**
	 * Extends RobotGUI's void KeyPressed(KeyEvent) with options for safe teleports.
	 */
	public void keyPressed(KeyEvent key)
	{
		char keyChar = key.getKeyChar();
		if(key.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD && keyChar == '+') 
		{
			this.performAction(Direction.SAFE);
		}
		else if(key.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD && keyChar == '*')
		{
			this.performAction(Direction.RANDOM);
		}
		else
		{
			super.keyPressed(key);
		}
	}
}

