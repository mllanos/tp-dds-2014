package ar.edu.utn.frba.dds.OPF5.domain.division

import ar.edu.utn.frba.dds.OPF5.domain.{Jugador, Partido}
import org.uqbar.commons.utils.Observable
import collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._

@Observable
class CriterioParidad extends CriterioDivision {

  override def dividir(partido: Partido) {
    val equipoA = new ArrayBuffer[Jugador]
    val equipoB = new ArrayBuffer[Jugador]
    
    for (x <- 0 to 4) {
      equipoA += partido.jugadores(x * 2)
      equipoB += partido.jugadores(x * 2 + 1)
    }
    
    partido.equipoA = equipoA
    partido.equipoB = equipoB
  }

  override def getString = "Paridad"

}