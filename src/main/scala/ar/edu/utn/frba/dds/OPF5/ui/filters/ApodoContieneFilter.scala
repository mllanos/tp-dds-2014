package ar.edu.utn.frba.dds.OPF5.ui.filters

import ar.edu.utn.frba.dds.OPF5.domain.Jugador

class ApodoContieneFilter extends FilterByValue[Jugador, String] {

  override def apply(jugador: Jugador, substring: String) = jugador.apodo contains substring

  override def getString = "Apodo contiene"
    
}