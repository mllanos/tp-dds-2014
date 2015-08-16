package ar.edu.utn.frba.dds.OPF5.ui.filters

import ar.edu.utn.frba.dds.OPF5.domain.Jugador

class DesdePromedioFilter extends FilterByValue[Jugador, Double] {

  override def apply(jugador: Jugador, value: Double) = jugador.promedioUltimoPartido.toDouble >= value

  override def getString = "Desde promedio"

}