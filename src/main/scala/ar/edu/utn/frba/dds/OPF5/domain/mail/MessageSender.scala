package ar.edu.utn.frba.dds.OPF5.domain.mail

trait MessageSender {
  def send(mail: Mail)
}