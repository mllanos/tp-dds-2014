package ar.edu.utn.frba.dds.OPF5.domain.inscripcion

import ar.edu.utn.frba.dds.OPF5.domain.{ Jugador, Partido }
import ar.edu.utn.frba.dds.OPF5.domain.exceptions._
import javax.persistence._
import scala.beans.BeanProperty

@Entity
class Estandar extends Inscripcion {

  override def prioridad = 0

  override def esEstandar = true
}