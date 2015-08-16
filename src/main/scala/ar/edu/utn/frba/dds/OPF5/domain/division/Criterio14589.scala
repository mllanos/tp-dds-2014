package ar.edu.utn.frba.dds.OPF5.domain.division

import ar.edu.utn.frba.dds.OPF5.domain.{Jugador, Partido}
import org.uqbar.commons.utils.Observable
import collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._

@Observable
class Criterio14589 extends CriterioDivision {

  override def dividir(partido: Partido) {
    val equipoA = new ArrayBuffer[Jugador] += (
      partido.jugadores(0),
      partido.jugadores(3),
      partido.jugadores(4),
      partido.jugadores(7),
      partido.jugadores(8))

    val equipoB = new ArrayBuffer[Jugador] += (
      partido.jugadores(1),
      partido.jugadores(2),
      partido.jugadores(5),
      partido.jugadores(6),
      partido.jugadores(9))

    partido.equipoA = equipoA
    partido.equipoB = equipoB
  }

  override def getString = "14589"

}