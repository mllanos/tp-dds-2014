package ar.edu.utn.frba.dds.OPF5.ui.filters

import ar.edu.utn.frba.dds.OPF5.domain.Jugador

class HastaHandicapFilter extends FilterByValue[Jugador, Int] {

  override def apply(jugador: Jugador, value: Int) = jugador.handicap <= value

  override def getString = "Hasta handicap"

}