package model.hospital.sectors.departments

import model.entity.KlimaProduct
import model.hospital.staff.{StaffShared, StaffType}

/** The Departments.
  *
  * @param internalMedicine the [[model.hospital.sectors.departments.Department]] internalMedicine
  * @param surgicalWard     the [[model.hospital.sectors.departments.Department]] surgicalWard
  * @param gynecology       the [[model.hospital.sectors.departments.Department]] gynecology
  * @param  staff           the [[model.hospital.staff.StaffShared]] for all Departments
  */
case class AllDepartments(internalMedicine: Department,
                          surgicalWard: Department,
                          gynecology: Department,
                          staff: Map[StaffType, StaffShared]
                         ) extends KlimaProduct.Default {


  /** Results for overall Departments (internalMedicine, surgicalWard and gynecology)
    *
    */
  lazy val overallResults: Option[DepartmentResults] =
    try {
      val results = List(internalMedicine, surgicalWard, gynecology).map(_.results.get)
      Some(DepartmentResults(
        contributionMargin = results.map(_.contributionMargin).sum, // TODO missing special services
        patientsSurvey = results.map(_.patientsSurvey).sum / results.size,
        medicalQuality = results.map(_.medicalQuality).sum / results.size,
        equipmentDepreciation = results.map(_.equipmentDepreciation).sum,
        materialExpense = results.map(_.materialExpense).sum,
        occupancyRateEmergencies = results.map(_.occupancyRateEmergencies).sum / results.size))
    } catch {
      case _: NoSuchElementException => None
    }

  def getDepartment(departmentType: DepartmentType): Department = {
    departmentType match {
      case DepartmentType.InternalMedicine => internalMedicine
      case DepartmentType.SurgicalWard => surgicalWard
      case DepartmentType.Gynecology => gynecology
    }
  }

}
