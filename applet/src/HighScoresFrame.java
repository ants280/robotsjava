import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * A frame that shows the player the high scores that have been made.  The high scores can also be reset via this frame.  Reads the list of high scores out of a text file named HighScores.txt.  Only the top 5 scores for each game mode are stored. Scores are stored in an xml file.
 */
public class HighScoresFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel scoresLabel;
	private JFrame resetDialougeFrame;
	private HashMap<String, TreeMap<Integer, String>> scoresList;
	private Gui currentGui;
	
	/**
	 * For restarting the game on frame close.
	 */
	private boolean restartGame;

	/**
	 * Constructs HighScoresFrame.
	 *
	 * @param currentGui The current type of Gui the player is using.  For hiding the Gui when the AboutFrame is clicked.
	 */
	public HighScoresFrame(Gui currentGui)
	{
		super("High Scores");
		this.currentGui = currentGui;
		scoresLabel = new JLabel("High scores");

		JButton okButton = new JButton("Ok");
			okButton.setActionCommand("ok");
			okButton.addActionListener(this);
		JButton resetButton = new JButton("Reset");
			resetButton.setActionCommand("resetDialouge");
			resetButton.addActionListener(this);

		this.readScores();
		this.add(scoresLabel, BorderLayout.NORTH);
		this.add(okButton, BorderLayout.WEST);
		this.add(resetButton, BorderLayout.EAST);
		this.setSize(200, 200);
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
		String command = event.getActionCommand();
		if(command.equals("ok"))
		{
			this.setVisible(false, restartGame);
		}
		else if(command.equals("resetDialouge"))
		{
			String message = "<html><pre>Are you sure you want to<br> reset the high score?</pre></html>";
			JOptionPane.showConfirmDialog(this, message, "Reset?", JOptionPane.YES_NO_OPTION);
		}
		else if(command.equals("resetYes"))
		{
			resetScores();
			resetDialougeFrame.setVisible(false);
			this.pack();
			this.setVisible(true);
		}
		else if(command.equals("resetNo"))
		{
			resetDialougeFrame.setVisible(false);
			this.setVisible(true);
		}
	}

	/**
	 * Updates the scoresLabelText.  Should only be called when the current high scores change.
	 */
	private void updateScoresLabel()
	{
	}

	/**
	 * Called when constructed.
	 */
	private void readScores()
	{
	}

	/**
	 * called when the user confirms to reset the scores.
	 */
	private void resetScores()
	{
	}

	/**
	 * Adds the specified score to the list of high scores. Removes all high scores lower than the top 4 (To make 5 total).
	 *
	 * @param gameModeType The Game-mode to add the high score to.
	 * @param score The score to add to the list of high scores.
	 */
	public void addScore(String gameModeType, String score)
	{
	}

	/**
	 * For formatting the name from the file as to include spaces and trailing numbers (Example: fat bob 123)
	 * 
	 * @param separated
	 * @return
	 */
	private String getName(String[] separated)
	{
		String name = "";
		int pos = 0;
		while(pos != separated.length - 1)
		{
			name += separated[pos++] + ' ';
		}
		return name;
	}

	/**
	 * Saves the high scores.
	 */
	public void saveScores()
	{
	}

	/**
	 * Sees if the given score is high enough to be a high score.
	 *
	 * @param score The score to see if is a high score.
	 * @return True if is a high score, otherwise, false.
	 */
	public boolean isHighScore(int score)
	{
		return false;
	}

	/**
	 * Shows or hides this component depending on the value of parameter visible.
	 *
	 * @param visible If true, shows this component; otherwise, hides this component
	 * @param restartGame Tells whether or not to restart the game when the HighScoresPrame is closed (if visible is True).
	 */
	public void setVisible(boolean visible, boolean restartGame)
	{
		if(!visible)
		{
			if(restartGame)
			{
				final int choice = JOptionPane.showConfirmDialog(this, "Do you want to restart the game?", "Restart?", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.YES_OPTION)
				{
					currentGui.getGame().resetBoard();
				}
				else if(choice == JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
			}
		}
		else
		{
			this.restartGame = restartGame;
			this.updateScoresLabel();
			this.pack();
		}
		currentGui.setEnabled(!visible);
		currentGui.setVisible(!visible);
		super.setVisible(visible);
	}
}	
