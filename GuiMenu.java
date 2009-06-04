import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GuiMenu extends JMenuBar implements ActionListener
{
	private static final long serialVersionUID = 4860132710390756010L;
	private Gui gui;
	private HighScoresFrame highScoresFrame;
	private AboutFrame aboutFrame;
	private SettingsFrame settingsFrame;
	private HelpFrame helpFrame;

	/**
	 * Gets the highScoresFrame.  Used by the gui to add a new high score to the high score list.
	 *
	 * @return The highScoresFrame.
	 */
	public HighScoresFrame getHighScoresFrame() { return highScoresFrame; }

	/**
	 * Creates the main menu bar.
	 *
	 * @param gui The gui to place the menuBar in.
	 */
	public GuiMenu(Gui gui)
	{
		super();
		this.gui = gui;
		createFrames();

		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");

		JMenuItem settingsMenuItem = new JMenuItem("Settings");
				settingsMenuItem.setActionCommand("settings");
				settingsMenuItem.addActionListener(this);
		JMenuItem highScoresItem = new JMenuItem("High Scores");
				highScoresItem.setActionCommand("high scores");
				highScoresItem.addActionListener(this);
		JMenuItem restartItem = new JMenuItem("Restart");
				restartItem.setActionCommand("restart");
				restartItem.addActionListener(this);
		JMenuItem closeItem = new JMenuItem("Close");
				closeItem.setActionCommand("close");
				closeItem.addActionListener(this);
		JMenuItem helpItem = new JMenuItem("Help");
				helpItem.setActionCommand("help");
				helpItem.addActionListener(this);
		JMenuItem aboutItem = new JMenuItem("About");
				aboutItem.setActionCommand("about");
				aboutItem.addActionListener(this);

		fileMenu.add(settingsMenuItem);
		fileMenu.add(highScoresItem);
		fileMenu.add(restartItem);
		fileMenu.add(closeItem);
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);

		this.add(fileMenu);
		this.add(helpMenu);
	}

	/**
	 * Instantiates the option frames.
	 */
	private void createFrames()
	{
		highScoresFrame = new HighScoresFrame(gui);
		aboutFrame = new AboutFrame(gui);
		settingsFrame = new SettingsFrame(gui, gui.getGame());
		helpFrame = new HelpFrame();
	}

	/**
	 * Is called when a button is pressed.  Opens up the corresponding menu.
	 *
	 * @param event The event triggered by a button.
	 */
	public void actionPerformed(ActionEvent event)
	{
		String command = event.getActionCommand();
		if(command.equals("restart"))
		{
			final int choice = JOptionPane.showConfirmDialog(this, "Do you want to restart the game", "Restart?", JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION)
			{
				gui.getGame().resetBoard();
			}
		}
		else if(command.equals("high scores"))
		{
			highScoresFrame.setVisible(true, false);
		}
		else if(command.equals("settings"))
		{
			settingsFrame.setVisible(true);
		}
		else if(command.equals("help"))
		{
			helpFrame.setVisible(true);
		}
		else if(command.equals("close"))
		{
			System.exit(0);
		}
		else if(command.equals("about"))
		{
			aboutFrame.setVisible(true);
		}
	}
}
