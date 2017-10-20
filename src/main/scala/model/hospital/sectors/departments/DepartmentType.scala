package model.hospital.sectors.departments

import model.common.{KlimaEnum, KlimaEnumCompanion}

sealed abstract case class DepartmentType(name: String) extends KlimaEnum

object DepartmentType extends KlimaEnumCompanion[DepartmentType] {

  object InternalMedicine extends DepartmentType("internalMedicine")

  object SurgicalWard extends DepartmentType("surgicalWard")

  object Gynecology extends DepartmentType("gynecology")

  override val all = List(InternalMedicine, SurgicalWard, Gynecology)

}
