package ar.edu.utn.frba.dds.OPF5.ui

import ar.edu.utn.frba.dds.OPF5.domain.Administrador
import scala.collection.JavaConverters._
import org.uqbar.arena.windows._
import org.uqbar.arena.widgets._
import org.uqbar.arena.actions._
import org.uqbar.arena.layout._

class OPF5 {}

class OPF5Window(owner: WindowOwner, admin: Administrador) extends SimpleWindow(owner, admin) {

  override def createMainTemplate(mainPanel: Panel) {
    this.setTitle("TP Diseño de Sistemas 2014")
    this.setTaskDescription("Seleccione una opción:")
    super.createMainTemplate(mainPanel)
  }

  override def createFormPanel(mainPanel: Panel) = new Panel(mainPanel)

  override def addActions(actionsPanel: Panel) {
    new Button(actionsPanel)
      .setCaption("Generar equipos")
      .onClick(new MessageSend(this, "generarEquipos"))

    new Button(actionsPanel)
      .setCaption("Busqueda de jugadores")
      .onClick(new MessageSend(this, "busquedaJugadores"))
  }

  def generarEquipos = new GenerarEquiposWindow(this, admin).open
  def busquedaJugadores = new BusquedaJugadoresWindow(this).open

}