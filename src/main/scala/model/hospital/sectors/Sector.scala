package model.hospital.sectors

import model.entity.{KlimaDecision, KlimaProduct}

/**
  *
  */
trait Sector[D <: KlimaDecision] extends KlimaProduct[D] {

  def depreciation: Int
}
