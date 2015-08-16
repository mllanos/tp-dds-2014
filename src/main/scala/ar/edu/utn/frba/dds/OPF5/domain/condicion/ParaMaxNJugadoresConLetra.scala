package ar.edu.utn.frba.dds.OPF5.domain.condicion

import scala.collection.mutable.ArrayBuffer
import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import javax.persistence._
import scala.beans.BeanProperty

@Entity
@DiscriminatorValue("paraMaxNJugadoresConLetra")
class ParaMaxNJugadoresConLetra(n: Int, m: String) extends Condicion {

  @Column(name = "cantidad_jugadores")
  var cantidadJugadores = n

  @Column(name = "primera_letra")
  var primeraLetra = m
  
  private def this() = this(0, null)

  override def apply(jugadores: ArrayBuffer[Jugador]): Boolean = {
    jugadores.filter(_.nombre.startsWith(primeraLetra)).size <= cantidadJugadores
  }
}