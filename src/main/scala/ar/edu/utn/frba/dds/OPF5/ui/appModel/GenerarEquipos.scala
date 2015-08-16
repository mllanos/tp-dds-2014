package ar.edu.utn.frba.dds.OPF5.ui.appModel

import org.uqbar.commons.utils.Observable
import scala.collection.mutable._
import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import ar.edu.utn.frba.dds.OPF5.domain.division._
import org.uqbar.commons.model.UserException
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import ar.edu.utn.frba.dds.OPF5.dao._

@Observable
object GenerarEquipos {
  var admin: Administrador = _
  val criterioMix = new CriterioMix
  val criterioPromedioNPartidos = new CriterioPromedioNPartidos
  val criteriosOrden = new ArrayBuffer[CriterioOrden] += (
    new CriterioHandicap, criterioMix, criterioPromedioNPartidos, new CriterioPromedioUltimoPartido)
  val criteriosDivision = new ArrayBuffer[CriterioDivision] += (
    new CriterioParidad, new Criterio14589)
  var criterioOrdenSeleccionado: CriterioOrden = _
  var criterioDivisionSeleccionado: CriterioDivision = _
  var cantPartidosAConsiderar: Int = _
  var criteriosSeleccionados = new ArrayBuffer[CriterioOrden]
  var jugadorE1Seleccionado: Jugador = _
  var jugadorE2Seleccionado: Jugador = _

  def getCriteriosDivision = criteriosDivision.asJavaCollection

  def getCriteriosOrden = criteriosOrden.asJavaCollection

  var equipoA: java.util.List[Jugador] = _
  var equipoB: java.util.List[Jugador] = _

  def finalizarInscripcion() {
    try {
      val partidoActivo = PartidoDAO.lastGame()

      admin.finalizarInscripcion(partidoActivo)

      PartidoDAO.update(partidoActivo)
    } catch {
      case e: Exception => throw new UserException(e.getMessage())
    }
  }

  def popularEquipos() {
    val partidoActivo = PartidoDAO.lastGame()
    equipoA = partidoActivo.equipoA
    equipoB = partidoActivo.equipoB
  }

  def generarEquipos() {
    if (criterioOrdenSeleccionado == null)
      throw new UserException("Debe seleccionar un criterio de orden.")
    else if (criterioOrdenSeleccionado.getString == "Promedio N partidos" && cantPartidosAConsiderar < 1)
      throw new UserException("Debe ingresar una cantidad de partidos a considerar mayor a 0.")
    else if (criterioOrdenSeleccionado.getString == "Mix" && criteriosSeleccionados.isEmpty)
      throw new UserException("Debe seleccionar al menos un criterio para el mix.")
    if (criterioDivisionSeleccionado == null)
      throw new UserException("Debe seleccionar un criterio de division.")

    criterioMix.setCriterios(criteriosSeleccionados)
    criterioPromedioNPartidos.setN(cantPartidosAConsiderar)

    try {
      val partidoActivo = PartidoDAO.lastGame()

      admin.organizarJugadores(partidoActivo, criterioOrdenSeleccionado)
      admin.generarEquiposTentativos(partidoActivo, criterioDivisionSeleccionado)

      PartidoDAO.update(partidoActivo)

      popularEquipos()
    } catch {
      case e: Exception => throw new UserException(e.getMessage())
    }
  }

  def confirmarEquipos() {
    try {
      val partidoActivo = PartidoDAO.lastGame()

      admin.confirmarEquipos(partidoActivo)

      PartidoDAO.update(partidoActivo)
    } catch {
      case e: Exception => throw new UserException(e.getMessage())
    }
  }

  def verificarJugadorEquipo1() {
    if (jugadorE1Seleccionado == null)
      throw new UserException("Debe seleccionar un jugador del Equipo 1.")
  }

  def verificarJugadorEquipo2() {
    if (jugadorE2Seleccionado == null)
      throw new UserException("Debe seleccionar un jugador del Equipo 2.")
  }

}