package ar.edu.utn.frba.dds.OPF5.ui

import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.division._
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import ar.edu.utn.frba.dds.OPF5.ui.adapters.ColorTransformer
import ar.edu.utn.frba.dds.OPF5.ui.appModel.GenerarEquipos
import org.uqbar.arena.windows._
import org.uqbar.arena.widgets._
import org.uqbar.arena.actions._
import org.uqbar.arena.layout._
import org.uqbar.arena.bindings._
import org.uqbar.arena.widgets.tables._

class GenerarEquiposWindow(owner: WindowOwner, admin: Administrador) extends SimpleWindow(owner, GenerarEquipos) {

  override def createMainTemplate(mainPanel: Panel) {
    this.setTitle("Generar Equipos")
    this.setTaskDescription("Elija un criterio de orden y división de equipos.")
    super.createMainTemplate(mainPanel)
    this.getModelObject.admin = admin
    this.getModelObject.popularEquipos()
  }

  override def createFormPanel(mainPanel: Panel) {

    val hPanel = new Panel(mainPanel).setLayout(new HorizontalLayout)

    val vPanel1 = new Panel(hPanel)

    new Label(vPanel1).setText("Equipo 1")

    val tablaEquipo1 = new Table[Jugador](vPanel1, classOf[Jugador])
    tablaEquipo1.setWidth(300)
    tablaEquipo1.setHeigth(150)
    tablaEquipo1.bindItemsToProperty("equipoA")
    tablaEquipo1.bindValueToProperty("jugadorE1Seleccionado")

    new Column[Jugador](tablaEquipo1)
      .setTitle("Nombre")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("nombre")

    new Column[Jugador](tablaEquipo1)
      .setTitle("Mail")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("mail")

    new Column[Jugador](tablaEquipo1)
      .setTitle("Handicap")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("handicap")

    new Button(vPanel1)
      .setCaption("Ver jugador")
      .onClick(new MessageSend(this, "mostrarJugador1"))

    val vPanel2 = new Panel(hPanel)

    new Label(vPanel2).setText("Equipo 2")

    val tablaEquipo2 = new Table[Jugador](vPanel2, classOf[Jugador])
    tablaEquipo2.setWidth(300)
    tablaEquipo2.setHeigth(150)
    tablaEquipo2.bindItemsToProperty("equipoB")
    tablaEquipo2.bindValueToProperty("jugadorE2Seleccionado")

    new Column[Jugador](tablaEquipo2)
      .setTitle("Nombre")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("nombre")

    new Column[Jugador](tablaEquipo2)
      .setTitle("Mail")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("mail")

    new Column[Jugador](tablaEquipo2)
      .setTitle("Handicap")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("handicap")

    new Button(vPanel2)
      .setCaption("Ver jugador")
      .onClick(new MessageSend(this, "mostrarJugador2"))

    val rVPanel = new Panel(hPanel)

    new Label(rVPanel).setText("Criterios de orden")

    val rsCriteriosOrden = new RadioSelector(rVPanel).allowNull(false)
    rsCriteriosOrden.bindItems(new ObservableProperty(this.getModelObject, "criteriosOrden"))
      .setAdapter(new PropertyAdapter(classOf[CriterioOrden], "string"))
    rsCriteriosOrden.bindValueToProperty("criterioOrdenSeleccionado")

    new Label(rVPanel).setText("Criterios de division")

    val rsCriteriosDivision = new RadioSelector(rVPanel).allowNull(false)
    rsCriteriosDivision.bindItems(new ObservableProperty(this.getModelObject, "criteriosDivision"))
      .setAdapter(new PropertyAdapter(classOf[CriterioDivision], "string"))
    rsCriteriosDivision.bindValueToProperty("criterioDivisionSeleccionado")

    val lastGameCountPanel = new Panel(rVPanel).setLayout(new ColumnLayout(3))

    new Label(lastGameCountPanel).setText("Cant. partidos a considerar:")

    new TextBox(lastGameCountPanel).bindValueToProperty("cantPartidosAConsiderar")

    new Button(lastGameCountPanel)
      .setCaption("Elegir criterios para mix")
      .onClick(new MessageSend(this, "elegirCriterios"))

    new Button(rVPanel)
      .setCaption("Finalizar inscripción")
      .onClick(new MessageSend(this.getModelObject, "finalizarInscripcion"))

    new Button(rVPanel)
      .setCaption("Generar equipos")
      .onClick(new MessageSend(this.getModelObject, "generarEquipos"))

    new Button(rVPanel)
      .setCaption("Confirmar equipos")
      .onClick(new MessageSend(this.getModelObject, "confirmarEquipos"))

  }

  override def addActions(actionsPanel: Panel) {
  }

  def elegirCriterios() = new ElegirCriteriosWindow(this).open

  def mostrarJugador1() {
    this.getModelObject.verificarJugadorEquipo1
    new InfoJugadorWindow(this, this.getModelObject.jugadorE1Seleccionado).open
  }

  def mostrarJugador2() {
    this.getModelObject.verificarJugadorEquipo2
    new InfoJugadorWindow(this, this.getModelObject.jugadorE2Seleccionado).open
  }

}