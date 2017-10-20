package model.hospital.sectors.staticSectors

import model.entity.{KlimaDecision, KlimaProduct, KlimaResult}
import model.hospital.staff.StaffStatic

/** An abstraction for the sectors which are always contained in a hospital.
  * These sectors are: all central and functional sectors
  */
abstract class StaticSector[SCD <: StaticSectorDecisions](
                                                           implicit private val decisionManifest: Manifest[SCD]
                                                         ) extends KlimaProduct[SCD] {
  val staff: StaffStatic
  val description: String
  val centralAmortization: Int
  val waterAndEnergyExpense: Int
  val managementExpenses: Int
  val maintenanceExpense: Int
  val decisions: SCD
  val results: Option[StaticSectorResults]

  lazy val personalCosts: Double =
    staff.wage * staff.numberEmployees

}

trait StaticSectorDecisions extends KlimaDecision {

}

trait StaticSectorResults extends KlimaResult {
  val periodResult: Double
  val economicsRequirementExpense: Double
  val materialExpense: Double
  val contributionMargin: Double
  val overdraftInterest: Double
  val receivedInterest: Double
}

case class CommonStaticSectorResults(periodResult: Double,
                                     materialExpense: Double,
                                     contributionMargin: Double,
                                     economicsRequirementExpense: Double,
                                     overdraftInterest: Double,
                                     receivedInterest: Double) extends StaticSectorResults

object StaticSectorResults {

  def apply(results: List[StaticSectorResults]) = CommonStaticSectorResults(
    materialExpense = results.map(_.materialExpense).sum,
    contributionMargin = results.map(_.contributionMargin).sum,
    economicsRequirementExpense = results.map(_.economicsRequirementExpense).sum,
    periodResult = results.map(_.periodResult).sum,
    overdraftInterest = results.map(_.overdraftInterest).sum,
    receivedInterest = results.map(_.receivedInterest).sum)

}
