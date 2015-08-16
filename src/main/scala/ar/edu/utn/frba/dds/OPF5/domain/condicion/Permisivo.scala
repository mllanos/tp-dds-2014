package ar.edu.utn.frba.dds.OPF5.domain.condicion

import scala.collection.mutable.ArrayBuffer
import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import javax.persistence._

@Entity
@DiscriminatorValue("permisivo")
class Permisivo extends Condicion {
  override def apply(jugadores: ArrayBuffer[Jugador]): Boolean = true
}