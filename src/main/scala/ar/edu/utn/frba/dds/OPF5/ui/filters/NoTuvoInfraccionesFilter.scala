package ar.edu.utn.frba.dds.OPF5.ui.filters

import org.uqbar.commons.utils.Observable
import ar.edu.utn.frba.dds.OPF5.domain.Jugador

@Observable
class NoTuvoInfraccionesFilter extends Filter[Jugador] {

  override def apply(jugador: Jugador) = jugador.infracciones.isEmpty

  override def getString = "No tuvo infracciones"

}