package model.hospital.staff

/** Staff for static sectors
  * see BasicStaff and OvertimeStaff tait
  */
case class StaffStatic(wage: Int,
                       numberEmployees: Int,
                       numberEmployeesNextPeriod: Int,
                       decisions: StaffStaticDecision,
                       overtimePremium: Double,
                       overtimeMaximum: Double,
                       overtime: Double,
                       results: Option[StaffStaticResult] = None
                      ) extends BasicStaff[StaffStaticDecision, StaffStaticResult] with OvertimeStaff[StaffStaticDecision, StaffStaticResult]


/** User decision for static staff
  * see BasicStaff and OvertimeStaff tait
  */
case class StaffStaticDecision(
                                numberEmployeesIn2Periods: Int,
                                overtimeFuture: Double
                              ) extends BasicStaffDecisions with OvertimeStaffDecisions

/** Results for static staff
  * see BasicStaff and OvertimeStaff tait
  */

case class StaffStaticResult(
                              personnelCosts: Double
                            ) extends BasicStaffResults with OvertimeStaffResults