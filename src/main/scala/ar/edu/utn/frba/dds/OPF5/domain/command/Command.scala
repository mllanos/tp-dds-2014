package ar.edu.utn.frba.dds.OPF5.domain.command

import ar.edu.utn.frba.dds.OPF5.domain.Administrador
import ar.edu.utn.frba.dds.OPF5.domain.informes.Propuesta

trait Command {
  def execute(admin: Administrador, propuesta: Propuesta)
}