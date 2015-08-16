package ar.edu.utn.frba.dds.OPF5.domain.orden

import ar.edu.utn.frba.dds.OPF5.domain.Jugador

trait CriterioOrden {
  def valuarJugador(jugador: Jugador): Double
  def getString: String
}