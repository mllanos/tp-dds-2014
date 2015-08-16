package ar.edu.utn.frba.dds.OPF5.domain.condicion

import scala.collection.mutable.ArrayBuffer
import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import javax.persistence._
import scala.beans.BeanProperty

@Entity
@DiscriminatorValue("paraJugadoresConApodoDeTamanio")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class ParaJugadoresConApodoDeTamanio(n: Int) extends Condicion {

  @Column(name = "tamanio_maximo")
  var tamanioMaximo = n
  
  private def this() = this(0)

  override def apply(jugadores: ArrayBuffer[Jugador]): Boolean = {
    jugadores.filter(_.apodo.size > tamanioMaximo).size == 0
  }
}