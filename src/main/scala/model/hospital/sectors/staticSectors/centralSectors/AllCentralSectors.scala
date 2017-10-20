package model.hospital.sectors.staticSectors.centralSectors

import model.entity.KlimaProduct
import model.hospital.sectors.staticSectors.StaticSectorResults

/** The central sectors.
  *
  * @param kitchen  see [[model.hospital.sectors.staticSectors.centralSectors.Kitchen]]
  * @param cleaning see [[model.hospital.sectors.staticSectors.centralSectors.Cleaning]]
  * @param laundry  see [[model.hospital.sectors.staticSectors.centralSectors.Laundry]] */
case class AllCentralSectors(kitchen: Kitchen,
                             cleaning: Cleaning,
                             laundry: Laundry,
                             other: OtherCentralSectors) extends KlimaProduct.Default {


  lazy val overallResults: Option[StaticSectorResults] =
    try {
      Some(StaticSectorResults(List(kitchen, cleaning, other).map(_.results.get)))
    } catch {
      case _: NoSuchElementException => None
    }

}
