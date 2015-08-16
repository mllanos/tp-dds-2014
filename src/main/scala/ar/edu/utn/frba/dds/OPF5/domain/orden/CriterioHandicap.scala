package ar.edu.utn.frba.dds.OPF5.domain.orden

import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import org.uqbar.commons.utils.Observable

@Observable
class CriterioHandicap extends CriterioOrden {
  override def valuarJugador(jugador: Jugador): Double = {
    jugador.handicap.toDouble
  }

  override def getString = "Handicap"
}