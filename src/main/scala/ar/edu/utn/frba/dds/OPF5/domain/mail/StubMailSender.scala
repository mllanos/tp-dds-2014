package ar.edu.utn.frba.dds.OPF5.domain.mail

import collection.mutable._
import ar.edu.utn.frba.dds.OPF5._

object StubMailSender extends MessageSender {
  val mails = new HashMap[String, HashSet[Mail]]

  override def send(mail: Mail) {
    simularEnvioMail(mail.to, mail)
  }

  def simularEnvioMail(to: String, mail: Mail) {
    var casilla: HashSet[Mail] = mails.getOrElse(to, null)
    if (casilla == null)
      casilla = new HashSet[Mail]
    casilla += mail
    mails.put(to, casilla)
  }

  def casillaDe(email: String): HashSet[Mail] = {
    var casilla: HashSet[Mail] = mails.getOrElse(email, null)
    if (casilla == null)
      casilla = new HashSet[Mail]
    mails.put(email, casilla)
    casilla
  }
}