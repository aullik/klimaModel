package model.common

trait KlimaEnum {
  val name: String
}

trait KlimaEnumCompanion[E <: KlimaEnum] {

  val all: List[E]

  lazy val number: Int = all.size

  def parse(name: String): E = {
    all.find(_.name == name).getOrElse(
      throw new IllegalArgumentException(s"can't parse $name to enum")
    )
  }

}
