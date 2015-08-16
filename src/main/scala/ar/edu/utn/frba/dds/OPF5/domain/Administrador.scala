package ar.edu.utn.frba.dds.OPF5.domain

import ar.edu.utn.frba.dds.OPF5.domain.exceptions._
import ar.edu.utn.frba.dds.OPF5.domain.informes._
import ar.edu.utn.frba.dds.OPF5.domain.orden.CriterioOrden
import ar.edu.utn.frba.dds.OPF5.domain.division.CriterioDivision
import ar.edu.utn.frba.dds.OPF5.domain.command.Command
import ar.edu.utn.frba.dds.OPF5.domain.informes.Propuesta
import org.uqbar.commons.utils.Observable
import scala.collection.mutable.ArrayBuffer
import javax.persistence._
import scala.beans.BeanProperty
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import javax.persistence.CascadeType.ALL
import javax.persistence.GenerationType.IDENTITY
import java.util._

@Entity
@Table(name = "administrador")
@Observable
class Administrador(nm: String, ml: String) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_administrador")
  var id: Long = _

  @Column(name = "nombre")
  val nombre = nm

  @Column(name = "mail")
  val mail = ml

  @OneToMany(fetch = FetchType.EAGER, cascade = Array(ALL), mappedBy = "administrador")
  val propuestos: List[Propuesta] = new ArrayList[Propuesta]

  @OneToMany(fetch = FetchType.EAGER, cascade = Array(ALL), mappedBy = "administrador")
  val denegados: List[Denegacion] = new ArrayList[Denegacion]

  private def this() = this(null, null)

  def considerarPropuesta(propuesta: Propuesta, command: Command) {
    command.execute(this, propuesta)
    propuestos -= propuesta
  }

  def asignarHandicap(jugador: Jugador, hd: Int) {
    jugador.handicap = hd
  }

  def finalizarInscripcion(partido: Partido) {
    if (partido.jugadores.size < 10)
      throw new NoHay10AnotadosException("Faltan jugadores.")
    if (partido.partidoConfirmado)
      throw new PartidoConfirmadoException("Partido ya confirmado.")

    val ordenados = partido.jugadores sortBy (_.inscripciones.find(_.partido == partido).get.prioridad)
    partido.jugadores = ordenados.slice(0, 10)

    for (j <- partido.jugadores) {
      j.inscripciones.find(_.partido == partido).get.haJugado = true
      //j.promedioUltimo = j.promedioUltimoPartido.toDouble
    }

    partido.partidoConfirmado = true
  }

  def organizarJugadores(partido: Partido, criterio: CriterioOrden) {
    val nuevoOrden = partido.jugadores sortBy (criterio.valuarJugador(_))
    partido.jugadores = nuevoOrden.reverse
  }

  def generarEquiposTentativos(partido: Partido, criterio: CriterioDivision) {
    if (partido.equiposConfirmados)
      throw new EquipoConfirmadoException("Ya estan confirmados los equipos.")
    if (!partido.partidoConfirmado)
      throw new PartidoConfirmadoException("El partido no esta confirmado todavía.")

    criterio.dividir(partido)
  }

  def confirmarEquipos(partido: Partido) {
    if (partido.equipoA.size != 5 || partido.equipoB.size != 5)
      throw new EquipoConfirmadoException("No se pueden confirmar los equipos.")
    if (partido.equiposConfirmados)
      throw new EquipoConfirmadoException("Equipos ya confirmados.")

    partido.equiposConfirmados = true
  }

}