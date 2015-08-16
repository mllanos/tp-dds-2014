package ar.edu.utn.frba.dds.OPF5.domain.division

import ar.edu.utn.frba.dds.OPF5.domain.Partido

trait CriterioDivision {
  def dividir(partido: Partido)
  def getString: String
}