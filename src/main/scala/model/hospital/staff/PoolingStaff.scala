package model.hospital.staff

import model.entity.{KlimaDecision, KlimaProduct, KlimaResult}

/** Describes staff which includes pooling
  * numberEmployeesWithPooling        the number of employees in the same pool
  */
trait PoolingStaff[D <: PoolingStaffDecisions, R <: PoolingStaffResults] extends KlimaProduct[D] {
  val numberEmployeesWithPooling: Int
  val results: Option[R]
}

/** User decision for pooling staff
  * numberEmployeesPoolingFuture       the number of employees in the same pool in the next period
  */

trait PoolingStaffDecisions extends KlimaDecision {
  val numberEmployeesPoolingFuture: Int
}

/** Results for pooling staff
  *
  */
trait PoolingStaffResults extends KlimaResult {
}