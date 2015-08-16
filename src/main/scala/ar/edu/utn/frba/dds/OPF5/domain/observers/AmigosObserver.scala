package ar.edu.utn.frba.dds.OPF5.domain.observers

import ar.edu.utn.frba.dds.OPF5.domain.{ Jugador, Partido }
import ar.edu.utn.frba.dds.OPF5.domain.mail._
import scala.collection.JavaConversions._
import org.joda.time.DateTime

class AmigosObserver extends PartidoObserver {

  override def notifyAlta(jugador: Jugador, partido: Partido) {
    for (amigo <- jugador.amigos)
      StubMailSender.send(new Mail(jugador.mail, amigo.mail, "Me anote al partido",
        "Me anote al partido de la fecha " + new DateTime(partido.fecha).toString("yyyy-MM-dd") + ", copate."))
  }

  override def notifyBaja(jugador: Jugador, partido: Partido) {
  }
}