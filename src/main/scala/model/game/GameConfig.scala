package model.game

import model.hospital.Hospital

/** Overall configuration for the game - these parameters do not change during the game
  * set by game master.
  *
  * This configuration contains 1 hospital object defining which sectors, DRGs and departments are available for the
  * teams. This means, if this hospital does not contain the department Gynecology, no team can activate it in their
  * hospital, making it unavailable for the whole game..
  *
  * @param hospital                         basis hospital (see above).
  * @param periodDurationInWeeks            duration of one period in weeks.
  * @param costDiagnosisEquipment           cost of the diagnosis which can be purchased by a team (who would've thought..)
  * @param costOperationEquipment           cost of the operation equipment which can be purchased by a team.
  * @param costTraySystem                   cost of the tray system which can be purchased by a team.
  * @param costOralSurgeryEquipment         cost of the oral surgery equipment which can be purchased by a team.
  * @param costInfoPackage                  cost of the infopackage
  * @param depreciationTraySystem           depreciation of the tray system
  * @param depreciationDiagnosisEquipment   depreciation of the diagnosis equipment
  * @param depreciationOralSurgeryEquipment depreciation of the oral surgery equipment
  * @param depreciationSurgeryEquipment     depreciation of the surgery equipment
  * @param maxPeriods                       maximum number of periods which are played in this game
  * @param departmentQualityWeight          percentage of the department quality in the total quality
  * @param administrationQualityWeight      percentage of the administration quality in the total quality
  * */
case class GameConfig(hospital: Hospital,
                      periodDurationInWeeks: Int,
                      costDiagnosisEquipment: Int,
                      costOperationEquipment: Int,
                      costTraySystem: Int,
                      costOralSurgeryEquipment: Int,
                      costInfoPackage: Int,
                      depreciationTraySystem: Int,
                      depreciationDiagnosisEquipment: Int,
                      depreciationOralSurgeryEquipment: Int,
                      depreciationSurgeryEquipment: Int,
                      maxPeriods: Int,
                      departmentQualityWeight: Double,
                      administrationQualityWeight: Double)

