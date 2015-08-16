package ar.edu.utn.frba.dds.OPF5.domain.exceptions

case class NoCumpleCondicionException(smth: String) extends Exception(smth)