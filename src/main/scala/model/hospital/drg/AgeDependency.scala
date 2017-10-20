package model.hospital.drg

/** Specifies the age dependency of a DRG */
sealed abstract case class AgeDependency(appliesToYoungPeople: Boolean = true,
                                         appliesToOldPeople: Boolean = true) {

  private def getPercentageOldModifier(percentageOldPeople: Double): Double = {
    if (!appliesToOldPeople)
    //only young
      0d
    else if (!appliesToYoungPeople)
    //only old
      1d
    else if (percentageOldPeople == 0)
    //both, needed to ensure calculations
      Double.MinPositiveValue
    else
    //both
      percentageOldPeople
  }

  private def getLengthOfStay(modifier: Double, lengthOfStayYoungAndOld: Double, default: Double): Double = {
    if (lengthOfStayYoungAndOld == 0 && modifier > 0)
      default
    else
      modifier * lengthOfStayYoungAndOld
  }

  /**
    *
    * @param percentageOldPeople     percentage of old people
    * @param lengthOfStayYoungAndOld the length of stay for both young and old
    * @return The length of stay for old people. If lengthOfStayYoungAndOld is 0, return default if this applies to old people
    */
  def getLengthOfStayOld(percentageOldPeople: Double, lengthOfStayYoungAndOld: Double, default: Double): Double = {
    getLengthOfStay(getPercentageOldModifier(percentageOldPeople), lengthOfStayYoungAndOld, default)
  }

  /**
    *
    * @param percentageOldPeople     percentage of old people
    * @param lengthOfStayYoungAndOld the length of stay for both young and old
    * @return The length of stay for young people. If lengthOfStayYoungAndOld is 0, return 1 if this applies to young people
    */
  def getLengthOfStayYoung(percentageOldPeople: Double, lengthOfStayYoungAndOld: Double, default: Double): Double = {
    getLengthOfStay(1 - getPercentageOldModifier(percentageOldPeople), lengthOfStayYoungAndOld, default)
  }


}

object AgeDependency {

  /** applies only to young people (< 61 years) */
  object YoungOnly extends AgeDependency(appliesToOldPeople = false)

  /** applies only to old people (>= 61) */
  object OldOnly extends AgeDependency(appliesToYoungPeople = false)

  /** no age restrictions */
  object YoungAndOld extends AgeDependency

}
