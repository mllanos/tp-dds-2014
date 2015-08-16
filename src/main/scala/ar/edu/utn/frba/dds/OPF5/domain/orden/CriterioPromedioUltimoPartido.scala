package ar.edu.utn.frba.dds.OPF5.domain.orden

import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import org.uqbar.commons.utils.Observable
import scala.collection.JavaConversions._

@Observable
class CriterioPromedioUltimoPartido extends CriterioOrden {

  override def valuarJugador(jugador: Jugador): Double = {
    var result = 0.00
    if (jugador.partidosJugados.size > 1) {
      val ultimoPartido = jugador.partidosJugados.reverse(1)
      val calificacionesUltimoPartido = jugador.calificaciones.filter(_.partido == ultimoPartido)

      if (calificacionesUltimoPartido.size > 0) {
        val sumatoria = calificacionesUltimoPartido.map(c => c.valor).foldLeft(0)(_ + _)
        result = sumatoria / calificacionesUltimoPartido.size.toDouble
      }
    }

    result
  }

  override def getString = "Promedio ultimo partido"

}