package model.hospital

import model.entity.{KlimaProduct, _}
import model.game.PeriodConfig
import model.hospital.drg.{DiagnosisId, TeamDiagnosis, TeamDiagnosisResults}
import model.hospital.sectors.departments.{AllDepartments, DepartmentType}
import model.hospital.sectors.staticSectors.centralSectors.AllCentralSectors
import model.hospital.sectors.staticSectors.functionalSectors.AllFunctionalSectors

/** A hospital with all its internals.
  *
  * It contains different sectors and departments. Attributes that are specific to a certain sector or department
  * are capsuled in the corresponding classes. The attributes in the hospital itself concern the hospital as a whole
  * or are comprehensive for several areas of the hospitals.
  * All the changes (decisions) made by the team are written directly into the current hospital, but can changed again
  * while the period is still running and the changes were not submitted.
  * After the transition from the current to the next period, some period results are written to the hospital. The rest
  * of the results can be derived from other values.
  *
  * @param id               a unique ID to identify the hospital
  * @param teamId           the ID of the [[model.users.Team]] this Hospital belongs to
  * @param changesSubmitted a flag indicating if the team submitted their decisions for the
  *                         next period
  * @param diagnoses        List of treated DRGs
  *                         TODO
  *
  *
  *
  */
case class Hospital(id: HospitalId = HospitalId(),
                    teamId: TeamId,
                    diagnoses: Map[DiagnosisId, TeamDiagnosis],
                    departments: AllDepartments,
                    centralSectors: AllCentralSectors,
                    functionalSectors: AllFunctionalSectors,
                    decisions: HospitalDecisions,
                    diagnosisEquipment: Boolean = false,
                    operationEquipment: Boolean = false,
                    traySystem: Boolean = false,
                    oralSurgeryEquipment: Boolean = false,
                    changesSubmitted: Boolean = false,
                    results: Option[HospitalResults] = None
                   ) extends KlimaProduct[HospitalDecisions] with Entity[HospitalId] {

  def overallDiagnosisResults(conf: PeriodConfig, depType: DepartmentType): TeamDiagnosisResults = {
    val results: List[TeamDiagnosisResults] = {
      val td = diagnoses.values.toStream.filter(td =>
        conf.diagnoses.get(td.diagnosis).exists(_.department == depType)
      )
      if (td.isEmpty) throw new IllegalArgumentException("Diagnosis are empty")
      td.flatMap(_.results).toList
    }
    TeamDiagnosisResults.sumUp(results)
  }
}

case class HospitalOverallResults(balance: Double)

/** The calculated Results for [[model.hospital.Hospital]]
  *
  * @param acquisitionAllianceSize the size of the approved AcquisitionAlliance.
  * @param totalQuality            the quality of the Hospital in this period.
  * @param administrationQuality   the administrative quality of the hospital (value from 0.0-99.0)
  * @param reputation              patients survey on quality aspects (from 0.0-99.0)
  *
  */
case class HospitalResults(acquisitionAllianceSize: Int,
                           totalQuality: Double,
                           administrationQuality: Double,
                           reputation: Double,
                           totalNumberPatients: Int,
                           balance: Double,
                           balanceOverallPeriods: Double
                          ) extends KlimaResult

/** The User decisions for the [[model.hospital.Hospital]]
  *
  * @param extendHospitalizationOverWeekends            a flag indicating, if the hospitalization of patients is extended
  *                                                     over the
  *                                                     weekend.
  * @param acceptPatientsOnWeekends                     a flag indicating if patients who arrive at saturday/sunday are
  *                                                     received
  * @param qualityAssuranceMeasures                     a flag indicating if measures should be taken to assure quality
  * @param sendPatientsToUniversityHospitalsWhenCostsHT a flag indicating if patients that are likely to cause high costs
  *                                                     are transferred to university hospital
  * @param emergencyCapacity                            the percentage of beds that are reserved for emergencies
  * @param publicRelationExpenses                       the amount of money to be spend for public relations
  * @param attendingDoctor                              a flag indicating if a private doctor should be employed
  *                                                     (oral surgeon)
  * @param acquireDiagnosisEquipment                    a flag indicating if diagnosis equipment should be purchased
  * @param acquireOperationEquipment                    a flag indicating if operation equipment should be purchased
  * @param acquireTraySystem                            a flag indicating if the tray system should be established
  * @param acquireOralSurgeryEquipment                  a flag indicating if oral surgery equipment should be purchased
  * @param socialWorker                                 flag indicating if a social worker is employed
  * @param setAcquisitionAlliances                      current team decisions on acquisition alliances. A list of teamIds.
  * @param overheadDrgS1                                drg for optional benefit one
  * @param overheadDrgS2                                drg for optional benefit two
  * @param addCostsResS1                                costs for optional benefit one
  * @param addCostsResS2                                costs for optional benefit two
  * @param addCostsFoodS1                               food costs for optional benefit one
  * @param addCostsFoodS2                               food costs for optional benefit two
  * @param specialHospital                              TODO
  */
case class HospitalDecisions(extendHospitalizationOverWeekends: Boolean,
                             acceptPatientsOnWeekends: Boolean,
                             qualityAssuranceMeasures: Boolean,
                             sendPatientsToUniversityHospitalsWhenCostsHT: Boolean,
                             emergencyCapacity: Double,
                             publicRelationExpenses: Double,
                             attendingDoctor: Boolean,
                             acquireDiagnosisEquipment: Boolean,
                             acquireOperationEquipment: Boolean,
                             acquireTraySystem: Boolean,
                             acquireOralSurgeryEquipment: Boolean,
                             socialWorker: Boolean,
                             socialWorkerCost: Int,
                             overheadDrgS1: Double,
                             overheadDrgS2: Double,
                             addCostsResS1: Double,
                             addCostsResS2: Double,
                             addCostsFoodS1: Double,
                             addCostsFoodS2: Double,
                             specialHospital: Boolean,
                             setAcquisitionAlliances: List[TeamId] = List.empty
                            ) extends KlimaDecision

