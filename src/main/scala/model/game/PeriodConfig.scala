package model.game

import model.hospital.drg.{Diagnosis, DiagnosisId}

/** Configuration for a period - set by game master
  * The values are the percentage change relating to the last period.
  *
  * @param inflation           Inflation rate.
  * @param wageIncrease        Wage increase.
  * @param percentageOldPeople Percentage of patients equal or older than 61 arriving in this period
  * @param diagnoses           Map with all DRGs and belonging diagnoses that can be treated in a hospital (see [[model.hospital.drg.Diagnosis]].
  * @param prognosis           Prognosis for the next period (written by the game master)
  * @param otherHospitalClosed Indicates, if a hospital nearby has been closed. */
case class PeriodConfig(inflation: Double,
                        wageIncrease: Double,
                        percentageOldPeople: Double,
                        diagnoses: Map[DiagnosisId, Diagnosis],
                        prognosis: String,
                        otherHospitalClosed: Boolean,
                        receivedInterestFactor: Double,
                        overdraftInterestFactor: Double)
