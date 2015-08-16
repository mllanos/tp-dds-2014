package ar.edu.utn.frba.dds.OPF5.ui.filters

import org.uqbar.commons.utils.Observable
import ar.edu.utn.frba.dds.OPF5.domain.Jugador

@Observable
class TodosFilter extends Filter[Jugador] {

  override def apply(jugador: Jugador) = true

  override def getString = "Todos"

}