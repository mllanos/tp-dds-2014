package ar.edu.utn.frba.dds.OPF5.domain.command

import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.informes.Propuesta

class AceptarJugador extends Command {

  override def execute(admin: Administrador, propuesta: Propuesta) {
    propuesta.jugador.inscribirse(propuesta.partido, propuesta.tipo)
  }

}