package model

/** An Acquisition Alliance. The matrix that defines which Hospitals form an Acquisition Alliance.
  *
  */
class AcquisitionAlliance private(val dimensions: Int, val matrix: Vector[Vector[Boolean]]) extends Product {


  /**
    * Get the size of the specific alliance for a team. A team will always be part of its own alliance
    * thus the minimum return value is 1.
    *
    * @param teamId the Id of the Team
    * @return the size of the Alliance associated with this Team
    */
  def getSizeOfAllianceFor(teamId: Int): Int = {
    checkTeamId(teamId)
    var n = 0
    var i = -1
    matrix(teamId).foreach(b => {
      i += 1
      if (i == teamId || (b && matrix(i)(teamId))) n += 1
    })
    n
  }

  /**
    * Get the specific alliance for a team. A team will always be part of its own alliance.
    *
    * @param teamId the Id of the Team
    * @return the Alliance associated with this Team
    * @throws IndexOutOfBoundsException when out of dimensions
    */
  def getAllianceFor(teamId: Int): Vector[Boolean] = {
    checkTeamId(teamId)
    val sup = matrix(teamId).iterator
    var i = -1
    Vector.fill(dimensions) {
      i += 1
      if (sup.next)
        matrix(i)(teamId)
      else
        i == teamId
    }
  }

  /**
    *
    * @param teamId       the Id of the Team that is going to change
    * @param setHospitals the row of the matrix to be replaced
    * @return a new instance of with AcquisitionAlliance with changed values
    * @throws IndexOutOfBoundsException when out of dimensions
    */
  def update(teamId: Int, setHospitals: Vector[Boolean]): AcquisitionAlliance = {
    checkTeamId(teamId)

    if (setHospitals.size != dimensions)
      throw new ArrayIndexOutOfBoundsException()

    _update(teamId, setHospitals)
  }

  /**
    *
    * @param teamId       the Id of the Team that are going to be swapped
    * @param setHospitals list of hospitals to set, Ids start with 0 and end with dimensions -1
    * @return a new instance of with AcquisitionAlliance with changed values
    * @throws IndexOutOfBoundsException when out of dimensions
    */
  def update(teamId: Int, setHospitals: Int*): AcquisitionAlliance = {
    checkTeamId(teamId)

    val sup = matrix(teamId).iterator
    val array = Array.fill(dimensions) {
      sup.next()
    }
    setHospitals.foreach(i => {
      if (i >= dimensions)
        throw new ArrayIndexOutOfBoundsException()
      array(i) = !array(i)
    })

    val vec: Vector[Boolean] = Vector(array: _*)
    _update(teamId, vec)
  }

  private def _update(teamId: Int, setHospitals: Vector[Boolean]): AcquisitionAlliance =
    new AcquisitionAlliance(dimensions, matrix.updated(teamId, setHospitals))

  private def checkTeamId(teamId: Int) {
    if (teamId >= dimensions || teamId < 0)
      throw new ArrayIndexOutOfBoundsException("teamId: " + teamId + "is greater then " + (dimensions - 1))
  }

  // Product methods

  override def productElement(n: Int): Any = n match {
    case 0 => dimensions
    case 1 => matrix
    case _ => throw new IndexOutOfBoundsException(n.toString)
  }


  override def productArity: Int = 2

  override def productPrefix: String = classOf[AcquisitionAlliance].getSimpleName

  //Equal and Hashcode

  override def canEqual(that: Any): Boolean = {
    that.isInstanceOf[AcquisitionAlliance]
  }

  override def equals(other: Any): Boolean = other match {
    case that: AcquisitionAlliance =>
      (that canEqual this) &&
        dimensions == that.dimensions &&
        matrix == that.matrix
    case _ => false
  }

  override def hashCode(): Int =
    runtime.ScalaRunTime._hashCode(this)
}

object AcquisitionAlliance {

  def apply(dimensions: Int): AcquisitionAlliance =
    new AcquisitionAlliance(dimensions, createBooleanMatrixOfDimensions(dimensions))

  def apply(matrix: Vector[Vector[Boolean]]): AcquisitionAlliance = {
    if (!checkMatrixDimensions(matrix))
      throw new IllegalArgumentException("Argument must be an M x M  Matrix")
    new AcquisitionAlliance(matrix.size, matrix)
  }

  def checkMatrixDimensions(matrix: Vector[Vector[Boolean]]): Boolean = {
    val n = matrix.size
    for (row <- matrix) {
      if (row.size != n)
        return false
    }
    true
  }

  private def createVectorOfSize[T](s: Int)(elem: => T): Vector[T] = {
    Vector.fill(s)(elem)
  }

  private def createBooleanMatrixOfDimensions(dimensions: Int): Vector[Vector[Boolean]] = {
    val vec = createVectorOfSize(dimensions)(false)
    createVectorOfSize(dimensions)(vec)
  }


}

