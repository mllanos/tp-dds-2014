package ar.edu.utn.frba.dds.OPF5.domain.command

import ar.edu.utn.frba.dds.OPF5.domain._
import informes.Denegacion
import org.joda.time.DateTime
import ar.edu.utn.frba.dds.OPF5.domain.informes.Propuesta
import scala.collection.JavaConversions._

class DenegarJugador(motivo: String) extends Command {

  override def execute(admin: Administrador, propuesta: Propuesta) {
    admin.denegados += new Denegacion(propuesta.jugador, motivo)
  }

}