package ar.edu.utn.frba.dds.OPF5.ui.filters

import ar.edu.utn.frba.dds.OPF5.domain.Jugador

class NombreComienzaFilter extends FilterByValue[Jugador, String] {

  override def apply(jugador: Jugador, substring: String) = jugador.nombre startsWith substring

  override def getString = "Nombre comienza"

}