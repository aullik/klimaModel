package model.game

import model.entity.{Entity, GameId, PlayerId, TeamId}


/** A game holding all participating teams and playable periods
  * created by the game master.
  *
  * @param id    the ID  shared with [[model.game.GameData]].
  * @param name  the name of the game.
  * @param owner the ID of the game-owner, see [[model.users.Player]].
  *
  */
case class Game(id: GameId = GameId(),
                name: String,
                owner: PlayerId) extends Entity[GameId]


/** The DATA class for [[model.game.Game]].
  * <p>
  * The Data class is separated to decrease data loading.
  *
  * @param id               the ID shared with [[model.game.Game]].
  * @param period           number of the period which is currently played (starting at 0).
  * @param isFinished       flag if the game leader finished the game
  * @param teams            the IDs of all teams participating in this game, see [[model.users.Team]].
  * @param config           Overall configuration for the game (set by game master).
  * @param nextPeriodConfig the config of the period to come  will be updated each period
  * @param periodConfig     configurations of all periods which have been or are currently played.
  */
case class GameData(id: GameId,
                    period: Int = 0,
                    isFinished: Boolean = false,
                    teams: List[TeamId] = List(),
                    config: GameConfig = DefaultConfig.game,
                    nextPeriodConfig: PeriodConfig = DefaultConfig.period,
                    periodConfig: List[PeriodConfig] = List(DefaultConfig.period)) extends Entity[GameId]
