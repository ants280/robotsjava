import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;

/**
 * A robots game with safe teleports.  On each level completion the player is granted a teleport that will not result in death.  The player can still teleport randomly by pressing '*' even when he/she has available safe teleports.
 */
public class SafeTeleportsGui extends Gui
{
	private boolean hasSafeTeleports;

	/**
	 * Creates a Robots game with safe teleports.
	 *
	 * @param hasSafeTeleports Whether or not the game has safe teleports.
	 */
	public SafeTeleportsGui(boolean hasSafeTeleports)
	{
		super(new SafeTeleportsGame(hasSafeTeleports));
		if(hasSafeTeleports)
		{
			this.addLabel(((SafeTeleportsGame)this.getGame()).getSafeTeleportsLabel());
		}
		this.hasSafeTeleports = hasSafeTeleports;
		this.addMenu();
	}

	/**
	 * Must be implemented from RobotGui.  Tells what type of Game is being played.
	 */
	public String getGameType()
	{
		return "Classic game" + (hasSafeTeleports ? " with safe teleports" : "");
	}

	/**
	 * Extends RobotGui's void KeyPressed(KeyEvent) with options for safe teleports.
	 */
	public void keyPressed(KeyEvent key)
	{
		char keyChar = key.getKeyChar();
		if(hasSafeTeleports && key.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD && keyChar == '+') 
		{
			this.performAction(Direction.SAFE);
		}
		else if(hasSafeTeleports && key.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD && keyChar == '*')
		{
			this.performAction(Direction.RANDOM);
		}
		else
		{
			super.keyPressed(key);
		}
	}
}

