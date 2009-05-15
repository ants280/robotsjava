import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.lang.Thread;

/**
 * The home control point for the game.  Includes options and information about the game.
 * TODO: get buttons to work with keylistener.
 */
public abstract class Gui extends JFrame implements KeyListener
{
	/**
	 * Abstracte Method to tell type of game.  Used for options such as game type selection and high score viewing.
	 */
	public abstract String getGameType();

	private Thread thread;
	private boolean continous;
	private Game game;
	private GuiMenu menu;
	private JLabel levelLabel;
	private JLabel scoreLabel;
	private JToolBar southToolBar;


	/**
	 * Gets the Game used by the GUI.
	 *
	 * @return The game.
	 */
	public Game getGame() { return game; }

	/**
	 * Gets the level label.
	 *
	 * @return The level label.
	 */
	public JLabel getLevelLabel() { return levelLabel; }

	/**
	 * Gets the score label.
	 *
	 * @return The score label.
	 */
	public JLabel getScoreLabel() { return scoreLabel; }

	/**
	 * Creates a default, classic game.  Sets up all componets and some frames
	 */
	public Gui(Game game)
	{
		super();
		super.setTitle("Robots - " + this.getGameType().toLowerCase() + " mode.");
		this.game = game;
		levelLabel = new JLabel("Level 1");
		scoreLabel = new JLabel("Score: 0");;

		thread = new Thread();

		southToolBar = new JToolBar();
		southToolBar.add(levelLabel);
		southToolBar.add(new JLabel("  |  "));
		southToolBar.add(scoreLabel);
		southToolBar.setFloatable(false);
		
		menu = new GuiMenu(this);

		this.add(menu, BorderLayout.NORTH);
		this.add(game, BorderLayout.CENTER);
		this.add(southToolBar, BorderLayout.SOUTH);
		this.addKeyListener(this); 
		this.setSize(820, 690);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Not implemented.
	 *
	 * @param key The event triggered when a key is pressed
	 */
	public void keyTyped(KeyEvent key)
	{
		//Does nothing.
	}

	/**
	 * Handles Keyboard Input. Handles Keys :<p>
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
	 * ENTER - Moves the bots toward you (in the 'SAME' position) until either you or all of them die. 
	 *
	 * @param key The event triggered when a key is pressed
	 */
	public void keyPressed(KeyEvent key)
	{
		if(game.getHuman().isAlive())
		{
			if(key.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD)
			{
				continous = false;
				switch(key.getKeyChar())
				{
					case '1' : performAction(Direction.SW);  break;
					case '2' : performAction(Direction.S);   break;
					case '3' : performAction(Direction.SE);  break;
					case '4' : performAction(Direction.W);   break;
					case '5' : performAction(Direction.SAME);break;
					case '6' : performAction(Direction.E);   break;
					case '7' : performAction(Direction.NW);  break;
					case '8' : performAction(Direction.N);   break;
					case '9' : performAction(Direction.NE);  break;
					case '+' : performAction(Direction.RANDOM);break;
					case KeyEvent.VK_ENTER : performAction(Direction.CONTINOUS); break;
					case KeyEvent.VK_K :
				}
			}
		}
		else // game.gutHuman.IsAlive == false
		{
			performAction(null);	
		}
		
	}

	/**
	 * Not implemented.
	 *
	 * @param key The event triggered when a key is released.
	 */
	public void keyReleased(KeyEvent key)
	{
		//Does nothing.
	}

	/**
	 * For adding a label to the south toolbar.
	 *
	 * @param label The label to add to the south toolbar.
	 */
	protected void addLabel(JLabel label)
	{
		southToolBar.add(new JLabel("  |  "));
		southToolBar.add(label);
	}

	/**
	 * Moves the player in the specifed Direction.  Also moves the player the correct number of steps in the game.
	 *
	 * @param move The Direction to move the Player.
	 */
	protected void performAction(Direction move)
	{
		if(move != null)
		{
			boolean continous = (move == Direction.CONTINOUS ? true : false);
			do
			{
				game.makeMove(move);
				game.printBoard();
				if(move == Direction.CONTINOUS)
				{
					try
					{
						thread.sleep(100);
					}
						catch(InterruptedException ex)
					{
						ex.printStackTrace();
					}
				}
				if(!game.getHuman().isAlive() || game.numBots() == 0)
				{
					continous = false;
				}
			}
			while(continous);
		}
		if(game.getHuman().isAlive())
		{
			scoreLabel.setText("Score: " + game.getScore());
			if(game.numBots() == 0)
			{
				try
				{
					thread.sleep(1000);
				}
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}
				game.increaseLevel();
				game.printBoard();
				levelLabel.setText("Level: " + game.getLevel());
			}
		}
		else
		{
			this.removeKeyListener(this);
			if(menu.getHighScoresFrame().isHighScore(game.getScore()))
			{
				NameGetterFrame nameGetterFrame = new NameGetterFrame(menu.getHighScoresFrame(), game.getScore(), this);
			}
			else
			{
				menu.getGameRestarterFrame().openWithQuit(true);
			}
			this.addKeyListener(this);
		}
	}
}
