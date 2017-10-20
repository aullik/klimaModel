package model.hospital.sectors.staticSectors.centralSectors

import model.hospital.staff.StaffStatic

/** Laundry of a hospital (central sector)
  *
  * @param staff                 List of staff being employed in this sector (see [[StaffStatic]])
  * @param waterAndEnergyExpense the amount of money spend for water and energy resources in this sector
  * @param managementExpenses    the amount of money spend on office expenses
  * @param maintenanceExpense    the amount of money spend on maintenance expenses
  * @param costPerKgOutsourced   the cost per kg when outsourced
  * @param kgLaundryPerPatient   kg Laundry per patient per day(set by player)
  *
  */
case class Laundry(staff: StaffStatic,
                   waterAndEnergyExpense: Int,
                   managementExpenses: Int,
                   maintenanceExpense: Int,
                   liquidationCost: Int,
                   costPerKg: Double,
                   costPerKgOutsourced: Double,
                   kgLaundryPerPatient: Double,
                   centralAmortization: Int,
                   decisions: LaundryDecisions,
                   results: Option[LaundryResults] = None
                  ) extends CentralSectorOutsourceable[LaundryDecisions] {
  override val description: String = Laundry.DESCRIPTION

  override protected def getServiceCosts(): Double = results.map(_.economicsRequirementExpense).getOrElse(0d)


}

case class LaundryResults(
                           periodResult: Double,
                           materialExpense: Double,
                           contributionMargin: Double,
                           overdraftInterest: Double,
                           costsForeignProcurement: Double,
                           economicsRequirementExpense: Double,
                           costsIfOutsourcedThisPeriod: Int,
                           receivedInterest: Double
                         ) extends CentralSectorOutsourceableResults


object Laundry {
  val DESCRIPTION: String = "Laundry"
}


/** The set decisions for [[model.hospital.sectors.staticSectors.centralSectors.Laundry]]
  *
  * @param outsourced a flag indicating if the service has been outsourced to an external provider
  */
case class LaundryDecisions(outsourced: Boolean,
                            kilogramClothsPerDay: Double
                           ) extends CentralSectorOutsourceableDecisions {
}