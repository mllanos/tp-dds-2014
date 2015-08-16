package ar.edu.utn.frba.dds.OPF5.dao

import org.hibernate._
import java.util._
import ar.edu.utn.frba.dds.OPF5.domain._
import scala.collection.JavaConversions._

object PartidoDAO extends DAO[Partido] {

  def lastGame() = findUnique("Partido", Array[String](), "order by id_partido desc")

  def allInstances() = find("Partido")

}