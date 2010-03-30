package com.ants280.robots.mysql;

/**
 * Used for communication the result of adding a HighScore to the server.
 */
public enum AddedScore
{
	/**
	 * Indicates the database could not be connected to.
	 */
	ConnectionError,

	/**
	 * Indicates the database could not be added.
	 */
	AccessError,

	/**
	 * Indicates the score could not be inserted into the database.
	 */
	InsertionError,

	/**
	 * Indicates the score did not make any toy-5 lists.
	 */
	NormalScore,

	/**
	 * Indicates the score made ONLY the personal top-5 list.
	 */
	PersonalHigh,

	/**
	 * Indicates the score made both the personal and global top-5 lists.
	 */
	GlobalHigh;
}
