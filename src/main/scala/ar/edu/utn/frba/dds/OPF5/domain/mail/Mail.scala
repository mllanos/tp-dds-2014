package ar.edu.utn.frba.dds.OPF5.domain.mail

class Mail(isFrom: String, sendTo: String, myTitle: String, myMessage: String) {
  val from = isFrom
  val to = sendTo
  val title = myTitle
  val message = myMessage
}