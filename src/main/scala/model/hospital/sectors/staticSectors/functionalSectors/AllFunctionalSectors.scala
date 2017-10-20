package model.hospital.sectors.staticSectors.functionalSectors

import model.entity.KlimaProduct
import model.hospital.sectors.staticSectors.StaticSectorResults

/** The functional sectors.
  *
  * @param laboratory see [[model.hospital.sectors.staticSectors.functionalSectors.Laboratory]]
  * @param surgery    see [[model.hospital.sectors.staticSectors.functionalSectors.Surgery]]
  * @param other      see [[model.hospital.sectors.staticSectors.functionalSectors.OtherFunctionalSector]] */
case class AllFunctionalSectors(laboratory: Laboratory,
                                surgery: Surgery,
                                other: OtherFunctionalSector
                               ) extends KlimaProduct.Default {


  lazy val overallResults: Option[StaticSectorResults] =
    try {
      Some(StaticSectorResults(List(laboratory, surgery, other).map(_.results.get)))
    } catch {
      case _: NoSuchElementException => None
    }

}
