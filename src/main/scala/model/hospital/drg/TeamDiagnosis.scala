package model.hospital.drg

import model.entity.{KlimaDecision, KlimaProduct, KlimaResult}

/** Specified DRG for a team
  *
  * @param diagnosis General DRG the rest of the parameters are associated with. Can be retrieved of the PeriodConfig
  *                  in the previous period (needed to calculate the waste of beds)
  */
case class TeamDiagnosis(diagnosis: DiagnosisId,
                         enabled: Boolean,
                         arrivalRateModifier: Double,
                         decisions: TeamDiagnosisDecisions,
                         results: Option[TeamDiagnosisResults] = None
                        ) extends KlimaProduct[TeamDiagnosisDecisions]


object TeamDiagnosisResults {

  def sumUp(results: List[TeamDiagnosisResults]) = TeamDiagnosisResults(
    numberPatients = results.map(_.numberPatients).sum,
    totalNormalArrivals = results.map(_.totalNormalArrivals).sum,
    acceptedPatients = results.map(_.acceptedPatients).sum,
    rejectedPatients = results.map(_.rejectedPatients).sum,
    emergencies = results.map(_.emergencies).sum / results.size,
    residenceTime = results.map(_.residenceTime).sum / results.size,
    lengthOfStayYoungAndOld = results.map(_.lengthOfStayYoungAndOld).sum / results.size,
    occupancyRate = results.map(_.occupancyRate).sum / results.size,
    campBedsUsed = results.map(_.campBedsUsed).sum,
    wastedBeds = results.map(_.wastedBeds).sum,
    equivalencePoints = results.map(_.equivalencePoints).sum / results.size,
    incomePerPatient = results.map(_.incomePerPatient).sum / results.size,
    grantedCaseRate = 0,

    individualNeeds = IndividualNeeds(
      medical = results.map(_.individualNeeds.medical).sum,
      laboratory = results.map(_.individualNeeds.laboratory).sum,
      surgery = results.map(_.individualNeeds.surgery).sum,
      other = results.map(_.individualNeeds.other).sum),

    commonNeeds = CommonNeeds(
      departments = results.map(_.commonNeeds.departments).sum,
      laboratory = results.map(_.commonNeeds.laboratory).sum,
      surgery = results.map(_.commonNeeds.surgery).sum,
      otherFS = results.map(_.commonNeeds.otherFS).sum,
      kitchen = results.map(_.commonNeeds.kitchen).sum,
      laundry = results.map(_.commonNeeds.laundry).sum,
      cleaning = results.map(_.commonNeeds.cleaning).sum,
      performance = results.map(_.commonNeeds.performance).sum),

    materialCosts = MaterialCosts(
      departments = results.map(_.materialCosts.departments).sum,
      laboratory = results.map(_.materialCosts.laboratory).sum,
      surgery = results.map(_.materialCosts.surgery).sum,
      otherFS = results.map(_.materialCosts.otherFS).sum,
      centralSectors = results.map(_.materialCosts.centralSectors).sum),

    personalCosts = PersonalCosts(
      laboratory = results.map(_.personalCosts.laboratory).sum,
      surgery = results.map(_.personalCosts.surgery).sum,
      otherFS = results.map(_.personalCosts.otherFS).sum,
      centralSectors = results.map(_.personalCosts.centralSectors).sum,
      depreciation = results.map(_.personalCosts.depreciation).sum,
      readiness = results.map(_.personalCosts.readiness).sum,
      other = results.map(_.personalCosts.other).sum))

}

/** The calculated Results for [[model.hospital.drg.TeamDiagnosis]]
  *
  * @param occupancyRate     the percentage of occupation
  * @param campBedsUsed      number of Patients that had no regular Bed
  * @param equivalencePoints equivalence points for this Diagnosis
  * @param numberPatients    Total number of patients who arrived in the DRG in the current period
  * @param rejectedPatients  Number of patients who have been rejected (i.e. due to high costs or full capacities)
  * @param wastedBeds        Number of beds who have been unused for at least 1 day
  * @param emergencies       Total number of emergencies in the current period
  * @param residenceTime     the mean time a patient stayed hospitalized
  * @param acceptedPatients  number of accepted patients and therefore in treatment
  * @param grantedCaseRate   case rate the team is granted for the DRG
  */
case class TeamDiagnosisResults(numberPatients: Int,
                                totalNormalArrivals: Int,
                                acceptedPatients: Int,
                                rejectedPatients: Int,
                                emergencies: Int,
                                lengthOfStayYoungAndOld: Double,
                                residenceTime: Double,
                                occupancyRate: Double,
                                campBedsUsed: Int,
                                wastedBeds: Int,
                                equivalencePoints: Int,
                                incomePerPatient: Double,
                                individualNeeds: IndividualNeeds,
                                commonNeeds: CommonNeeds,
                                materialCosts: MaterialCosts,
                                personalCosts: PersonalCosts,
                                grantedCaseRate: Int //TODO: @nicolas
                               ) extends KlimaResult {

  /** sum of all income. */
  lazy val totalIncome: Double = incomePerPatient * acceptedPatients

  /** sum of all costs. */
  lazy val totalCosts: Double = individualNeeds.total + commonNeeds.total + materialCosts.total + personalCosts.total

  /** difference between totalIncome and totalCosts. */
  lazy val profit: Double = totalIncome - totalCosts

}


case class IndividualNeeds(medical: Double,
                           laboratory: Double,
                           surgery: Double,
                           other: Double) {

  lazy val total: Double = medical + laboratory + surgery + other

}

case class CommonNeeds(departments: Double,
                       laboratory: Double,
                       surgery: Double,
                       otherFS: Double,
                       kitchen: Double,
                       laundry: Double,
                       cleaning: Double,
                       performance: Double) {

  lazy val total: Double = departments + laboratory + surgery + otherFS + kitchen + laundry + cleaning + performance

}

case class MaterialCosts(departments: Double,
                         laboratory: Double,
                         surgery: Double,
                         otherFS: Double,
                         centralSectors: Double) {

  lazy val total: Double = departments + laboratory + surgery + otherFS + centralSectors

}

case class PersonalCosts(laboratory: Double,
                         surgery: Double,
                         otherFS: Double,
                         centralSectors: Double,
                         depreciation: Double,
                         readiness: Double,
                         other: Double) {

  lazy val total: Double = laboratory + surgery + otherFS + centralSectors + depreciation + readiness + other

}

/** The calculated Results for [[model.hospital.drg.TeamDiagnosisDecisions]]
  *
  * @param requestedCaseRate           Case rate the team is requesting for the DRG
  * @param changeOfHospitalizationTime percentage change of the days a patient is being hospitalized in this DRG (in
  *                                    relation to the default time)
  */
case class TeamDiagnosisDecisions(enableNexPeriod: Boolean,
                                  requestedCaseRate: Int,
                                  changeOfHospitalizationTime: Double
                                 ) extends KlimaDecision