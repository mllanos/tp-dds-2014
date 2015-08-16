package ar.edu.utn.frba.dds.OPF5.domain.observers

import ar.edu.utn.frba.dds.OPF5.domain.{ Jugador, Partido }

trait PartidoObserver {
  def notifyAlta(jugador: Jugador, partido: Partido)
  def notifyBaja(jugador: Jugador, partido: Partido)
}