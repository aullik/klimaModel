package model.hospital.staff

/** Staff for departments with pooling
  * see PoolingStaff and OvertimeStaff trait
  */
case class StaffPooling(
                         numberEmployeesWithPooling: Int,
                         overtime: Double,
                         decisions: StaffPoolingDecision,
                         results: Option[StaffPoolingResult] = None
                       ) extends PoolingStaff[StaffPoolingDecision, StaffPoolingResult] with OvertimeStaff[StaffPoolingDecision, StaffPoolingResult]

/** User decision for pooled Staff
  * see PoolingStaff and OvertimeStaff trait
  */

case class StaffPoolingDecision(
                                 numberEmployeesPoolingFuture: Int,
                                 overtimeFuture: Double
                               ) extends PoolingStaffDecisions with OvertimeStaffDecisions

/** Results for pooled staff
  * see PoolingStaff and OvertimeStaff trait
  */

case class StaffPoolingResult(
                               personnelCosts: Double
                             ) extends PoolingStaffResults with OvertimeStaffResults

