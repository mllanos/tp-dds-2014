package ar.edu.utn.frba.dds.OPF5.ui

import collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import ar.edu.utn.frba.dds.OPF5.ui.appModel.ElegirCriterios
import org.uqbar.arena.windows._
import org.uqbar.arena.widgets._
import org.uqbar.arena.actions._
import org.uqbar.arena.layout._
import org.uqbar.arena.bindings._
import org.uqbar.arena.widgets.tables._

class ElegirCriteriosWindow(owner: WindowOwner) extends Dialog(owner, ElegirCriterios) {

  override def createMainTemplate(mainPanel: Panel) {
    this.setTitle("Selección de Criterios")
    this.setTaskDescription("Elija criterios para el mix.")
    super.createMainTemplate(mainPanel)
    this.getModelObject.init()
  }

  override def createFormPanel(mainPanel: Panel) = {
    val panelCriterios = new Panel(mainPanel).setLayout(new HorizontalLayout)

    val tablaCriterios1 = new Table[CriterioOrden](panelCriterios, classOf[CriterioOrden])
    tablaCriterios1.setWidth(150)
    tablaCriterios1.setHeigth(150)
    tablaCriterios1.bindItemsToProperty("criteriosSeleccionados")
    tablaCriterios1.bindValueToProperty("criterioT1Seleccionado")

    new Column[CriterioOrden](tablaCriterios1)
      .setTitle("Nombre")
      .bindContentsToProperty("string")

    val panelOpciones = new Panel(panelCriterios)

    new Button(panelOpciones)
      .setCaption("<<")
      .onClick(new MessageSend(this.getModelObject, "agregar"))
      .setWidth(40)

    new Button(panelOpciones)
      .setCaption(">>")
      .onClick(new MessageSend(this.getModelObject, "quitar"))
      .setWidth(40)

    new TextBox(panelOpciones).bindValueToProperty("partidosAConsiderar")

    val tablaCriterios2 = new Table[CriterioOrden](panelCriterios, classOf[CriterioOrden])
    tablaCriterios2.setWidth(150)
    tablaCriterios2.setHeigth(150)
    tablaCriterios2.bindItemsToProperty("criteriosDisponibles")
    tablaCriterios2.bindValueToProperty("criterioT2Seleccionado")

    new Column[CriterioOrden](tablaCriterios2)
      .setTitle("Nombre")
      .bindContentsToProperty("string")

    val panelAceptar = new Panel(mainPanel).setLayout(new HorizontalLayout)

    new Button(panelAceptar)
      .setCaption("Cancelar")
      .onClick(new MessageSend(this, "cancel"))

    new Button(panelAceptar)
      .setCaption("Aceptar")
      .onClick(new MessageSend(this, "accept"))
  }

  override def addActions(actions: Panel) = {
  }

  override def accept() {
    this.getModelObject.finalizar()
    super.accept()
  }
}