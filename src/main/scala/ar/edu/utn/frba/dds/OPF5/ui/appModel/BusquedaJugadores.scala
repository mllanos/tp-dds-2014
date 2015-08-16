package ar.edu.utn.frba.dds.OPF5.ui.appModel

import org.uqbar.commons.utils.Observable
import scala.collection.mutable._
import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import ar.edu.utn.frba.dds.OPF5.domain.division._
import ar.edu.utn.frba.dds.OPF5.ui.filters._
import org.uqbar.commons.model.UserException
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import ar.edu.utn.frba.dds.OPF5.dao._

@Observable
object BusquedaJugadores {

  val nombreComienzaCon = new NombreComienzaFilter
  val apodoContiene = new ApodoContieneFilter
  val nacioAntesDe = new NacioAntesDeFilter
  val desdeHandicap = new DesdeHandicapFilter
  val hastaHandicap = new HastaHandicapFilter
  val desdePromedio = new DesdePromedioFilter
  val hastaPromedio = new HastaPromedioFilter
  val infracciones = new ArrayBuffer[Filter[Jugador]] +=
    (new NoTuvoInfraccionesFilter, new TuvoInfraccionesFilter, new TodosFilter)
  var jugadorSeleccionado: Jugador = _
  var porInfraccionesSeleccionado: Filter[Jugador] = _
  var nombreFiltro: String = _
  var apodoFiltro: String = _
  var nacimientoFiltro: String = _
  var desdeHandicapFiltro: String = _
  var hastaHandicapFiltro: String = _
  var desdePromedioFiltro: String = _
  var hastaPromedioFiltro: String = _

  var resultados: java.util.List[Jugador] = _

  def getInfracciones = infracciones.asJavaCollection

  def buscar() {
    verificarFiltros()

    var jugadores = JugadorDAO.allInstances

    if (nombreFiltro != null && !nombreFiltro.isEmpty)
      jugadores = jugadores.filter(nombreComienzaCon(_, nombreFiltro))

    if (apodoFiltro != null && !apodoFiltro.isEmpty)
      jugadores = jugadores.filter(apodoContiene(_, apodoFiltro))

    if (nacimientoFiltro != null && !nacimientoFiltro.isEmpty)
      jugadores = jugadores.filter(nacioAntesDe(_, nacimientoFiltro))

    if (desdeHandicapFiltro != null && !desdeHandicapFiltro.isEmpty)
      jugadores = jugadores.filter(desdeHandicap(_, desdeHandicapFiltro.toInt))

    if (hastaHandicapFiltro != null && !hastaHandicapFiltro.isEmpty)
      jugadores = jugadores.filter(hastaHandicap(_, hastaHandicapFiltro.toInt))

    if (desdePromedioFiltro != null && !desdePromedioFiltro.isEmpty)
      jugadores = jugadores.filter(desdePromedio(_, desdePromedioFiltro.toDouble))

    if (hastaPromedioFiltro != null && !hastaPromedioFiltro.isEmpty)
      jugadores = jugadores.filter(hastaPromedio(_, hastaPromedioFiltro.toDouble))

    jugadores = jugadores.filter(porInfraccionesSeleccionado(_))

    resultados = jugadores
  }

  def verificarFiltros() {
    if (nacimientoFiltro != null && !nacimientoFiltro.isEmpty && !nacimientoFiltro.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
      throw new UserException("Debe ingresar la fecha con formato yyyy-MM-dd'.")

    if (desdeHandicapFiltro == null && hastaHandicapFiltro == null)
      throw new UserException("Debe ingresar al menos un valor de filtro por handicap.")

    if (desdeHandicapFiltro != null && !desdeHandicapFiltro.matches("^\\d*$"))
      throw new UserException("El handicap desde tiene que ser un número entero.")

    if (hastaHandicapFiltro != null && !hastaHandicapFiltro.matches("^\\d*$"))
      throw new UserException("El handicap hasta tiene que ser un número entero.")

    if (desdeHandicapFiltro != null && desdeHandicapFiltro.toInt <= 0)
      throw new UserException("El handicap desde tiene que tener un valor mayor a 0.")

    if (hastaHandicapFiltro != null && hastaHandicapFiltro.toInt > 10)
      throw new UserException("El handicap hasta tiene que tener un valor menor o igual a 10.")

    if (desdePromedioFiltro == null || desdePromedioFiltro.isEmpty)
      throw new UserException("Debe ingresar el promedio desde.")

    if (!desdePromedioFiltro.matches("^\\d*(.\\d\\d*){0,1}$"))
      throw new UserException("El promedio desde tiene que ser un número decimal o entero.")

    if (hastaPromedioFiltro == null || hastaPromedioFiltro.isEmpty)
      throw new UserException("Debe ingresar el promedio hasta.")

    if (!hastaPromedioFiltro.matches("^\\d*(.\\d\\d*){0,1}$"))
      throw new UserException("El handicap hasta tiene que ser un número decimal o entero.")

    if (porInfraccionesSeleccionado == null)
      throw new UserException("Debe seleccionar un filtro por infracción.")
  }

  def verificarJugador() {
    if (jugadorSeleccionado == null)
      throw new UserException("Debe seleccionar un jugador.")
  }

}