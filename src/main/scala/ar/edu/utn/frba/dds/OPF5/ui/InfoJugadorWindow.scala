package ar.edu.utn.frba.dds.OPF5.ui

import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import ar.edu.utn.frba.dds.OPF5.domain.informes.Infraccion
import ar.edu.utn.frba.dds.OPF5.ui.adapters.ColorTransformer
import ar.edu.utn.frba.dds.OPF5.ui.factory.GrillaJugadoresFactory
import ar.edu.utn.frba.dds.OPF5.ui.appModel.InfoJugador
import scala.collection.JavaConverters._
import org.uqbar.arena.windows._
import org.uqbar.arena.widgets._
import org.uqbar.arena.actions._
import org.uqbar.arena.layout._
import org.uqbar.arena.bindings._
import org.uqbar.arena.widgets.tables._
import scala.collection.JavaConversions._
import org.joda.time.DateTime

class InfoJugadorWindow(owner: WindowOwner, jugador: Jugador) extends Dialog(owner, InfoJugador) {
  

  override def createMainTemplate(mainPanel: Panel) {
    this.getModelObject.jugador = jugador
    this.setTitle("Datos de Jugador")
    super.createMainTemplate(mainPanel)
  }

  override def createFormPanel(mainPanel: Panel) {
    val hPanel = new Panel(mainPanel).setLayout(new HorizontalLayout)

    val infoPanel = new Panel(hPanel)

    new Label(infoPanel).setText("Nombre: " + jugador.nombre).setFontSize(12)
    new Label(infoPanel).setText("Apodo: " + jugador.apodo).setFontSize(12)
    new Label(infoPanel).setText("Handicap: " + jugador.handicap).setFontSize(12)
    new Label(infoPanel).setText("Promedio último partido: " + jugador.promedioUltimoPartido).setFontSize(12)
    new Label(infoPanel).setText("Promedio total: " + jugador.promedioTotal).setFontSize(12)
    new Label(infoPanel).setText("Fecha de nacimiento: " + new DateTime(jugador.fechaNacimiento).toString("dd-MM-yyyy")).setFontSize(12)
    new Label(infoPanel).setText("Partidos jugados: " + jugador.inscripciones.filter(_.haJugado).size).setFontSize(12)

    val grillaPanel = new Panel(hPanel)

    new GrillaJugadoresFactory(grillaPanel, "Grilla amigos", "amigos", "amigoSeleccionado").create()

    new Label(grillaPanel).setText("Grilla infracciones")

    val grillaInfracciones = new Table[Infraccion](grillaPanel, classOf[Infraccion])
    grillaInfracciones.setWidth(300)
    grillaInfracciones.setHeigth(100)
    grillaInfracciones.bindItemsToProperty("infracciones")

    new Column[Infraccion](grillaInfracciones)
      .setTitle("Fecha")
      .bindContentsToProperty("fecha")

    new Column[Infraccion](grillaInfracciones)
      .setTitle("Hora")
      .bindContentsToProperty("hora")

    new Column[Infraccion](grillaInfracciones)
      .setTitle("Motivo")
      .bindContentsToProperty("motivo")

    new Button(mainPanel)
      .setCaption("Volver")
      .onClick(new MessageSend(this, "cancel"))

  }

  override def addActions(actionsPanel: Panel) {

  }

}