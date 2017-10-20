package model.hospital.sectors.staticSectors.centralSectors

import model.hospital.sectors.staticSectors.{StaticSector, StaticSectorDecisions}

/** An abstraction for the central sectors of a hospital
  *
  */
abstract class CentralSector[SCD <: StaticSectorDecisions](
                                                            implicit private val decisionManifest: Manifest[SCD]
                                                          ) extends StaticSector[SCD] {

  protected def getServiceCosts(): Double = 0d


  lazy val serviceCosts: Double = getServiceCosts()

  lazy val sumStandbyCosts: Double = centralAmortization + results.map(_.materialExpense).getOrElse(0d) + serviceCosts


}






