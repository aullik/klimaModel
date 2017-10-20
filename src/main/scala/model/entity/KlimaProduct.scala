package model.entity

/**
  */


abstract class KlimaProduct[D <: KlimaDecision](
                                                 //implicit private val decisionManifest: Manifest[D]
                                               ) extends Product {

  protected val decisions: D

  def copyFunc(func: this.type => KlimaProduct[D]): this.type = func(this).asInstanceOf[this.type]

}


object KlimaProduct {

  abstract class Default extends KlimaProduct[KlimaDecision] {
    protected final val decisions: KlimaDecision = null

  }

  def decisionClass[D <: KlimaDecision](mf: Manifest[D]): Class[D] = mf.runtimeClass.asInstanceOf[Class[D]]

  type klimaClass = Class[_ <: KlimaProduct[_ <: KlimaDecision]]

  def toKlimaClass(clazz: Class[_]): klimaClass = clazz.asInstanceOf[klimaClass]

  def toKlimaClass[C](implicit manifest: Manifest[C]): klimaClass = manifest.runtimeClass.asInstanceOf[klimaClass]
}

