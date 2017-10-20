package model.hospital.sectors.staticSectors.functionalSectors

import model.hospital.staff.StaffStatic

/** Laboratory of a hospital (functional sector)
  *
  * @param staff                 the staff being employed in this sector (see [[StaffStatic]])
  * @param waterAndEnergyExpense expenses for water and energy (fixed sum)
  * @param managementExpenses    expenses or office requirement (fixed sum)
  * @param maintenanceExpense    expenses for maintenance (fixed sum)
  *
  */
case class Laboratory(staff: StaffStatic,
                      waterAndEnergyExpense: Int,
                      managementExpenses: Int,
                      maintenanceExpense: Int,
                      centralAmortization: Int,
                      medicalExpenses: Int,
                      decisions: LaboratoryDecisions,
                      results: Option[CommonFunctionalSectorResults] = None
                     ) extends FunctionalSector[LaboratoryDecisions] {
  override val description = Laboratory.DESCRIPTION

}

object Laboratory {
  val DESCRIPTION: String = "Laboratory"
}


/** The User decisions for the [[model.hospital.sectors.staticSectors.functionalSectors.Laboratory]]
  *
  * @param changeOfMaterialExpenses percentage change of the material expenses in relation to the starting value
  */


case class LaboratoryDecisions(changeOfMaterialExpenses: Double) extends FunctionalSectorDecisions