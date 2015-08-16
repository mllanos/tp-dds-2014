package ar.edu.utn.frba.dds.OPF5.domain.inscripcion

import ar.edu.utn.frba.dds.OPF5.domain.{ Jugador, Partido }
import javax.persistence._
import scala.beans.BeanProperty
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

@Entity
class Condicional extends Inscripcion {

  override def prioridad = 2

  override def cumpleCondicion(partido: Partido, jugador: Jugador) =
    partido.condicion.apply(partido.jugadores.to[ArrayBuffer].clone += jugador)
}