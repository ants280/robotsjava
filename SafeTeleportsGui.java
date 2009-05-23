import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;

/**
 * A robots game with safe teleports.  On each level completion the player is granted a teleport that will not result in death.  The player can still teleport randomly by pressing '*' even when he/she has available safe teleports.
 */
public class SafeTeleportsGui extends Gui
{
	/**
	 * Creates a Robots game with safe teleports.
	 *
	 * @param game The type of game to play.  Must be a typeof SafeTeleportsGame.
	 */
	public SafeTeleportsGui(Game game)
	{
		super(game);
		if(game.isSafeTeleportsGame())
		{
			this.addLabel(((SafeTeleportsGame)game).getSafeTeleportsLabel());
		}
		this.addMenu();
	}

	/**
	 * Must be implemented from RobotGui.  Tells what type of Game is being played.
	 */
	public String getGameType()
	{
		return "Classic game" + (getGame().isSafeTeleportsGame() ? " with safe teleports" : "");
	}

	/**
	 * Extends RobotGui's void KeyPressed(KeyEvent) with options for safe teleports.
	 */
	public void keyPressed(KeyEvent key)
	{
		if(getGame().isSafeTeleportsGame() && key.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD)
		{
			if(key.getKeyChar() == '+') 
			{
				performAction(Direction.SAFE);
			}
			else if(key.getKeyChar() == '*')
			{
				performAction(Direction.RANDOM);
			}
			else
			{
				super.keyPressed(key);
			}
		}
		else
		{
			super.keyPressed(key);
		}
	}
}

