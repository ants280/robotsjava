package com.robots.gui;

import java.awt.event.KeyEvent;
import com.robots.Direction;
import com.robots.game.Game;
import com.robots.game.SafeTeleportsGame;

/**
 * A robots game with safe teleports.  On each level completion the player is granted a teleport that will not result in death.  The player can still teleport randomly by pressing '*' even when he/she has available safe teleports.
 */
public class SafeTeleportsGui extends Gui
{
	/**
	 * Used for serializing.
	 */
	private static final long serialVersionUID = 0;
	
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
		return "Classic game" + safeTeleportsTitle();
	}

	/**
	 * For adding to the label of the game if it has safe teleports.
	 * TODO:  this should be made better (shouldn't exist!)
	 * @return A special string if the game has safe teleports.
	 */
	protected String safeTeleportsTitle()
	{
		if(getGame().isSafeTeleportsGame())
		{
			return " with safe teleports";
		}
		return "";
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

