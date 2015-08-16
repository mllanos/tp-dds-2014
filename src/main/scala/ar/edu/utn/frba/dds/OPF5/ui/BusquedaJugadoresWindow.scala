package ar.edu.utn.frba.dds.OPF5.ui

import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.ui.adapters._
import ar.edu.utn.frba.dds.OPF5.ui.factory.GrillaJugadoresFactory
import ar.edu.utn.frba.dds.OPF5.ui.filters.Filter
import ar.edu.utn.frba.dds.OPF5.ui.appModel.BusquedaJugadores
import org.uqbar.arena.windows._
import org.uqbar.arena.widgets._
import org.uqbar.arena.actions._
import org.uqbar.arena.layout._
import org.uqbar.arena.bindings._
import org.uqbar.arena.widgets.tables._

class BusquedaJugadoresWindow(owner: WindowOwner) extends SimpleWindow(owner, BusquedaJugadores) {

  override def createMainTemplate(mainPanel: Panel) {
    this.setTitle("Busqueda de Jugadores")
    this.setTaskDescription("Ingrese un criterio de búsqueda")
    super.createMainTemplate(mainPanel)
  }

  override def createFormPanel(mainPanel: Panel) {

    new Label(mainPanel).setText("Filtros de búsqueda.")

    val cPanel = new Panel(mainPanel).setLayout(new ColumnLayout(2))

    new Label(cPanel).setText("Jugador comienza con: ")
    new TextBox(cPanel).bindValueToProperty("nombreFiltro")

    new Label(cPanel).setText("Apodo contiene: ")
    new TextBox(cPanel).bindValueToProperty("apodoFiltro")

    new Label(cPanel).setText("Fecha de nacimiento anterior a: ")
    new TextBox(cPanel).bindValueToProperty("nacimientoFiltro")

    new Label(cPanel).setText("Desde / hasta handicap: ")

    val handicapPanel = new Panel(cPanel).setLayout(new HorizontalLayout)
    new TextBox(handicapPanel).setWidth(20).bindValueToProperty("desdeHandicapFiltro")
    new TextBox(handicapPanel).setWidth(20).bindValueToProperty("hastaHandicapFiltro")

    new Label(cPanel).setText("Desde / hasta promedio ultimo partido: ")

    val promedioPanel = new Panel(cPanel).setLayout(new HorizontalLayout)
    new TextBox(promedioPanel).setWidth(20).bindValueToProperty("desdePromedioFiltro")
    new TextBox(promedioPanel).setWidth(20).bindValueToProperty("hastaPromedioFiltro")

    val infraccionesRS = new RadioSelector(mainPanel).allowNull(false)
    infraccionesRS.bindItems(new ObservableProperty(this.getModelObject, "infracciones"))
      .setAdapter(new PropertyAdapter(classOf[Filter[Jugador]], "string"))
    infraccionesRS.bindValueToProperty("porInfraccionesSeleccionado")

    val botoneraPanel = new Panel(mainPanel).setLayout(new HorizontalLayout)

    new Button(botoneraPanel)
      .setCaption("Buscar")
      .onClick(new MessageSend(this.getModelObject, "buscar"))
      .setAsDefault

    new Button(botoneraPanel)
      .setCaption("Ver Jugador")
      .onClick(new MessageSend(this, "mostrarJugador"))

    new GrillaJugadoresFactory(mainPanel, "Resultados", "resultados", "jugadorSeleccionado").create()
  }

  override def addActions(actionsPanel: Panel) {

  }

  def mostrarJugador() {
    this.getModelObject.verificarJugador
    new InfoJugadorWindow(this, this.getModelObject.jugadorSeleccionado).open
  }

}