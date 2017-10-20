package model.hospital.sectors.staticSectors.functionalSectors

import model.hospital.sectors.staticSectors.{StaticSector, StaticSectorDecisions, StaticSectorResults}
import model.hospital.staff.StaffStatic

/** Functional sector of a hospital
  *
  * description                 description of this sector (i.e. Laboratory)
  * staff                       the staff being employed in this sector (see [[StaffStatic]])
  * waterAndEnergyExpense       expenses for water and energy (fixed sum)
  * officeRequirementExpense    expenses or office requirement (fixed sum)
  * economicsRequirementExpense expenses for economical expenses (fixed sum)
  * maintenanceExpense          expenses for maintenance (fixed sum)
  */

trait FunctionalSector[SCD <: StaticSectorDecisions] extends StaticSector[SCD] {
  val medicalExpenses: Int
  val results: Option[FunctionalSectorResults]


  lazy val standByCost: Double = personalCosts + results.map(_.materialExpense).getOrElse(0d) + centralAmortization
}

/** The decisions for [[model.hospital.sectors.staticSectors.functionalSectors.FunctionalSector]]
  *
  * changeOfMaterialExpenses    percentage change of the material expenses in relation to the starting value
  */

trait FunctionalSectorDecisions extends StaticSectorDecisions {
  val changeOfMaterialExpenses: Double
}

trait FunctionalSectorResults extends StaticSectorResults {
  val serviceCosts: Double
  val depreciationInvestment: Double
}

case class CommonFunctionalSectorResults(periodResult: Double,
                                         materialExpense: Double,
                                         contributionMargin: Double,
                                         overdraftInterest: Double,
                                         serviceCosts: Double,
                                         depreciationInvestment: Double,
                                         economicsRequirementExpense: Double,
                                         receivedInterest: Double) extends FunctionalSectorResults

