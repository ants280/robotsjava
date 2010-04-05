package com.ants280.robots.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PriorityQueue;

/**
 * Provides a connection to the server-side databases.  Uses the java.sql package.  Keeps track of both personal and global high scores.  Also keeps track of safe teleports.
 * Some of these docs copied directly from "http://java.sun.com/j2se/1.5.0/docs/api/java/sql/DriverManager.html".
 */
public class MysqlBot
{
	/**
	 * The name of the user accumulating the high scores.
	 */
	private String username;

	/**
	 * A database url of the form jdbc:subprotocol:subname
	 */
	private String db_url;

	/**
	 * The database user on whose behalf the connection is being made.
	 */
	private String db_user;

	/**
	 * The user's password
	 */
	private String db_password;

	/**
	 * The Connection to the high score database.
	 */
	private Connection conn;

	/**
	 * Creates a new MysqlBot.
	 *
	 * @param username The name of the user accumulationg the high scores.
	 * @param db_url A database url of the form jdbc:subprotocol:subname
	 * @param db_user The database user on whose behalf the connection is being made.
	 * @param db_password The user's password.
	 */
	public MysqlBot(String username, String db_url, String db_user, String db_password)
	{
		this.username = username;
		this.db_url = db_url;
		this.db_user = db_user;
		this.db_password = db_password;

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Connects to the database.
	 *
	 * @throws SQLException If the database could not be connected to.
	 */
	private void connectToDatabase() throws SQLException
	{
		conn  = DriverManager.getConnection(db_url, db_user, db_password);
	}

	/**
	 * Gives a score to the database.  The score may be added to up two two tables.
	 *
	 * @param score The score being fed to the MysqlBot.
	 * @return An AddedScore which describes the result of the score that was fed.
	 */
	public AddedScore feedScore(int score)
	{
		// The database connection failed.
		if(conn == null)
		{
			return AddedScore.ConnectionError;
		}

		String query;
		boolean personalHigh, globalHigh;
		Statement stmt;
		
		//Prepare to talk to the database.
		try
		{
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return AddedScore.AccessError;
		}

		// Adds the score to the highScore table.
		try
		{
			long date = System.currentTimeMillis();
			query = "INSERT INTO highScores (username, score, date) VALUES('" + username + "', '" + score + "', '" + date + "')"; 
			stmt.executeUpdate(query);
		}
		catch(SQLException ex)
		{
			// Could not add the score to the database.
			ex.printStackTrace();
			return AddedScore.InsertionError;
		}

		try
		{
			query = "SELECT score FROM highScores WHERE username='" + username + "'";
			ResultSet personalSet = stmt.executeQuery(query);
			personalHigh = this.isHighScore(score, personalSet);
		}
		catch(SQLException ex)
		{
			// Could not select from the database.
			ex.printStackTrace();
			return AddedScore.AccessError;
		}

		if(personalHigh)
		{
			try
			{
				query = "SELECT score FROM highScores";
				ResultSet globalSet = stmt.executeQuery(query);
				globalHigh = this.isHighScore(score, globalSet);
			}
			catch(SQLException ex)
			{
				// Could not select from the database.
				ex.printStackTrace();
				return AddedScore.AccessError;
			}

			if(globalHigh)
			{
				return AddedScore.GlobalHigh;
			}
			else
			{
				return AddedScore.PersonalHigh;
			}
		}
		else
		{
			return AddedScore.NormalScore;
		}
	}

	/**
	 * Finds out if the given score should be a high score.  Adds all the scores in the result set to a priority queue.  Adds the inverse of the score to the PriorityQueue because the queue is a min heap, and the inverse of a high number will be really low.
	 *
	 * @param score The score to see if is a high score.  Is a double so it can be converted to it's iverse value easily.
	 * @param set The ResultSet to see if the score should be a part of.
	 * @return Whether or not the score should be a high score in the given ResultSet.
	 * @throws SQLException If a database access error occurs.
	 */
	private boolean isHighScore(double score, ResultSet set) throws SQLException
	{
		PriorityQueue<Double> scores = new PriorityQueue<Double>();
		// Build list of current scores.
		while(set.next())
		{
			scores.add(1 / set.getDouble("score"));
		}

		score = 1 / score;
		// See if the score is higher than any of the current 5 highest scores, if they exist.
		for(int i = 0; i < 5; i++)
		{
			if(scores.isEmpty() || scores.poll() > score)
			{
				return true;
			}
		}

		// The score is not a high score in the provided ResultSet.
		return false;
	}

	/**
	 * Gets the amount of safe teleports the Player of the robots game has earned.
	 *
	 * @param cap The maximum number of safe teleports to remove from the database and return.
	 * @return The numbe of safe teleports for 'username'.
	 * @throws SQLException If the database could not be connected to.  This should cause the game to not submit future high scores.
	 */
	public int getSafeTeleports(int cap) throws SQLException
	{
		if(conn == null)
		{
			// Try to connect to the databese.
			this.connectToDatabase();
		}

		Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		// Java requires a key to be selected from all statements.
		String query = "SELECT username, safeTeleports FROM robots WHERE username='" + username + "'";
		ResultSet set = stmt.executeQuery(query);
		set.next(); // Get the result set ready for reading
		int safeTeleports = set.getInt("safeTeleports");

		int givenTeleports = 0;
		if(safeTeleports > 10)
		{
			givenTeleports = 10;
			safeTeleports -=10;
		}
		else
		{
			givenTeleports = safeTeleports;
			safeTeleports = 0;
		}

		set.updateInt("safeTeleports", safeTeleports);
		set.updateRow();

		return givenTeleports;
	}

	/**
	 * Changes the amount of safe teleports.  The amount of safe teleports can never go below 0.  This property is not chacked, as the 'safeTeleports' field of the database should be signed.
	 *
	 * @param amount The amount to increase the number of safeTeleports by.
	 * @throws SQLException If the database could not be connected to.  This should cause the game to not submit future high scores.
	 */
	public void increaseSafeTeleports(int amount) throws SQLException
	{
		if(conn == null)
		{
			throw new SQLException("There is no connection to the database!");
		}

		Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		// Java requires a key to be selected from all statements.
		String query = "SELECT safeTeleports, username FROM robots WHERE username='" + username + "'";
		ResultSet set = stmt.executeQuery(query);
		set.next(); // Get the result set ready for reading

		int safeTeleports = set.getInt("safeTeleports");
		set.updateInt("safeTeleports", safeTeleports + amount);
		set.updateRow();
	}
}
