package model.game

import model.entity.TeamId
import model.hospital.drg.AgeDependency.{OldOnly, YoungAndOld, YoungOnly}
import model.hospital.drg._
import model.hospital.sectors.departments.DepartmentType.{Gynecology, InternalMedicine, SurgicalWard}
import model.hospital.sectors.departments.{AllDepartments, Department, DepartmentDecision}
import model.hospital.sectors.staticSectors.centralSectors._
import model.hospital.sectors.staticSectors.functionalSectors._
import model.hospital.staff.StaffType.{AssistantDoctor, Nurse, NursingStudent, WardNurse}
import model.hospital.staff._
import model.hospital.{Hospital, HospitalDecisions}
import util.FunctionalHelper.ofTuple

/** Complete default configuration for a game. */
object DefaultConfig {

  lazy val period = PeriodConfig(
    inflation = 0.02,
    wageIncrease = 0.03,
    percentageOldPeople = 0.6,
    diagnoses = diagnoses,
    prognosis = "No special events this period",
    otherHospitalClosed = false,
    receivedInterestFactor = 0.02,
    overdraftInterestFactor = 0.1
  )

  lazy val game = GameConfig(hospital = hospital,
    periodDurationInWeeks = 13,
    costDiagnosisEquipment = 1500000,
    costOperationEquipment = 33000,
    costTraySystem = 400000,
    costOralSurgeryEquipment = 700000,
    costInfoPackage = 35000,
    depreciationTraySystem = 20000,
    depreciationDiagnosisEquipment = 75000,
    depreciationOralSurgeryEquipment = 35000,
    depreciationSurgeryEquipment = 16500,
    maxPeriods = 10,
    departmentQualityWeight = 0.7,
    administrationQualityWeight = 0.3
  )

  lazy val hospital: Hospital = {
    val dia =
      diagnoses.map(ofTuple((id, dia) => {
        val enabled: Boolean = id.name != "drg14"
        (id, TeamDiagnosis(
          diagnosis = id,
          enabled = enabled,
          arrivalRateModifier = 1,
          decisions = TeamDiagnosisDecisions(
            enableNexPeriod = enabled,
            requestedCaseRate = dia.caseRate,
            changeOfHospitalizationTime = 0)
        ))
      }))

    Hospital(
      teamId = TeamId(),
      departments = departments.all,
      centralSectors = centralSectors.all,
      functionalSectors = functionalSectors.all,
      decisions = hospitalDecisions,
      diagnoses = dia
    )
  }

  lazy val hospitalDecisions = HospitalDecisions(
    extendHospitalizationOverWeekends = false,
    acceptPatientsOnWeekends = false,
    qualityAssuranceMeasures = false,
    sendPatientsToUniversityHospitalsWhenCostsHT = false,
    emergencyCapacity = 0,
    publicRelationExpenses = 0,
    attendingDoctor = false,
    acquireDiagnosisEquipment = false,
    acquireOperationEquipment = false,
    acquireTraySystem = false,
    acquireOralSurgeryEquipment = false,
    socialWorker = false,
    socialWorkerCost = 15234,
    overheadDrgS1 = 0.358,
    overheadDrgS2 = 0.215,
    addCostsResS1 = 0.3,
    addCostsResS2 = 0.2,
    addCostsFoodS1 = 0.0255,
    addCostsFoodS2 = 0.017,
    specialHospital = false)

  lazy val diagnoses: Map[DiagnosisId, Diagnosis] = {
    Map(
      (DiagnosisId("drg1"), Diagnosis(
        description = "tumor",
        department = InternalMedicine,
        hospitalizationDays = 13,
        fixCost = 0, age = YoungAndOld,
        caseRate = 4800,
        numberOfBeds = 30,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 20,
        totalArrivalsRestDayPP = 1,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 370,
          laboratory = 43,
          surgery = 0,
          other = 196.76),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 180,
          laboratory = 2.64,
          surgery = 0,
          otherFS = 113.76,
          kitchen = 106.10,
          performance = 1005),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 260,
          laboratory = 43,
          surgery = 0,
          otherFS = 60,
          centralSectors = 55.23),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 224,
          surgery = 0,
          otherFS = 249.49,
          centralSectors = 604.74,
          depreciation = 396.90,
          readiness = 3250
        ))
      ),

      (DiagnosisId("drg2"), Diagnosis(
        description = "diabetes",
        department = InternalMedicine,
        hospitalizationDays = 23,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 5950,
        numberOfBeds = 30,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 20,
        totalArrivalsRestDayPP = 5,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 493.2,
          laboratory = 75,
          surgery = 0,
          other = 397),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 198,
          laboratory = 22.91,
          surgery = 0,
          otherFS = 121.66,
          kitchen = 130.23,
          performance = 939),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 523,
          laboratory = 75.98,
          surgery = 0,
          otherFS = 57.70,
          centralSectors = 60.98),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 336,
          surgery = 0,
          otherFS = 263.98,
          centralSectors = 640.5,
          depreciation = 424.21,
          readiness = 4778.8))
      ),


      (DiagnosisId("drg3"), Diagnosis(
        description = "circulatory disease young age",
        department = InternalMedicine,
        hospitalizationDays = 12,
        fixCost = 0,
        age = YoungOnly,
        caseRate = 4725,
        numberOfBeds = 30,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 368.72,
          laboratory = 43,
          surgery = 0,
          other = 161),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 166.5,
          laboratory = 20.82,
          surgery = 0,
          otherFS = 110.08,
          kitchen = 118.02,
          performance = 1475),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 230.43,
          laboratory = 45.78,
          surgery = 0,
          otherFS = 50,
          centralSectors = 59.24),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 204,
          surgery = 0,
          otherFS = 238,
          centralSectors = 582.34,
          depreciation = 395.9,
          readiness = 2998))
      ),


      (DiagnosisId("drg4"), Diagnosis(
        description = "circulatory disease old age",
        department = InternalMedicine,
        hospitalizationDays = 19,
        fixCost = 0,
        age = OldOnly,
        caseRate = 5380,
        numberOfBeds = 30,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 498.92,
          laboratory = 48.5,
          surgery = 0,
          other = 245),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 178.42,
          laboratory = 21.24,
          surgery = 0,
          otherFS = 117.95,
          kitchen = 158.51,
          performance = 1255),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 395,
          laboratory = 49.1,
          surgery = 0,
          otherFS = 57,
          centralSectors = 57),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 228,
          surgery = 0,
          otherFS = 219.48,
          centralSectors = 626,
          depreciation = 430.41,
          readiness = 4223.3))
      ),


      (DiagnosisId("drg5"), Diagnosis(
        description = "alimentary organ young age",
        department = InternalMedicine,
        hospitalizationDays = 10,
        fixCost = 0,
        age = YoungOnly,
        caseRate = 4100,
        numberOfBeds = 30,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 344.5,
          laboratory = 45.92,
          surgery = 0,
          other = 149),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 165.35,
          laboratory = 20.68,
          surgery = 0,
          otherFS = 109.01,
          kitchen = 78.52,
          performance = 1050),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 200,
          laboratory = 47.38,
          surgery = 0,
          otherFS = 52,
          centralSectors = 58),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 217,
          surgery = 0,
          otherFS = 238.75,
          centralSectors = 579.28,
          depreciation = 385.5,
          readiness = 2839))
      ),


      (DiagnosisId("drg6"), Diagnosis(
        description = "alimentary organ old age",
        department = InternalMedicine,
        hospitalizationDays = 16,
        fixCost = 0,
        age = OldOnly,
        caseRate = 5600,
        numberOfBeds = 30,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 450.75,
          laboratory = 59,
          surgery = 0,
          other = 290),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 175.22,
          laboratory = 21.93,
          surgery = 0,
          otherFS = 115.86,
          kitchen = 129.06,
          performance = 1200),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 323.07,
          laboratory = 63.78,
          surgery = 0,
          otherFS = 57.56,
          centralSectors = 58.09),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 376,
          surgery = 0,
          otherFS = 225.9,
          centralSectors = 615,
          depreciation = 400,
          readiness = 3648))
      ),


      (DiagnosisId("drg7"), Diagnosis(
        description = "circulatory disease",
        department = SurgicalWard,
        hospitalizationDays = 15,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 6550,
        numberOfBeds = 25,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 674.15,
          laboratory = 63,
          surgery = 278.93,
          other = 380),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 150,
          laboratory = 22.15,
          surgery = 116,
          otherFS = 116.35,
          kitchen = 123,
          performance = 1645),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 105,
          laboratory = 85,
          surgery = 72.43,
          otherFS = 58.29,
          centralSectors = 134),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 370,
          surgery = 370,
          otherFS = 370,
          centralSectors = 586,
          depreciation = 402.24,
          readiness = 4414))
      ),


      (DiagnosisId("drg8"), Diagnosis(
        description = "tumor",
        department = SurgicalWard,
        hospitalizationDays = 15,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 6140,
        numberOfBeds = 25,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 612,
          laboratory = 54,
          surgery = 225.02,
          other = 322),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 151.49,
          laboratory = 21.72,
          surgery = 115.2,
          otherFS = 114.07,
          kitchen = 116.82,
          performance = 1776),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 351,
          laboratory = 55.55,
          surgery = 85,
          otherFS = 56,
          centralSectors = 123.21),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 269,
          surgery = 408,
          otherFS = 249.8,
          centralSectors = 485,
          depreciation = 405,
          readiness = 4200))
      ),


      (DiagnosisId("drg9"), Diagnosis(
        description = "alimentary organ young age",
        department = SurgicalWard,
        hospitalizationDays = 11,
        fixCost = 0,
        age = YoungOnly,
        caseRate = 5900,
        numberOfBeds = 25,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 380.86,
          laboratory = 91.5,
          surgery = 319.6,
          other = 32),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 148.5,
          laboratory = 26.15,
          surgery = 113.5,
          otherFS = 109.21,
          kitchen = 88.52,
          performance = 1312),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 208.63,
          laboratory = 89.81,
          surgery = 640,
          otherFS = 54.73,
          centralSectors = 115),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 445,
          surgery = 339,
          otherFS = 236.5,
          centralSectors = 503.5,
          depreciation = 388,
          readiness = 3883.52))
      ),


      (DiagnosisId("drg10"), Diagnosis(
        description = "alimentary organ old age",
        department = SurgicalWard,
        hospitalizationDays = 14,
        fixCost = 0,
        age = OldOnly,
        caseRate = 6750,
        numberOfBeds = 25,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 842.96,
          laboratory = 58,
          surgery = 209,
          other = 440),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 151.76,
          laboratory = 86.22,
          surgery = 114.5,
          otherFS = 110.75,
          kitchen = 111.57,
          performance = 1890),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 330,
          laboratory = 25,
          surgery = 75,
          otherFS = 60,
          centralSectors = 118.98),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 342,
          surgery = 406,
          otherFS = 242.98,
          centralSectors = 550,
          depreciation = 393,
          readiness = 4219.20))
      ),


      (DiagnosisId("drg11"), Diagnosis(
        description = "appendix disease",
        department = SurgicalWard,
        hospitalizationDays = 10,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 4880,
        numberOfBeds = 25,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 299.89,
          laboratory = 25,
          surgery = 128,
          other = 151.5),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 151.3,
          laboratory = 20.97,
          surgery = 113.4,
          otherFS = 113,
          kitchen = 81.9,
          performance = 1103),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 231.14,
          laboratory = 25.54,
          surgery = 77.33,
          otherFS = 59,
          centralSectors = 113),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 115,
          surgery = 386.43,
          otherFS = 301,
          centralSectors = 570,
          depreciation = 393.21,
          readiness = 3631))
      ),


      (DiagnosisId("drg12"), Diagnosis(
        description = "trauma",
        department = SurgicalWard,
        hospitalizationDays = 10,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 4930,
        numberOfBeds = 25,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 226.16,
          laboratory = 15.31,
          surgery = 94.5,
          other = 162),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 149.3,
          laboratory = 20.66,
          surgery = 113.2,
          otherFS = 109,
          kitchen = 80.16,
          performance = 975.14),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 222,
          laboratory = 16.15,
          surgery = 70.44,
          otherFS = 53,
          centralSectors = 117),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 81.19,
          surgery = 380,
          otherFS = 241,
          centralSectors = 570,
          depreciation = 387.35,
          readiness = 3297))
      ),


      (DiagnosisId("drg13"), Diagnosis(
        description = "disease upper air passages",
        department = SurgicalWard,
        hospitalizationDays = 5,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 3650,
        numberOfBeds = 25,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 48.5,
          laboratory = 4.3,
          surgery = 22.07,
          other = 20.73),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 140.7,
          laboratory = 19.77,
          surgery = 113,
          otherFS = 102.98,
          kitchen = 33,
          performance = 518.41),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 85,
          laboratory = 4.47,
          surgery = 75,
          otherFS = 51.55,
          centralSectors = 111.48),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 20.35,
          surgery = 398.28,
          otherFS = 377,
          centralSectors = 544,
          depreciation = 366,
          readiness = 2439))
      ),


      (DiagnosisId("drg14"), Diagnosis(
        description = "oral surgery",
        department = SurgicalWard,
        hospitalizationDays = 4,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 4600,
        numberOfBeds = 25,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        // TODO missing constants
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 0,
          laboratory = 0,
          surgery = 0,
          other = 0),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 0,
          laboratory = 0,
          surgery = 0,
          otherFS = 0,
          kitchen = 0,
          performance = 0),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 0,
          laboratory = 0,
          surgery = 0,
          otherFS = 0,
          centralSectors = 0),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 0,
          surgery = 0,
          otherFS = 0,
          centralSectors = 0,
          depreciation = 0,
          readiness = 0))
      ),

      (DiagnosisId("drg15"), Diagnosis(
        description = "delivery without surgery",
        department = Gynecology,
        hospitalizationDays = 5,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 2750,
        numberOfBeds = 27,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 80.22,
          laboratory = 4.85,
          surgery = 0,
          other = 241.91),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 124.2,
          laboratory = 19.82,
          surgery = 0,
          otherFS = 109.85,
          kitchen = 36.24,
          performance = 387),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 119.70,
          laboratory = 5.43,
          surgery = 0,
          otherFS = 53.05,
          centralSectors = 114),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 25,
          surgery = 0,
          otherFS = 232,
          centralSectors = 571,
          depreciation = 381.61,
          readiness = 1984))
      ),

      (DiagnosisId("drg16"), Diagnosis(
        description = "delivery with surgery",
        department = Gynecology,
        hospitalizationDays = 6,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 3920,
        numberOfBeds = 27,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 212.82,
          laboratory = 3.5,
          surgery = 0,
          other = 16.21),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 123.25,
          laboratory = 24.95,
          surgery = 0,
          otherFS = 136,
          kitchen = 46,
          performance = 745),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 2619.72,
          laboratory = 4.79,
          surgery = 74.58,
          otherFS = 52.76,
          centralSectors = 108),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 20,
          surgery = 395,
          otherFS = 229,
          centralSectors = 558,
          depreciation = 372,
          readiness = 3466))
      ),

      (DiagnosisId("drg17"), Diagnosis(
        description = "fem. sexual organs",
        department = Gynecology,
        hospitalizationDays = 12,
        fixCost = 0,
        age = YoungAndOld,
        caseRate = 4800,
        numberOfBeds = 27,
        costFactorLaboratory = 1.0,
        costFactorSurgery = 1.0,
        costFactorOther = 1.0,
        totalArrivalsWorkDayPP = 100,
        totalArrivalsRestDayPP = 10,
        emergencyRateWorkDay = 0.2,
        emergencyRateRestDay = 1,
        individualNeedsMultiplier = IndividualNeedsMultiplier(
          medical = 275,
          laboratory = 23.65,
          surgery = 0,
          other = 108.55),
        commonNeedsMultiplier = CommonNeedsMultiplier(
          departments = 129.3,
          laboratory = 0,
          surgery = 0,
          otherFS = 110.54,
          kitchen = 93.89,
          performance = 959),
        materialCostsMultiplier = MaterialCostsMultiplier(
          departments = 336.39,
          laboratory = 23,
          surgery = 75.40,
          otherFS = 55.34,
          centralSectors = 55.34),
        personalCostsMultiplier = PersonalCostsMultiplier(
          laboratory = 111,
          surgery = 400.23,
          otherFS = 237.12,
          centralSectors = 576,
          depreciation = 392,
          readiness = 3585))
      ))
  }

  object departments {

    lazy val internalMedicine = Department(
      departmentType = InternalMedicine,
      enabled = true,
      staff = internalMedicineStaff,
      materialExpenseBase = 160000,
      depreciation = 247336,
      decisions = internalMedicineDecisions)

    lazy val internalMedicineStaff = Map(
      AssistantDoctor -> StaffPooling(
        numberEmployeesWithPooling = 6,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 6,
          overtimeFuture = 0.0)),
      WardNurse -> StaffPooling(
        numberEmployeesWithPooling = 4,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 4,
          overtimeFuture = 0.0)),
      Nurse -> StaffPooling(
        numberEmployeesWithPooling = 19,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 19,
          overtimeFuture = 0.0)),
      NursingStudent -> StaffPooling(
        numberEmployeesWithPooling = 8,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 8,
          overtimeFuture = 0.0)))

    lazy val internalMedicineDecisions = DepartmentDecision(
      researchExpense = 0,
      lengthOfStayYoungChange = 0,
      lengthOfStayOldChange = 0,
      changeOfServiceCost = 0,
      changeOfMaterialExpenses = 0)

    lazy val surgicalWard = Department(
      departmentType = SurgicalWard,
      enabled = true,
      staff = surgicalWardStaff,
      materialExpenseBase = 172000,
      depreciation = 247336,
      decisions = surgicalWardDecisions)

    lazy val surgicalWardStaff = Map(
      AssistantDoctor -> StaffPooling(
        numberEmployeesWithPooling = 6,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 6,
          overtimeFuture = 0.0)),
      WardNurse -> StaffPooling(
        numberEmployeesWithPooling = 4,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 4,
          overtimeFuture = 0.0)),
      Nurse -> StaffPooling(
        numberEmployeesWithPooling = 19,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 19,
          overtimeFuture = 0.0)),
      NursingStudent -> StaffPooling(
        numberEmployeesWithPooling = 8,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 8,
          overtimeFuture = 0.0)))

    lazy val surgicalWardDecisions = DepartmentDecision(
      researchExpense = 0,
      lengthOfStayYoungChange = 0,
      lengthOfStayOldChange = 0,
      changeOfServiceCost = 0,
      changeOfMaterialExpenses = 0)

    lazy val gynecology = Department(
      departmentType = Gynecology,
      enabled = true,
      staff = gynecologyStaff,
      materialExpenseBase = 73000,
      depreciation = 247336,
      decisions = gynecologyDecisions)

    lazy val gynecologyStaff = Map(
      AssistantDoctor -> StaffPooling(
        numberEmployeesWithPooling = 6,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 6,
          overtimeFuture = 0.0)),
      WardNurse -> StaffPooling(
        numberEmployeesWithPooling = 4,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 4,
          overtimeFuture = 0.0)),
      Nurse -> StaffPooling(
        numberEmployeesWithPooling = 18,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 18,
          overtimeFuture = 0.0)),
      NursingStudent -> StaffPooling(
        numberEmployeesWithPooling = 8,
        overtime = 0.0,
        decisions = StaffPoolingDecision(
          numberEmployeesPoolingFuture = 8,
          overtimeFuture = 0.0)))

    lazy val gynecologyDecisions = DepartmentDecision(
      researchExpense = 0,
      lengthOfStayYoungChange = 0,
      lengthOfStayOldChange = 0,
      changeOfServiceCost = 0,
      changeOfMaterialExpenses = 0
    )


    lazy val sharedStaff = Map(
      AssistantDoctor -> StaffShared(
        wage = 23907,
        numberEmployees = 18,
        numberEmployeesNextPeriod = 18,
        overtimePremium = 0.4,
        overtimeMaximum = 15.0,
        decisions = StaffSharedDecision(
          numberEmployeesIn2Periods = 18)),
      WardNurse -> StaffShared(
        wage = 15693,
        numberEmployees = 12,
        numberEmployeesNextPeriod = 12,
        overtimePremium = 0.4,
        overtimeMaximum = 15.0,
        decisions = StaffSharedDecision(
          numberEmployeesIn2Periods = 12)),
      Nurse -> StaffShared(
        wage = 13776,
        numberEmployees = 56,
        numberEmployeesNextPeriod = 56,
        overtimePremium = 0.4,
        overtimeMaximum = 15.0,
        decisions = StaffSharedDecision(
          numberEmployeesIn2Periods = 56)),
      NursingStudent -> StaffShared(
        wage = 4161,
        numberEmployees = 24,
        numberEmployeesNextPeriod = 24,
        overtimePremium = 0.4,
        overtimeMaximum = 15.0,
        decisions = StaffSharedDecision(
          numberEmployeesIn2Periods = 24)))

    lazy val all = AllDepartments(
      internalMedicine = internalMedicine,
      surgicalWard = surgicalWard,
      gynecology = gynecology,
      staff = sharedStaff)

  }


  object functionalSectors {

    lazy val laboratory = Laboratory(
      staff = laboratoryStaff,
      waterAndEnergyExpense = 63000,
      managementExpenses = 25987,
      maintenanceExpense = 5711,
      centralAmortization = 52434,
      medicalExpenses = 48291,
      decisions = laboratoryDecisions)

    lazy val laboratoryStaff = StaffStatic(
      wage = 11478,
      numberEmployees = 37,
      //numberEmployeesWithPooling = 37,
      numberEmployeesNextPeriod = 37,
      overtimePremium = 0.4,
      overtimeMaximum = 15.0,
      overtime = 0.0,
      decisions = StaffStaticDecision(
        //numberEmployeesPoolingFuture = 0,
        //numberEmployeesFuture = 0,
        //overtime = 0.0,
        numberEmployeesIn2Periods = 0,
        overtimeFuture = 0.0))

    lazy val laboratoryDecisions = LaboratoryDecisions(
      changeOfMaterialExpenses = 0)

    lazy val surgery = Surgery(
      staff = surgeryStaff,
      waterAndEnergyExpense = 120000,
      managementExpenses = 31606,
      maintenanceExpense = 6946,
      centralAmortization = 63772,
      medicalExpenses = 179627,
      decisions = surgeryDecisions)

    lazy val surgeryStaff = StaffStatic(
      wage = 13425,
      numberEmployees = 45,
      //numberEmployeesWithPooling = 45,
      numberEmployeesNextPeriod = 45,
      overtimePremium = 0.4,
      overtimeMaximum = 15.0,
      overtime = 0,
      decisions = StaffStaticDecision(
        //numberEmployeesPoolingFuture = 0,
        //numberEmployeesFuture = 0,
        //overtime = 0.0,
        numberEmployeesIn2Periods = 0,
        overtimeFuture = 0.0))

    lazy val surgeryDecisions = SurgeryDecisions(
      changeOfMaterialExpenses = 0)

    lazy val other = OtherFunctionalSector(
      staff = otherStaff,
      waterAndEnergyExpense = 50000,
      managementExpenses = 37225,
      maintenanceExpense = 8181,
      centralAmortization = 75109,
      medicalExpenses = 255591,
      decisions = otherDecisions)

    lazy val otherStaff = StaffStatic(
      wage = 11040,
      numberEmployees = 53,
      //numberEmployeesWithPooling = 53,
      numberEmployeesNextPeriod = 53,
      overtimePremium = 0.4,
      overtimeMaximum = 15.0,
      overtime = 0.0,
      decisions = StaffStaticDecision(
        //numberEmployeesPoolingFuture = 0,
        //numberEmployeesFuture = 0,
        //overtime = 0.0,
        numberEmployeesIn2Periods = 0,
        overtimeFuture = 0.0))

    lazy val otherDecisions = OtherFunctionalSectorDecisions(
      changeOfMaterialExpenses = 0)

    lazy val all = AllFunctionalSectors(
      laboratory = laboratory,
      surgery = surgery,
      other = other)

  }

  object centralSectors {

    lazy val cleaning = Cleaning(
      staff = cleaningStaff,
      waterAndEnergyExpense = 9492,
      managementExpenses = 8892,
      maintenanceExpense = 39680,
      costPerPatient = 1.49,
      costPerPatientOutsourced = 1.35,
      liquidationCost = 51000,
      centralAmortization = 37417,
      decisions = cleaningDecisions)

    lazy val cleaningStaff = StaffStatic(
      wage = 15513,
      numberEmployees = 28,
      //numberEmployeesWithPooling = 28,
      numberEmployeesNextPeriod = 28,
      overtimePremium = 0.4,
      overtimeMaximum = 15.0,
      overtime = 0.0,
      decisions = StaffStaticDecision(
        //numberEmployeesPoolingFuture = 0,
        //numberEmployeesFuture = 0,
        //overtime = 0.0,
        numberEmployeesIn2Periods = 0,
        overtimeFuture = 0.0))

    lazy val cleaningDecisions = CleaningDecisions(
      outsourced = false
    )

    lazy val kitchen = Kitchen(
      staff = kitchenStaff,
      waterAndEnergyExpense = 28445,
      managementExpenses = 11115,
      maintenanceExpense = 49600,
      groceryExpensePerDay = 8.5,
      centralAmortization = 37825,
      decisions = kitchenDecisions)

    lazy val kitchenStaff = StaffStatic(
      wage = 13425,
      numberEmployees = 35,
      //numberEmployeesWithPooling = 35,
      numberEmployeesNextPeriod = 35,
      overtimePremium = 0.4,
      overtimeMaximum = 15.0,
      overtime = 0.0,
      decisions = StaffStaticDecision(
        //numberEmployeesPoolingFuture = 0,
        //numberEmployeesFuture = 0,
        //overtime = 0.0,
        numberEmployeesIn2Periods = 0,
        overtimeFuture = 0.0))

    lazy val kitchenDecisions = KitchenDecisions(
      groceryExpensePerDay = 8.5,
      centralAmortization = 37825)

    lazy val laundry = Laundry(
      staff = laundryStaff,
      waterAndEnergyExpense = 1,
      managementExpenses = 2223,
      maintenanceExpense = 9920,
      liquidationCost = 162000,
      costPerKg = 1.89,
      costPerKgOutsourced = 1.72,
      kgLaundryPerPatient = 2.6,
      centralAmortization = 18552,
      decisions = laundryDecisions)

    lazy val laundryStaff = StaffStatic(
      wage = 13935,
      numberEmployees = 7,
      //numberEmployeesWithPooling = 7,
      numberEmployeesNextPeriod = 7,
      overtimePremium = 0.4,
      overtimeMaximum = 15.0,
      overtime = 0.0,
      decisions = StaffStaticDecision(
        //numberEmployeesPoolingFuture = 0,
        //numberEmployeesFuture = 0,
        //overtime = 0.0,
        numberEmployeesIn2Periods = 0,
        overtimeFuture = 0.0))

    lazy val laundryDecisions = LaundryDecisions(
      outsourced = false,
      kilogramClothsPerDay = 2.6)

    lazy val other = OtherCentralSectors(
      staff = otherStaff,
      waterAndEnergyExpense = 37927,
      managementExpenses = 9527,
      maintenanceExpense = 42514,
      centralAmortization = 37840,
      decisions = otherDecisions)

    lazy val otherStaff = StaffStatic(
      wage = 13476,
      numberEmployees = 31,
      //numberEmployeesWithPooling = 31,
      numberEmployeesNextPeriod = 31,
      overtimePremium = 0.4,
      overtimeMaximum = 15.0,
      overtime = 0.0,
      decisions = StaffStaticDecision(
        //numberEmployeesPoolingFuture = 0,
        //numberEmployeesFuture = 0,
        //overtime = 0.0,
        numberEmployeesIn2Periods = 0,
        overtimeFuture = 0.0))

    lazy val otherDecisions = OtherCentralSectorsDecisions()

    lazy val all = AllCentralSectors(
      kitchen = kitchen,
      cleaning = cleaning,
      laundry = laundry,
      other = other)

  }

}
