package ar.edu.utn.frba.dds.OPF5.ui.appModel

import org.uqbar.commons.utils.Observable
import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

@Observable
object InfoJugador {
  var jugador: Jugador = _
  var amigoSeleccionado: Jugador = _

  def getAmigos = jugador.amigos

  def getInfracciones = jugador.infracciones
}