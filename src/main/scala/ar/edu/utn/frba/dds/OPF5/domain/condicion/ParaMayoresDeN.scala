package ar.edu.utn.frba.dds.OPF5.domain.condicion

import scala.collection.mutable.ArrayBuffer
import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import javax.persistence._
import scala.beans.BeanProperty

@Entity
@DiscriminatorValue("paraMayoresDeN")
class ParaMayoresDeN(n: Int) extends Condicion {

  @Column(name = "edad_maxima")
  var edadMaxima = n
  
  private def this() = this(0)
  
  override def apply(jugadores: ArrayBuffer[Jugador]): Boolean = {
    jugadores.forall(_.edad > edadMaxima)
  }
}