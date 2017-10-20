package model.entity

import org.bson.types.ObjectId

/**
  */
sealed trait EntityId extends Product {
  val asString: String

}

sealed trait IDFactory[ID <: EntityId] {
  def apply(): ID = ConcreteId().asInstanceOf[ID]

  def ofStringID(id: String): ID = ConcreteId(id).asInstanceOf[ID]

}

sealed trait PlayerId extends EntityId

object PlayerId extends IDFactory[PlayerId]

sealed trait HospitalId extends EntityId

object HospitalId extends IDFactory[HospitalId]

sealed trait GameId extends EntityId

object GameId extends IDFactory[GameId]

sealed trait TeamId extends EntityId

object TeamId extends IDFactory[TeamId]


private case class ConcreteId(asString: String = EntityId.generateId()) extends PlayerId with HospitalId with GameId with TeamId {
  if (asString == null)
    throw new IllegalArgumentException("ID is null")

  override def toString: String = asString
}


object EntityId {
  private[entity] def generateId(): String = new ObjectId().toString

}
