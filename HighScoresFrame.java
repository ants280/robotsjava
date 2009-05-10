import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * A frame that shows the player the high scores that have been made.  The high scores can also be reset via this frame.  Reads the list of high scores out of a text file named HighScores.txt.  Only the top 5 scores for each game mode are stored. Scores are stored in an xml file.
 */
public class HighScoresFrame extends JFrame implements ActionListener
{
	private JLabel scoresLabel;
	private JFrame resetDialougeFrame;
	private GameRestarterFrame gameRestarterFrame;
	private HashMap<String, TreeMap<Integer, String>> scoresList;
	private RobotGUI currentGUI;
	
	//For restarting the game on frame close.
	private boolean restartGame;

	/**
	 * Constructs HighScoresFrame.
	 *
	 * @param currentGUI The current type of GUI the player is using.  For hiding the GUI when the AboutFrame is clicked.
	 */
	public HighScoresFrame(RobotGUI currentGUI, GameRestarterFrame gameRestarterFrame)
	{
		super("High Scores");
		this.currentGUI = currentGUI;
		this.gameRestarterFrame = gameRestarterFrame;
		resetDialougeFrame = resetDialougeFrame();
		scoresLabel = new JLabel("High scores for " + currentGUI.getGameType() + " mode.");

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
	}

	/**
	 * Creates a Frame to ask the player if he\she wants to reset the list of high scores.
	 */
	public JFrame resetDialougeFrame()
	{
		JFrame frame = new JFrame("Reset?");
		JLabel promptLabel = new JLabel("<html><pre>Are you sure you want to<br> reset the high scores<br>  for " + currentGUI.getGameType().toLowerCase() + " mode?</pre></html>");
		JButton yesButton = new JButton("Yes");
		yesButton.setActionCommand("resetYes");
		yesButton.addActionListener(this);

		JButton noButton = new JButton("NO!");
			noButton.setActionCommand("resetNo");
			noButton.addActionListener(this);

		frame.add(promptLabel, BorderLayout.NORTH);
		frame.add(yesButton, BorderLayout.WEST);
		frame.add(noButton, BorderLayout.EAST);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		return frame;
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
			this.setVisible(false);
			resetDialougeFrame.setVisible(true);
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

	//Updates the scoresLabelText.  Should only be called when the current high scores change.
	private void updateScoresLabel()
	{
		TreeMap<Integer, String> currentList = this.scoresList.get(currentGUI.getGameType());
		if(currentList != null)
		{
			String scoresListText = "<html>";
			Stack<String> stack= new Stack<String>();
			for(Integer key : currentList.keySet())
			{
				stack.push(currentList.get(key) + '\t' + key + "<br>");
			}
			while(!stack.empty())
			{
				scoresListText += stack.pop();
			}
			scoresListText += "</html>";
			scoresLabel.setText(scoresListText);
		}
	}

	//Called when constructed.
	private void readScores()
	{
		scoresList = new HashMap<String, TreeMap<Integer, String>>();
		String type;
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("HighScores.txt"));
			while(reader.ready())
			{
				do
				{
					type = reader.readLine();
				} while(reader.ready() && type.length() < 1);
				for(int i = 0; i < 5 && reader.ready(); i++)
				{
					this.addScore(type, reader.readLine());
				}
			}
			reader.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	//called when the user confirms to reset the scores.
	private void resetScores()
	{
		TreeMap tempMap = new TreeMap<Integer, String>();
		for(int i = 5; i > 0; i--)
		{
			tempMap.put((Integer)(i * 5), "Jacob Patterson");
		}
		this.scoresList.put(currentGUI.getGameType(), tempMap);
		this.saveScores();
		this.updateScoresLabel();
	}

	/**
	 * Adds the specified score to the list of high scores. Removes all high scores lower than the top 4 (To make 5 total).
	 *
	 * @param gameModeType The Game-mode to add the high score to.
	 * @param score The score to add to the list of high scores.
	 */
	public void addScore(String gameModeType, String score)
	{
		TreeMap<Integer, String> map = this.scoresList.get(gameModeType);
		if(map == null)
		{
			map = new TreeMap<Integer, String>();
		}
		String[] scoreArray = score.split(" ");
		Integer points = new Integer(scoreArray[scoreArray.length - 1]);
		String	name = this.getName(scoreArray).trim();
			name = (name.equals("") ? "Anonymous" : name);
		while(map.size() >= 5)
		{
			map.remove(map.firstKey());
		}
		map.put(points, name);
		this.scoresList.put(gameModeType, map);
	}

	// For formatting the name from the file as to inslude spaces and trailing numbers (ex: fat bob 123)
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
		try
		{
			TreeMap<Integer, String> gameTypeScores;
			BufferedWriter writer = new BufferedWriter(new FileWriter("HighScores.txt"));
			for(String gameMode : this.scoresList.keySet())
			{
				writer.write(gameMode);
				writer.newLine();
				gameTypeScores = this.scoresList.get(gameMode);
				for(Integer key : gameTypeScores.keySet())
				{
					writer.write(gameTypeScores.get(key) + " " + key);
					writer.newLine();
				}
			}
			writer.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Sees if the given score is high enough to be a high score.
	 *
	 * @param score The score to see if is a high score.
	 * @return True if is a high score, otherwise, false.
	 */
	public boolean isHighScore(int score)
	{
		if(score > this.scoresList.get(currentGUI.getGameType()).firstKey() || this.scoresList.get(currentGUI.getGameType()).size() < 5)
		{
			return true;
		}
		return false;
	}

	/**
	 * Shows or hides this component depending on the value of parameter visible.
	 *
	 * @param visible If true, shows this component; otherwise, hides this component
	 * @param restartGame Tells wether or not to restart the game when the HighScoresPrame is closed (if visible is True).
	 */
	public void setVisible(boolean visible, boolean restartGame)
	{
		if(!visible)
		{
			this.saveScores();
			if(restartGame)
			{
				gameRestarterFrame.setVisible(true);
			}
			super.setVisible(false);
			currentGUI.setVisible(true);
		}
		else
		{
			this.restartGame = restartGame;
			this.updateScoresLabel();
			this.pack();

			currentGUI.setVisible(false);
			super.setVisible(true);
		}
	}
}	