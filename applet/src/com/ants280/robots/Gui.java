package com.ants280.robots;

import com.ants280.robots.pieces.Direction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JApplet;
import javax.swing.Timer;

/**
 * The home control point for the game.  Includes options and information about the game.
 */
public class Gui extends JApplet implements KeyListener, ActionListener
{
	/** 
	 * The game being played.
	 */
	private Game game;
	/**
	 * The system that actual triggers game moves for the game.
	 */
	private Timer timer;
	/**
	 * The move being made.
	 */
	private Direction move;

	/**
	 * The entry point for the Applet.
	 */
	public void init()
	{
		//Creates a timer with no ititial delay and a short delay if is run multiple times before being stopped (if continous).
		timer = new Timer(0, this);
		timer.setDelay(200);

		//Creates and Launches the Game.
		String username = this.getParameter("username");
		this.game = new Game(username);
		this.showGui(game);
	}

	/**
	 * Creates a default, classic game.  Sets up all components and some frames
	 */
	private void showGui(Game game)
	{
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
			switch(key.getKeyChar())
			{
				case '1' : move = Direction.SW;     timer.start(); break;
				case '2' : move = Direction.S;      timer.start(); break;
				case '3' : move = Direction.SE;     timer.start(); break;
				case '4' : move = Direction.W;      timer.start(); break;
				case '5' : move = Direction.SAME;   timer.start(); break;
				case '6' : move = Direction.E;      timer.start(); break;
				case '7' : move = Direction.NW;     timer.start(); break;
				case '8' : move = Direction.N;      timer.start(); break;
				case '9' : move = Direction.NE;     timer.start(); break;
				case '+' : move = Direction.SAFE;   timer.start(); break;
				case '*' : move = Direction.RANDOM; timer.start(); break;
				case KeyEvent.VK_ENTER : 
					move = Direction.WAIT;
					timer.start();
					break;
			}
		}
		else
		{
			game.resetGame();
		}
	}

	/**
	 *Not implemented.
	 */
	public void keyPressed(KeyEvent key)  { }

	/**
	 * Not implemented.
	 */
	public void keyReleased(KeyEvent key) { }

	/**
	 * Moves the player in the specified Direction.  Also moves the player the correct number of steps in the game.
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(move != null)
		{
			game.makeMove(move);
			game.repaint();

			// Determines if game should stop moving.  Don't stop UNLESS the Player is WAITing AND the game's human is still alive AND some robots are still alive.
			if( !(move == Direction.WAIT && game.getHuman().isAlive() && game.numBots() != 0) )
			{
				timer.stop();
			}

			// Level was just increased.
			if(game.numBots() == 0 && game.getHuman().isAlive())
			{
				// Lags the display after each level.
				move = null;
				timer.setInitialDelay(1000);

				// Forces the action to be performed again, but to only pause before increasing the level.
				timer.start();
			}
		}
		else
		{
			game.increaseLevel();

			// Lag complete.  Set delay between moves to 0.  Stop timer.
			timer.setInitialDelay(0);
			timer.stop();
		}
	}
}
