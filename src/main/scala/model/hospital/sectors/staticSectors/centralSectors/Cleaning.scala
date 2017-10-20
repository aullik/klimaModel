package model.hospital.sectors.staticSectors.centralSectors

import model.hospital.staff.StaffStatic

/** A central sector of a hospital providing a cleaning service
  *
  * @param staff                       List of staff being employed in this sector (see [[StaffStatic]])
  * @param waterAndEnergyExpense       the amount of money spend for water and energy resources in this sector
  * @param economicsRequirementExpense the amount of money spend on economic expenses
  * @param managementExpenses          the amount of money spend on office expenses
  * @param maintenanceExpense          the amount of money spend on maintenance expenses
  */
case class Cleaning(staff: StaffStatic,
                    waterAndEnergyExpense: Int,
                    managementExpenses: Int,
                    maintenanceExpense: Int,
                    costPerPatient: Double,
                    costPerPatientOutsourced: Double,
                    liquidationCost: Int,
                    centralAmortization: Int,
                    decisions: CleaningDecisions,
                    results: Option[CleaningResults] = None
                   ) extends CentralSectorOutsourceable[CleaningDecisions] {

  override val description: String = Cleaning.DESCRIPTION

}

/** The calculated Results for [[model.hospital.sectors.staticSectors.centralSectors.Kitchen]]
  *
  * @param materialExpense        the amount of money spend for material in this sector
  * @param costDecreasePercentage the percentage of the cost decrease when outsourced
  * @param cleaningQuality        the quality of the Cleaning
  */

case class CleaningResults(materialExpense: Double,
                           contributionMargin: Double,
                           periodResult: Double,
                           costDecreasePercentage: Double, //TODO: not sure this belongs here
                           cleaningQuality: Double,
                           overdraftInterest: Double,
                           costsForeignProcurement: Double,
                           economicsRequirementExpense: Double,
                           costsIfOutsourcedThisPeriod: Int,
                           receivedInterest: Double
                          ) extends CentralSectorOutsourceableResults

object Cleaning {
  val DESCRIPTION: String = "Cleaning"

}

/** The set decisions for [[model.hospital.sectors.staticSectors.centralSectors.Cleaning]]
  *
  * @param outsourced a flag indicating if the service has been outsourced to an external provider
  */
case class CleaningDecisions(outsourced: Boolean
                            ) extends CentralSectorOutsourceableDecisions