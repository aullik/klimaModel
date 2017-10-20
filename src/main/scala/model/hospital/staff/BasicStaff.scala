package model.hospital.staff

import model.entity.{KlimaDecision, KlimaProduct, KlimaResult}

/** Basic staff information for all kinds of staff
  * wage                        wage of one staff member of the specific staff type
  * numberEmployees             number employees current period
  * numberEmployeesNextPeriod   number employees next period
  * overtimePremium             The additional amount given to employees for the overtime hours
  * overtimeMaximum             maximum hours of overtime this type of employees can work
  */
trait BasicStaff[D <: BasicStaffDecisions, R <: BasicStaffResults] extends KlimaProduct[D] {
  val wage: Int
  val numberEmployees: Int
  val numberEmployeesNextPeriod: Int
  val overtimePremium: Double
  val overtimeMaximum: Double
  val results: Option[R]
}

/** User decision for basic staff
  * numberEmployeesIn2Periods   number employees which will be employed in two periods in the future
  */

trait BasicStaffDecisions extends KlimaDecision {
  val numberEmployeesIn2Periods: Int
}

/** Results for basic staff
  */

trait BasicStaffResults extends KlimaResult {
}



