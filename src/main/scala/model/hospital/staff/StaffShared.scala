package model.hospital.staff

/** Shared staff for all departments
  * see BasicStaff trait
  */
case class StaffShared(
                        wage: Int,
                        numberEmployees: Int,
                        numberEmployeesNextPeriod: Int,
                        overtimePremium: Double,
                        overtimeMaximum: Double,
                        decisions: StaffSharedDecision,
                        results: Option[StaffSharedResult] = None
                      ) extends BasicStaff[StaffSharedDecision, StaffSharedResult]

/** User decision for shared staff
  * see BasicStaff trait
  */

case class StaffSharedDecision(
                                numberEmployeesIn2Periods: Int
                              ) extends BasicStaffDecisions

/** Results for shared staff
  * see BasicStaff trait
  */

case class StaffSharedResult(
                            ) extends BasicStaffResults
