package model.hospital.sectors.staticSectors.centralSectors

import model.hospital.sectors.staticSectors.{StaticSectorDecisions, StaticSectorResults}
import model.hospital.staff.StaffStatic

/** A central sector of a hospital - contains all costs/staff that don't belong to a specific central sector
  *
  * @param staff                 List of staff being employed in this sector (see [[StaffStatic]])
  * @param waterAndEnergyExpense the amount of money spend for water and energy resources in this sector
  * @param managementExpenses    the amount of money spend on office expenses
  * @param maintenanceExpense    the amount of money spend on maintenance expenses
  */
case class OtherCentralSectors(staff: StaffStatic,
                               waterAndEnergyExpense: Int,
                               managementExpenses: Int,
                               maintenanceExpense: Int,
                               centralAmortization: Int,
                               decisions: OtherCentralSectorsDecisions,
                               results: Option[OtherCentralSectorsResults] = None
                              ) extends CentralSector[OtherCentralSectorsDecisions] {
  override val description: String = OtherCentralSectors.DESCRIPTION

}

case class OtherCentralSectorsResults(
                                       periodResult: Double,
                                       materialExpense: Double,
                                       contributionMargin: Double,
                                       overdraftInterest: Double,
                                       economicsRequirementExpense: Double,
                                       proceedsZBGCredit: Double,
                                       receivedInterest: Double
                                     ) extends StaticSectorResults

object OtherCentralSectors {
  val DESCRIPTION: String = "Other"

}

/** The User decisions for the [[model.hospital.sectors.staticSectors.centralSectors.OtherCentralSectorsDecisions]]
  *
  */

case class OtherCentralSectorsDecisions() extends StaticSectorDecisions