package model.hospital.sectors.departments

import model.entity.{KlimaDecision, KlimaResult}
import model.hospital.drg.TeamDiagnosisResults
import model.hospital.sectors.Sector
import model.hospital.staff.{StaffPooling, StaffType}

/** A department of a hospital and it's internals
  *
  * A hospital can have 3 different departments (Internal Medicine, Surgery and Gynecology)
  * Each department can treat a specific set of DRGs (i.e. DRG 1-6 are assigned to Internal Medicine), has it's own staff,
  * expenses etc. Teams can modify their departments by reducing the treated DRGs or by fully closing a department
  *
  * @param departmentType      type of the department (i.e. "Internal Medicine")
  * @param staff               List of staff the department is employing (see [[model.hospital.staff.StaffPooling]])
  * @param materialExpenseBase amount of money spent for materials (sockel value)
  * @param depreciation        amortization
  *
  */

case class Department(departmentType: DepartmentType,
                      enabled: Boolean,
                      staff: Map[StaffType, StaffPooling],
                      materialExpenseBase: Int,
                      depreciation: Int,
                      decisions: DepartmentDecision,
                      results: Option[DepartmentResults] = None
                     ) extends Sector[DepartmentDecision] {


  lazy val overallResults: Option[TeamDiagnosisResults] = {
    try {
      // TODO move this
      /*val results = diagnoses.map(_._2.results.get)
      val occupancyRate = Try(results.map(_.acceptedPatients).sum / results.map(_.numberOfPatients).sum).getOrElse(0)
      Some(TeamDiagnosisResults(
        occupancyRate = occupancyRate * 100,
        campBedsUsed = results.map(_.campBedsUsed).sum,
        equivalencePoints = results.map(_.equivalencePoints).sum,
        numberOfPatients = results.map(_.numberOfPatients).sum,
        rejections = results.map(_.rejections).sum,
        wastedBeds = results.map(_.wastedBeds).sum,
        emergencies = results.map(_.emergencies).sum,
        profitPerPatient = Try(results.map(_.emergencies).sum / results.size).getOrElse(0).toDouble,
        medicalRequirements = results.map(_.medicalRequirements).sum,
        residenceTime = Try(results.map(_.residenceTime).sum / results.size).getOrElse(0),
        acceptedPatients = results.map(_.acceptedPatients).sum))*/
      None
    } catch {
      case _: NoSuchElementException => None
    }
  }

  lazy val totalPersonnelCosts: Double = {
    var totalCost = 0.0
    staff.values.foreach((pooling) => totalCost += pooling.results.get.personnelCosts)
    totalCost
  }
}

/** The calculated Results for [[model.hospital.sectors.departments.Department]]
  *
  * @param contributionMargin       contribution margin is the selling price per minus the variable cost.
  * @param patientsSurvey           result of the patients survey (value from 0.0 to 99.0)
  * @param medicalQuality           medical quality of the department (value from 0.0 to 99.0)
  * @param materialExpense          amount of money spent on material (base costs + dynamic costs)
  * @param occupancyRateEmergencies the percentage of occupation of emergencies
  */
case class DepartmentResults(contributionMargin: Double,
                             patientsSurvey: Double,
                             medicalQuality: Double,
                             equipmentDepreciation: Double,
                             materialExpense: Int,
                             occupancyRateEmergencies: Double
                            ) extends KlimaResult


/** The User decisions for the [[model.hospital.sectors.departments.Department]]
  *
  * @param researchExpense          amount of money the hospital invests in research for this department
  * @param lengthOfStayYoungChange  percentage change of the length of stay of young patients
  * @param lengthOfStayOldChange    percentage change of the length of stay of old patients
  * @param changeOfServiceCost      percentage change of the service cost in relation to the starting value
  * @param changeOfMaterialExpenses percentage change of the material expenses in relation to the starting value
  */
case class DepartmentDecision(researchExpense: Int,
                              lengthOfStayYoungChange: Double,
                              lengthOfStayOldChange: Double,
                              changeOfServiceCost: Double,
                              changeOfMaterialExpenses: Double
                             ) extends KlimaDecision
