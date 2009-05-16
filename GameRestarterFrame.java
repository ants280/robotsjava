import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;

/**
 * The GameRestarterFrame is a JFrame that becomes visible after the player dies.  It asks the player if he\she wants to play another Game.  If the player earns a high score, it appears after the HighScoreFrame is closed.
 */ 
public class GameRestarterFrame extends JFrame implements ActionListener
{
	private Gui currentGui;
	private Game game;
	private JButton alternativeButton;
	private JLabel levelLabel;
	private JLabel scoreLabel;

	/**
	 * Creates a new GameRestarterFrame.
	 *
	 * @param currentGui The current Gui for hiding when the GameRestarterFrame opens.
	 * @param game The current Game. For restarting it.
	 * @param levelLabel Resets the level label if the game restarts.
	 * @param scoreLabel Resets the score label if the game restarts.
	 */
	public GameRestarterFrame(Gui currentGui, Game game, JLabel levelLabel, JLabel scoreLabel)
	{
		super("Restart?");
		this.currentGui = currentGui;
		this.game = game;
		this.levelLabel = levelLabel;
		this.scoreLabel = scoreLabel;
		JLabel promptLabel = new JLabel("<html><pre>Do you want to start a new game?</pre></html>");
		JButton yesButton = new JButton("Yes");
			yesButton.setActionCommand("new game");
			yesButton.addActionListener(this);

		alternativeButton = new JButton("aoeu");
		alternativeButton.addActionListener(this);

		this.add(promptLabel, BorderLayout.NORTH);
		this.add(yesButton, BorderLayout.WEST);
		this.add(alternativeButton, BorderLayout.EAST);
		this.pack();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(currentGui);
	}

	/**
	 * The action performed when a button is pressed.
	 *
	 * @param event The event triggered by the close button.
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("new game"))
		{
			game.resetBoard();
			game.printBoard();
			levelLabel.setText("Level: " + game.getLevel());
			scoreLabel.setText("Score: " + game.getScore());

			this.setVisible(false);
		}
		else if(event.getActionCommand().equals("quit"))
		{
			System.exit(0);
		}
		else if(event.getActionCommand().equals("no"))
		{
			this.setVisible(false);
		}
	}

	/**
	 * Shows or hides this component depending on the value of parameter visible.
	 *
	 * @param visible If true, shows this component; otherwise, hides this component.
	 * @deprecated This should only be called internally to show/hide the frame.  Use {@link #openWithQuit(boolean) openWithQuit(boolean)} instead.
	 */
	public void setVisible(boolean visible)
	{
		currentGui.setVisible(!visible);
		super.setVisible(visible);
	}

	/**
	 * Should be used to open the frame.
	 *
	 * @param quit Tells wether or not set the alternive button to quit the game or close the frame.
	 */
	public void openWithQuit(boolean quit)
	{
		if(quit)
		{
			alternativeButton.setText("Quit");
			alternativeButton.setActionCommand("quit");
		}
		else
		{
			alternativeButton.setText("No");
			alternativeButton.setActionCommand("no");
		}
		this.setVisible(true);
	}
}
