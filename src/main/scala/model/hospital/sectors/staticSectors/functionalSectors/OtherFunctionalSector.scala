package model.hospital.sectors.staticSectors.functionalSectors

import model.hospital.staff.StaffStatic

/** OtherFunctionalSector of a hospital (functional sector)
  *
  * @param staff                 the staff being employed in this sector (see [[StaffStatic]])
  * @param waterAndEnergyExpense expenses for water and energy (fixed sum)
  * @param managementExpenses    expenses or office requirement (fixed sum)
  * @param maintenanceExpense    expenses for maintenances (fixed sum)
  */
case class OtherFunctionalSector(staff: StaffStatic,
                                 waterAndEnergyExpense: Int,
                                 managementExpenses: Int,
                                 maintenanceExpense: Int,
                                 centralAmortization: Int,
                                 medicalExpenses: Int,
                                 decisions: OtherFunctionalSectorDecisions,
                                 results: Option[CommonFunctionalSectorResults] = None
                                ) extends FunctionalSector[OtherFunctionalSectorDecisions] {
  override val description = OtherFunctionalSector.DESCRIPTION

}

object OtherFunctionalSector {
  val DESCRIPTION: String = "OtherFunctionalSector"
}


/** The User decisions for the [[model.hospital.sectors.staticSectors.functionalSectors.OtherFunctionalSector]]
  *
  * @param changeOfMaterialExpenses percental change of the material expenses in relation to the starting value
  */

case class OtherFunctionalSectorDecisions(changeOfMaterialExpenses: Double) extends FunctionalSectorDecisions