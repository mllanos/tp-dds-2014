package ar.edu.utn.frba.dds.OPF5.ui.filters

import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import java.util.Date
import java.text.SimpleDateFormat

class NacioAntesDeFilter extends FilterByValue[Jugador, String] {

  val formatter = new SimpleDateFormat("yyyy-MM-dd")

  override def apply(jugador: Jugador, date: String) = jugador.fechaNacimiento.before(formatter.parse(date))

  override def getString = "Nacio antes de"
}