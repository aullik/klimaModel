package model.common

sealed abstract case class WeekDay(index: Int, name: String) extends KlimaEnum

object WeekDay extends KlimaEnumCompanion[WeekDay] {

  object Monday extends WeekDay(0, "monday")

  object Tuesday extends WeekDay(1, "tuesday")

  object Wednesday extends WeekDay(2, "wednesday")

  object Thursday extends WeekDay(3, "thursday")

  object Friday extends WeekDay(4, "friday")

  object Saturday extends WeekDay(5, "saturday")

  object Sunday extends WeekDay(6, "sunday")

  override val all = List(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)

}
