package model.security

/** Authorization Groups. */
sealed abstract case class Group(isAdmin: Boolean = false, isLeader: Boolean = false)

object Group {

  object Admin extends Group(isAdmin = true, isLeader = true)

  object Leader extends Group(isLeader = true)

  object User extends Group

}

