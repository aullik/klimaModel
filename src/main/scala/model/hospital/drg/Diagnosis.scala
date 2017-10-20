package model.hospital.drg

import model.entity.KlimaProduct
import model.hospital.sectors.departments.DepartmentType

/** A DRG and it's fixed characteristics (i.e. costs, case rate etc.) in a hospital
  * The parameters are set by the game master and apply to all hospitals
  *
  * @param description            name of the disease associated with the DRG
  * @param hospitalizationDays    standard duration of the hospitalization of a patient in this DRG
  * @param fixCost                fixed costs (for the hospital) of the treatment
  * @param age                    specifies the age restrictions of the DRG (i.e. applies only to patients younger than 61)
  * @param caseRate               Case rate of the DRG (Fallpauschale)
  * @param numberOfBeds           Number of available beds
  * @param costFactorLaboratory   individual cost factor for laboratory costs
  * @param costFactorSurgery      individual cost factor for surgery costs
  * @param costFactorOther        individual cost factor for other costs
  * @param totalArrivalsWorkDayPP arrivals on normal workdays for the whole period
  * @param totalArrivalsRestDayPP arrivals  at weekends and holidays for the whole period
  * @param emergencyRateWorkDay   percentage emergencies of the [[totalArrivalsWorkDayPP]]
  * @param emergencyRateRestDay   percentage emergencies of the [[totalArrivalsRestDayPP]]*/
case class Diagnosis(description: String,
                     department: DepartmentType,
                     hospitalizationDays: Int,
                     fixCost: Int,
                     age: AgeDependency,
                     caseRate: Int,
                     numberOfBeds: Int,
                     costFactorLaboratory: Double,
                     costFactorSurgery: Double,
                     costFactorOther: Double,
                     totalArrivalsWorkDayPP: Int,
                     totalArrivalsRestDayPP: Int,
                     emergencyRateWorkDay: Double,
                     emergencyRateRestDay: Double,
                     individualNeedsMultiplier: IndividualNeedsMultiplier,
                     commonNeedsMultiplier: CommonNeedsMultiplier,
                     materialCostsMultiplier: MaterialCostsMultiplier,
                     personalCostsMultiplier: PersonalCostsMultiplier
                    ) extends KlimaProduct.Default


case class IndividualNeedsMultiplier(medical: Double,
                                     laboratory: Double,
                                     surgery: Double,
                                     other: Double)

case class CommonNeedsMultiplier(departments: Double,
                                 laboratory: Double,
                                 surgery: Double,
                                 otherFS: Double,
                                 kitchen: Double,
                                 performance: Double)

case class MaterialCostsMultiplier(departments: Double,
                                   laboratory: Double,
                                   surgery: Double,
                                   otherFS: Double,
                                   centralSectors: Double)

case class PersonalCostsMultiplier(laboratory: Double,
                                   surgery: Double,
                                   otherFS: Double,
                                   centralSectors: Double,
                                   depreciation: Double,
                                   readiness: Double)
