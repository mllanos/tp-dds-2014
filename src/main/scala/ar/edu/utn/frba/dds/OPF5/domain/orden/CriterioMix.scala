package ar.edu.utn.frba.dds.OPF5.domain.orden

import scala.collection.mutable.ArrayBuffer
import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import org.uqbar.commons.utils.Observable

@Observable
class CriterioMix extends CriterioOrden {
  var criterios = new ArrayBuffer[CriterioOrden]

  def setCriterios(crit: ArrayBuffer[CriterioOrden]): CriterioOrden = {
    criterios = crit
    this
  }

  override def valuarJugador(jugador: Jugador): Double = {
    val sumatoria = criterios.map(c => c.valuarJugador(jugador)).foldLeft(0.0)(_ + _)
    sumatoria / criterios.size
  }

  override def getString = "Mix"
}