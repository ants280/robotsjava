package com.ants280.robots.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;

/**
 * Provides a connection to the server-side database of high scores. Uses the java.swl package.  Keeps track of both personal and global high scores.
 * Some of these docs copied directly from "http://java.sun.com/j2se/1.5.0/docs/api/java/sql/DriverManager.html".
 */
public class HighScoreBot
{
	/**
	 * The name of the user accumulating the high scores.
	 */
	private String username;

	/**
	 * The connection to the database.
	 */
	private Connection conn;

	/**
	 * Creates a new HighScoreBot. Establishes connection to the database.
	 *
	 * @param username The name of the user accumulationg the high scores.
	 * @param url A database url of the form jdbc:subprotocol:subname
	 * @param user The database user on whose behalf the connection is being made.
	 * @param password The user's password.
	 */
	public HighScoreBot(String username, String url, String user, String password)
	{
		this.username = username;

		try
		{
			conn = DriverManager.getConnection(url, user, password);
		}
		catch(SQLException ex)
		{
			//Don't worry, nothing bad can happen.
			conn = null;
		}
	}

	/**
	 * Gives a score to the database.  The score may be added to up two two tables.
	 *
	 * @param score The score being fed to the HighScoreBot.
	 * @return An AddedScore which describes the result of the score that was fed.
	 */
	public AddedScore feedHighScore(int score)
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
			return AddedScore.AccessError;
		}

		// TODO: Add score to the highScore table.
		try
		{
			Date date = new Date(System.currentTimeMillis());
			query = "INSERT INTO highScores (username, score, date) VALUES('" + username + "', '" + score + "', '" + date + "')"; 
			stmt.executeUpdate(query);
		}
		catch(SQLException ex)
		{
			// Could not add the score to the database.
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
}
