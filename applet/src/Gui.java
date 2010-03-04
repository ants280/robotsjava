import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JApplet;
import javax.swing.Timer;
import Pieces.Direction;

/**
 * The home control point for the game.  Includes options and information about the game.
 */
public class Gui extends JApplet implements KeyListener, ActionListener
{
	private boolean continous;
	private Game game;
	private Timer timer;

	public void init()
	{
		timer = new Timer(0, this);
		timer.setDelay(200);
		this.showGui(new Game());
	}

	/**
	 * Creates a default, classic game.  Sets up all components and some frames
	 */
	public void showGui(Game game)
	{
		this.game = game;
		this.game.addKeyListener(this);

		this.add(game);
	}

	/**
	 * Handles Keyboard Input.
	 * Handles Keys :<p>
	 * Keypad Keys:<p>
	 * S - Settings.<p>
	 * 1 - W.<p>
	 * 2 - S.<p>
	 * 3 - SE.<p>
	 * 4 - W.<p>
	 * 5 - Moves the robots without the player moving.<p>
	 * 6 - E.<p>
	 * 7 - NW.<p>
	 * 8 - N.<p>
	 * 9 - NE.<p>
	 * + - Teleports Randomly.<p>
	 * ENTER - Moves the robots toward you (in the 'SAME' position) until either you or all of them die. 
	 *
	 * @param key The event triggered when a key is pressed
	 */
	public void keyTyped(KeyEvent key)
	{
		if(game.getHuman().isAlive())
		{
			continous = false;
			switch(key.getKeyChar())
			{
				case '1' : this.performAction(Direction.SW);    break;
				case '2' : this.performAction(Direction.S);     break;
				case '3' : this.performAction(Direction.SE);    break;
				case '4' : this.performAction(Direction.W);     break;
				case '5' : this.performAction(Direction.SAME);  break;
				case '6' : this.performAction(Direction.E);     break;
				case '7' : this.performAction(Direction.NW);    break;
				case '8' : this.performAction(Direction.N);     break;
				case '9' : this.performAction(Direction.NE);    break;
				case '+' : this.performAction(Direction.SAFE);  break;
				case '*' : this.performAction(Direction.RANDOM);break;
				case KeyEvent.VK_ENTER : 
					continous = true;
					this.performAction(Direction.CONTINUOUS);
					break;
			}
		}
		else
		{
			//Game needs to be restarted.
			game.resetBoard();
		}
	}

	/**
	 * Not implemented.
	 */
	public void keyPressed(KeyEvent key) { }

	/**
	 * Not implemented.
	 */
	public void keyReleased(KeyEvent key) { /*Does nothing. */ }

	/**
	 * Moves the player in the specified Direction.  Also moves the player the correct number of steps in the game.
	 *
	 * @param move The Direction to move the Player.
	 */
	private Direction move;
	private void performAction(Direction move)
	{
		this.move = move;
		timer.start();

	}
	public void actionPerformed(ActionEvent event)
	{
		game.makeMove(move);
		game.repaint();

		if(game.numBots() == 0 && game.getHuman().isAlive())
		{
			continous = false;
			timer.stop();
			game.increaseLevel();
		}
		else if(!(continous & game.getHuman().isAlive()))
		//else if(!continous || !game.getHuman().isAlive())
		{
			continous = false;
			timer.stop();
		}
	}
}
