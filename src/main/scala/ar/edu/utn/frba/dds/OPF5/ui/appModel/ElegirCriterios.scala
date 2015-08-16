package ar.edu.utn.frba.dds.OPF5.ui.appModel

import org.uqbar.commons.utils.Observable
import scala.collection.mutable._
import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import ar.edu.utn.frba.dds.OPF5.domain.division._
import org.joda.time._
import org.uqbar.commons.model.UserException
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

@Observable
object ElegirCriterios {

  val criterioHandicap = new CriterioHandicap
  val criterioNCalificaciones = new CriterioPromedioNPartidos
  val criterioUltimopartido = new CriterioPromedioUltimoPartido

  var criteriosDisponibles: java.util.List[CriterioOrden] = _
  var criteriosSeleccionados: java.util.List[CriterioOrden] = _

  var totalCriterios = new ArrayBuffer[CriterioOrden] +=
    (criterioHandicap, criterioNCalificaciones, criterioUltimopartido)

  var criterioT1Seleccionado: CriterioOrden = _
  var criterioT2Seleccionado: CriterioOrden = _

  var partidosAConsiderar: Int = _

  def getCriteriosDisponibles = criteriosDisponibles

  def getCriteriosSeleccionados = criteriosSeleccionados

  def init() {
    criteriosDisponibles = totalCriterios.filter(!GenerarEquipos.criteriosSeleccionados.contains(_))
    criteriosSeleccionados = GenerarEquipos.criteriosSeleccionados
  }

  def finalizar() {
    if (criteriosSeleccionados.contains(criterioNCalificaciones) && partidosAConsiderar < 1)
      throw new UserException("Debe indicar un valor numerico mayor a 0 para el criterio N partidos.")

    criterioNCalificaciones.setN(partidosAConsiderar)

    GenerarEquipos.criteriosSeleccionados = criteriosSeleccionados.to[ArrayBuffer]
  }

  def agregar() {
    if (criterioT2Seleccionado == null)
      throw new UserException("Debe seleccionar un criterio de la tabla 2.")

    val cDisponibles = criteriosDisponibles.to[ArrayBuffer]
    val cSeleccionados = criteriosSeleccionados.to[ArrayBuffer]

    cDisponibles -= criterioT2Seleccionado
    cSeleccionados += criterioT2Seleccionado

    criteriosDisponibles = cDisponibles
    criteriosSeleccionados = cSeleccionados
  }

  def quitar() {
    if (criterioT1Seleccionado == null)
      throw new UserException("Debe seleccionar un criterio de la tabla 1.")

    val cDisponibles = criteriosDisponibles.to[ArrayBuffer]
    val cSeleccionados = criteriosSeleccionados.to[ArrayBuffer]

    cSeleccionados -= criterioT1Seleccionado
    cDisponibles += criterioT1Seleccionado

    criteriosDisponibles = cDisponibles
    criteriosSeleccionados = cSeleccionados
  }

}