package model.users

import model.entity._


/** A team participating in a game.
  *
  * Players can join a team. The team itself is assigned to a single game.
  * All players of a team have access to the hospital of the current period as well as the hospitals of previous periods
  * in order to look at previous decisions and results.
  * At the beginning of a game, the team has to agree on their objectives. Once set, they cannot be changed again.
  *
  * @param id     a unique ID to identify the team, shared with [[model.users.TeamData]].
  * @param name   name of the team.
  * @param gameId the id of the [[model.game.Game]] this team is playing in.
  *
  *
  */
case class Team(id: TeamId = TeamId(),
                name: String,
                gameId: GameId
               ) extends Entity[TeamId]

/** The DATA class for [[model.users.Team]].
  * <p>
  * The Data class is separated to decrease data loading.
  *
  * @param id                           a unique ID to identify the team, shared with [[model.users.Team]].
  * @param players                      a list of the IDs of all [[model.users.Player]]s in this Team.
  * @param hospitals                    List containing the id of the current hospital of the team and all hospitals of
  *                                     the previous periods.
  * @param objectives                   A list of the weighted objectives the team decides about at the beginning of.
  *                                     the game (see [[model.users.WeightedObjective]].
  * @param statisticalInformationBought An Integer indicating in which period the team has bought the information package.
  *                                     The default value "-1" is indicating that the package hasn't bought yet.
  */


//TODO: change data type of statisticalInformationBought to ChoiceFlag and then put it in hospital where it belongs
case class TeamData(id: TeamId,
                    players: List[PlayerId] = List(),
                    hospitals: List[HospitalId] = List(),
                    objectives: List[WeightedObjective] = List(),
                    objectivesResults: List[RatedObjective] = List(),
                    statisticalInformationBought: Int = -1) extends Entity[TeamId]


