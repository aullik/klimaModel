package model.users

import Objective._

/** A wrapper class for a team's objective and it's weighting
  * A team needs to choose 3 objectives and assign each a weighting (the sum of all weighting must be 10)
  *
  * @param objective one of 10 objectives a team can choose from
  * @param weight    the weighting for the objective (a number between 1 and 8)
  *
  */
case class WeightedObjective(objective: Objective, weight: Int)

/** A wrapper class for a team's objective and it's rating
  *
  * @param objective one of 10 objectives a team can choose from
  * @param rating    the rating for the objective
  */
case class RatedObjective(objective: Objective, rating: Double)

sealed abstract class Objective(private val objectiveID: Int)

case object HighOverPlus extends Objective(highOverPlus)

case object LowCostPerCase extends Objective(lowCostPerCase)

case object ExactCostCoverage extends Objective(exactCostCoverage)

case object HighOccupancyLevel extends Objective(highOccupancyLevel)

case object LowLengthOfStay extends Objective(lowLengthOfStay)

case object LowGradeOfDismissals extends Objective(lowGradeOfDismissals)

case object LowWasteOfBeds extends Objective(lowWasteOfBeds)

case object ReasonableEmergencyCapacity extends Objective(reasonableEmergencyCapacity)

case object HighSpecialisationLevel extends Objective(highSpecialisationLevel)

case object HighTotalQuality extends Objective(highTotalQuality)

object Objective {
  val highOverPlus = 0
  val lowCostPerCase = 1
  val exactCostCoverage = 2
  val highOccupancyLevel = 3
  val lowLengthOfStay = 4
  val lowGradeOfDismissals = 5
  val lowWasteOfBeds = 6
  val reasonableEmergencyCapacity = 7
  val highSpecialisationLevel = 8
  val highTotalQuality = 9
}
