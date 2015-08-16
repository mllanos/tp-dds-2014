package ar.edu.utn.frba.dds.OPF5.ui.factory

import org.uqbar.arena.windows._
import org.uqbar.arena.widgets._
import org.uqbar.arena.actions._
import org.uqbar.arena.layout._
import org.uqbar.arena.bindings._
import org.uqbar.arena.widgets.tables._
import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import ar.edu.utn.frba.dds.OPF5.ui.adapters.ColorTransformer

class GrillaJugadoresFactory(panel: Panel, desc: String, property: String, selected: String) {

  def create() {
    new Label(panel).setText(desc)

    val grillaJugadores = new Table[Jugador](panel, classOf[Jugador])
    grillaJugadores.setWidth(300)
    grillaJugadores.setHeigth(100)
    grillaJugadores.bindItemsToProperty(property)
    grillaJugadores.bindValueToProperty(selected)

    new Column[Jugador](grillaJugadores)
      .setTitle("Nombre")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("nombre")

    new Column[Jugador](grillaJugadores)
      .setTitle("Apodo")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("apodo")

    new Column[Jugador](grillaJugadores)
      .setTitle("Handicap")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("handicap")

    new Column[Jugador](grillaJugadores)
      .setTitle("Promedio")
      .bindBackground("buenHandicap", ColorTransformer)
      .bindContentsToProperty("promedioTotal")
  }
}