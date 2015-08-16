package ar.edu.utn.frba.dds.OPF5.dao

import ar.edu.utn.frba.dds.OPF5.domain._
import scala.collection.JavaConversions._

object JugadorDAO extends DAO[Jugador] {
  
  def allInstances() = find("Jugador")
  
}