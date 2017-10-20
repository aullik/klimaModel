package model.hospital.sectors.staticSectors.centralSectors

import model.hospital.sectors.staticSectors.{StaticSectorDecisions, StaticSectorResults}

/** A central sector which can be outsourced to an external provider
  * Outsourcing cannot be undone
  */
trait CentralSectorOutsourceable[SCD <: CentralSectorOutsourceableDecisions] extends CentralSector[SCD] {
  val results: Option[CentralSectorOutsourceableResults]
  val decisions: SCD
  val liquidationCost: Int
}

trait CentralSectorOutsourceableDecisions extends StaticSectorDecisions {
  /** flag indicating, if the service has been outsourced
    */
  val outsourced: Boolean
}

trait CentralSectorOutsourceableResults extends StaticSectorResults {
  val costsForeignProcurement: Double
  val costsIfOutsourcedThisPeriod: Int
}

