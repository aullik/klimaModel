package model.hospital.staff

import model.common.{KlimaEnum, KlimaEnumCompanion}

/** A wrapper class holding the staff type
  *
  * @param name name of the staff type
  */
sealed abstract case class StaffType(name: String) extends KlimaEnum

object StaffType extends KlimaEnumCompanion[StaffType] {

  object Nurse extends StaffType("Nurse")

  object AssistantDoctor extends StaffType("AssistantDoctor")

  object WardNurse extends StaffType("WardNurse")

  object NursingStudent extends StaffType("NursingStudent")

  object Other extends StaffType("DefaultStaff")

  override val all = List(Nurse, AssistantDoctor, WardNurse, NursingStudent, Other)

}
