package ar.edu.utn.frba.dds.OPF5.domain.inscripcion

import ar.edu.utn.frba.dds.OPF5.domain.{ Jugador, Partido }
import javax.persistence._
import scala.beans.BeanProperty

@Entity
class Solidaria extends Inscripcion {
  override def prioridad = 1
}