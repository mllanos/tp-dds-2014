package ar.edu.utn.frba.dds.OPF5.domain.observers

import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.mail._
import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._
import org.joda.time.DateTime

class AdminObserver extends PartidoObserver {

  val systemMail = "sistema@opf5.com.ar"

  var completoNotificado: Boolean = false

  override def notifyAlta(jugador: Jugador, partido: Partido) {
    if (!completoNotificado && partido.jugadoresEstandar.size == 10) {
      StubMailSender.send(new Mail(systemMail, partido.admin.mail, "Partido Completo",
        "Las plazas de la fecha " + new DateTime(partido.fecha).toString("dd-MM-yyyy") + " estan completas."))
      completoNotificado = true
    }
  }

  override def notifyBaja(jugador: Jugador, partido: Partido) {
    if (completoNotificado && partido.jugadoresEstandar.size != 10) {
      StubMailSender.send(new Mail(systemMail, partido.admin.mail, "Partido Incompleto",
        "Las plazas de la fecha " + new DateTime(partido.fecha).toString("dd-MM-yyyy") + " ya no estan completas."))
      completoNotificado = false
    }
  }
}