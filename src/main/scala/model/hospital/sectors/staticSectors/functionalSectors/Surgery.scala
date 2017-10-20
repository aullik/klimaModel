package model.hospital.sectors.staticSectors.functionalSectors

import model.hospital.staff.StaffStatic

/** Surgery of a hospital (functional sector)
  *
  * @param staff                 the staff being employed in this sector (see [[StaffStatic]])
  * @param waterAndEnergyExpense expenses for water and energy (fixed sum)
  * @param managementExpenses    expenses or office requirement (fixed sum)
  * @param maintenanceExpense    expenses for maintenance (fixed sum)
  */
case class Surgery(staff: StaffStatic,
                   waterAndEnergyExpense: Int,
                   managementExpenses: Int,
                   maintenanceExpense: Int,
                   centralAmortization: Int,
                   medicalExpenses: Int,
                   decisions: SurgeryDecisions,
                   results: Option[CommonFunctionalSectorResults] = None
                  ) extends FunctionalSector[SurgeryDecisions] {
  override val description = Surgery.DESCRIPTION

}

object Surgery {
  val DESCRIPTION: String = "Surgery"
}

/** The User decisions for the [[model.hospital.sectors.staticSectors.functionalSectors.Surgery]]
  *
  * @param changeOfMaterialExpenses percental change of the material expenses in relation to the starting value
  */

case class SurgeryDecisions(changeOfMaterialExpenses: Double) extends FunctionalSectorDecisions
