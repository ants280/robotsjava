import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;

/**
 * A frame that displays how to play the game.
 */
public class HelpFrame extends JFrame implements ActionListener
{
	/**
	 * Constructs an HelpFrame.
	 */
	public HelpFrame()
	{
		super("Help");
		JLabel helpLabel = new JLabel(
"<html>Robots<br>" + 
"To play, use the keypad keys with the numlock on.  The '5' key is the \"no movement\" key.<br>" +
"the others move the player one move in the same direction as the key from the '5' key.  On<br>" +
"each turn the robots move one step toward the player, even if the player moves.  Robots<br>" +
"move in the same manner as the player.  When two robots collide (you cam force them to)<br>" +
"theywill form a wreck.  Any piece that moves in to this unmoving wreck will die. If there<br>" +
"are no possible moves, the player will be forced to teleport. The '+' key on the keypad<br>" +
"randomly teleports the player.  This can cause the player to land on an enemy, or somewhere<br>" + 
"an enemy might move, resulting in player death.  If the player does not like taking this big<br>" +
"of a risk, he/she can play the game in \"safe\" teleports mode.  On each level completion,<br>" +
"the player earns one safe teleport.  The player can use this ability with the '*' key on the<br>" +
"keypad.  Safe teleports can be saved early in a game for harder levels.<br>" + 
"Have fun playing Robots!</html>"); 

		JButton closeButton = new JButton("close");
			closeButton.setActionCommand("close");
			closeButton.addActionListener(this);

		this.add(helpLabel, BorderLayout.NORTH);
		this.add(closeButton, BorderLayout.SOUTH);
		this.pack();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	/**
	 * The action performed when a button is pressed.
	 *
	 * @param event The event triggered by the close button.
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("close"))
		{
			this.setVisible(false);
		}
	}
}
