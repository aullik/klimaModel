package model.hospital.sectors.staticSectors.centralSectors

import model.hospital.sectors.staticSectors.{StaticSectorDecisions, StaticSectorResults}
import model.hospital.staff.StaffStatic

/** Kitchen of the hospital (central sector)
  *
  * @param staff                 staff being employed in this sector (see [[model.hospital.staff.StaffStatic]])
  * @param waterAndEnergyExpense the amount of money spend for water and energy resources in this sector
  * @param managementExpenses    the amount of money spend on office expenses
  * @param maintenanceExpense    the amount of money spend on maintenance expenses
  */
case class Kitchen(staff: StaffStatic,
                   waterAndEnergyExpense: Int,
                   managementExpenses: Int,
                   maintenanceExpense: Int,
                   groceryExpensePerDay: Double,
                   centralAmortization: Int,
                   decisions: KitchenDecisions,
                   results: Option[KitchenResults] = None
                  ) extends CentralSector[KitchenDecisions] {
  override val description: String = Kitchen.DESCRIPTION

  override protected def getServiceCosts(): Double = results.map(_.groceryCosts).getOrElse(0d)
}


/** The calculated Results for [[model.hospital.sectors.staticSectors.centralSectors.Kitchen]]
  *
  * @param materialExpense        the amount of money spend for material in this sector
  * @param depreciationTraySystem the amount payed since the the tray system has been bought
  * @param foodQuality            quality of food (0-99)
  *
  */
case class KitchenResults(groceryCosts: Double,
                          depreciationTraySystem: Double,
                          foodQuality: Double,
                          materialExpense: Double,
                          contributionMargin: Double,
                          periodResult: Double,
                          overdraftInterest: Double,
                          economicsRequirementExpense: Double,
                          receivedInterest: Double
                         ) extends StaticSectorResults


object Kitchen {
  val DESCRIPTION: String = "Kitchen"
}


/** The User decisions for the [[model.hospital.sectors.staticSectors.centralSectors.Kitchen]]
  *
  * @param groceryExpensePerDay expense for grocery per day per patient (set by player)
  * @param centralAmortization  TODO
  */
case class KitchenDecisions(groceryExpensePerDay: Double,
                            centralAmortization: Int
                           ) extends StaticSectorDecisions