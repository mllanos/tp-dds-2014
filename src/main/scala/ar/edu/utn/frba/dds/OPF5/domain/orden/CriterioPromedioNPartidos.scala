package ar.edu.utn.frba.dds.OPF5.domain.orden

import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import org.uqbar.commons.utils.Observable
import scala.collection.JavaConversions._

@Observable
class CriterioPromedioNPartidos extends CriterioOrden {
  var cantidad: Int = 0

  def setN(n: Int): CriterioOrden = {
    cantidad = n
    this
  }

  override def valuarJugador(jugador: Jugador): Double = {
    var result = 0.00
    val ultimosNPartidos = jugador.partidosJugados.reverse.slice(1, cantidad + 1)
    val calificacionesPartidos = jugador.calificaciones.filter(ultimosNPartidos contains _.partido)
    if (calificacionesPartidos.size > 0) {
      val sumatoria = calificacionesPartidos.map(_.valor).sum
      result = sumatoria / calificacionesPartidos.size.toDouble
    }
    result
  }

  override def getString = "Promedio N partidos"

}