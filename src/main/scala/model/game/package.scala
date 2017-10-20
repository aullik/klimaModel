package model

/** Includes classes which represent a game instance and its phases. They can be configured over the configuration
  * classes.
  *
  * A game admin can open a new game, which can be joined by teams. A game runs through several rounds, the so called
  * periods. A period isn't represented by an own instance, but rather by a game state and a period configuration.
  * Both the game instance and the period properties can be configured by the game admin.
  */
package object game
