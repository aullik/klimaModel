package model.hospital.staff

import model.entity.{KlimaDecision, KlimaProduct, KlimaResult}

/** Describes overtime information for staff
  * overtime               the amount of overtime the employees should work (percental change in relation to
  * normal working hours
  */
trait OvertimeStaff[D <: OvertimeStaffDecisions, R <: OvertimeStaffResults] extends KlimaProduct[D] {
  val overtime: Double
  val results: Option[R]
}


/** User decision for overtime staff
  * overtimeFuture          the amount of overtime for the employee in the next period
  */

trait OvertimeStaffDecisions extends KlimaDecision {
  val overtimeFuture: Double
}

/**
  * results for overtime staff
  * personalCosts               total personnel cost
  *
  */

trait OvertimeStaffResults extends KlimaResult {
  val personnelCosts: Double
}