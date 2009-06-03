import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPanel;
import java.util.HashSet;

/**
 * The Settings Frame is used to change what "mode" the game is in.  
 */
public class SettingsFrame extends JFrame implements ActionListener
{
	private Gui currentGui;
	private Game currentGame;
	private String changeModeTo;
	private String teleportType;
	private JLabel gameModeLabel;
	private JRadioButtonMenuItem normalButton;
	private JRadioButtonMenuItem safeButton;
	private ButtonGroup guiTypeButtonGroup;

	/**
	 * Constructs a new SettingsFrame.
	 *
	 * @param currentGame The current type of game the player is using.
	 * @param currentGui The current type of Gui the player is using.  Also for hiding the Gui when the AboutFrame is clicked.
	 */
	public SettingsFrame(Gui currentGui, Game currentGame)
	{
		super("Settings");

		this.currentGui = currentGui;
		this.currentGame = currentGame;

		//Set up game mode label.
		gameModeLabel = new JLabel();

		//Set up non-radiobuttons.
		JButton okButton = new JButton("Ok");
			okButton.setActionCommand("ok");
			okButton.addActionListener(this);
		JButton closeButton = new JButton("Close");
			closeButton.setActionCommand("close");
			closeButton.addActionListener(this);
		JButton applyButton = new JButton("Apply");
			applyButton.setActionCommand("apply");
			applyButton.addActionListener(this);

		HashSet<JRadioButtonMenuItem> buttonSet = new HashSet<JRadioButtonMenuItem>();
//Begin	radioButton addition section. DO NOT DELETE THIS LINE.
		buttonSet.add(new JRadioButtonMenuItem("Classic"));
		buttonSet.add(new JRadioButtonMenuItem("Fast Robots"));
//End	radioButton addition section. DO NOT DELETE THIS LINE.

		//Set Game teleports type buttons.
		JPanel teleportsTypePanel = new JPanel(new GridLayout(2, 1));
		ButtonGroup teleportsButtonGroup = new ButtonGroup();
		normalButton = new JRadioButtonMenuItem("Normal mode");
			normalButton.addActionListener(this);
			normalButton.setActionCommand("t_normal");
			teleportsButtonGroup.add(normalButton);
			teleportsTypePanel.add(normalButton);
		safeButton = new JRadioButtonMenuItem("Safe teleports");
			safeButton.addActionListener(this);
			safeButton.setActionCommand("t_safe");
			teleportsButtonGroup.add(safeButton);
			teleportsTypePanel.add(safeButton);

		//Set Game type buttons.
		JPanel guiTypePanel = new JPanel(new GridLayout(0, 1));
		guiTypeButtonGroup = new ButtonGroup();
		for(JRadioButtonMenuItem radioButton : buttonSet)
		{
			if(radioButton.getText().equals(currentGui.getGameType()))
			{
				radioButton.setSelected(true);
			}
			radioButton.setActionCommand(radioButton.getText());
			radioButton.addActionListener(this);
			guiTypeButtonGroup.add(radioButton);
			guiTypePanel.add(radioButton);
		}

		//Add the two selection panels together
		JPanel gameModePanel = new JPanel(new GridLayout(1, 2));
		gameModePanel.add(guiTypePanel);
		gameModePanel.add(teleportsTypePanel);

		//Put the buttons into a panel.
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.add(okButton);
		buttonPanel.add(closeButton);
		buttonPanel.add(applyButton);

		//Add the components to the JFrame.
		this.add(gameModeLabel, BorderLayout.NORTH);
		this.add(gameModePanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
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
		if(command.equals("apply") || command.equals("ok"))
		{
			if( !changeModeTo.equals("")  && !teleportType.equals("") )
			{
 
				boolean safe;
				Game game;
				if(teleportType.equals("true"))
				{
					safe = true;
				}
				else //(teleports.equals("false"))
				{
					safe = false;
				}
				if(changeModeTo.equals("Classic"))
				{
					game = new SafeTeleportsGame(safe);
					currentGui = new SafeTeleportsGui(game);
				}
				else if(changeModeTo.equals("Fast Robots"))
				{
					game = new FastRobotsGame(safe);
					currentGui = new FastRobotsGui(game);
				}
//Add new button selecton modes HERE. Should be an "else if".  The Command should be the same as the button's text. DO NOT DELETE THIS LINE.
				if(command.equals("ok"))
				{
					this.setVisible(false);
				}
			}
			else
			{
				gameModeLabel.setText("Choose both buttons!");
			}
		}
		else if(command.equals("close"))
		{
			this.setVisible(false);
		}
		else if(command.equals("t_normal"))
		{
			teleportType = "false";
		}
		else if(command.equals("t_safe"))
		{
			teleportType = "true";
		}
		else //command is a gameType press
		{
			changeModeTo = command;
		}
	}

	/**
	 * Shows or hides this component depending on the value of parameter visible.
	 *
	 * @param visible If true, shows this component; otherwise, hides this component
	 */
	public void setVisible(boolean visible)
	{
		//Set the teleportTypeRadioButton settings
		if(visible)
		{
			if(currentGame.isSafeTeleportsGame())
			{
				teleportType = "true";
				safeButton.setSelected(true);
			}
			else
			{
				teleportType = "false";
				normalButton.setSelected(true);
			}

			changeModeTo = "";
		}
	
		gameModeLabel.setText("Current game mode is: " + currentGui.getGameType() + " mode.");
		this.pack();
		currentGui.setVisible(!visible);
		super.setVisible(visible);
	}
}
