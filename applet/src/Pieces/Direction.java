package Pieces;

/**
 * The Direction class is a format for moves. It is used to provide communication between the KeyListener and the Game.
 */
public enum Direction
{
	/**
	 * Move for going down and left.
	 */
	SW,

	/**
	 * Move for going down.
	 */
	S,

	/**
	 * Move for going down and right.
	 */
	SE,

	/**
	 * Move for going left.
	 */
	W, 
	
	/**
	 * Move for not moving.
	 */
	SAME,

	/**
	 * Move for going right.
	 */
	E,

	/**
	 * Move for going up and left.
	 */
	NW,

	/**
	 * Move for going up.
	 */
	N,

	/**
	 * Move for going up and right.
	 */
	NE,

	/**
	 * Move for teleporting randomly.
	 */
	RANDOM,

	/**
	 * Move for teleporting safely.
	 */
	SAFE,

	/**
	 * Move for continuous Robot movement until the Player dies or all of the Robots are dead. The Player does not move during this time.
	 */
	WAIT;
}
