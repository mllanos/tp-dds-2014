package ar.edu.utn.frba.dds.OPF5.domain

import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._
import collection.mutable.ArrayBuffer
import org.joda.time.{ DateTime, Period, PeriodType }
import ar.edu.utn.frba.dds.OPF5.domain.informes._
import ar.edu.utn.frba.dds.OPF5.domain.exceptions._
import org.uqbar.commons.utils.Observable
import ar.edu.utn.frba.dds.OPF5.domain.informes.Propuesta
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import javax.persistence._
import javax.persistence.CascadeType.ALL
import javax.persistence.GenerationType.IDENTITY
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import java.util._

@Entity
@Table(name = "jugador")
@Observable
class Jugador(nm: String, ap: String, bd: String, ml: String) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_jugador")
  var id: java.lang.Long = _

  @Column(name = "nombre")
  val nombre = nm

  @Column(name = "apodo")
  val apodo = ap

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_nacimiento")
  val fechaNacimiento: Date = new DateTime(bd).toDate

  @Column(name = "mail")
  val mail = ml

  @OneToMany(fetch = FetchType.EAGER, cascade = Array(ALL), mappedBy = "jugador")
  val calificaciones: List[Calificacion] = new ArrayList[Calificacion]

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "amigo",
    joinColumns = Array(new JoinColumn(name = "id_amigo")),
    inverseJoinColumns = Array(new JoinColumn(name = "id_jugador")))
  val amigos: List[Jugador] = new ArrayList[Jugador]

  @OneToMany(fetch = FetchType.EAGER, cascade = Array(ALL), mappedBy = "jugador")
  val infracciones: List[Infraccion] = new ArrayList[Infraccion]

  @OneToMany(fetch = FetchType.EAGER, cascade = Array(ALL), mappedBy = "jugador")
  val inscripciones: List[Inscripcion] = new ArrayList[Inscripcion]

  @Column(name = "handicap")
  var handicap: Int = 0

  private def this() = this(null, null, null, null)

  def inscribirse(partido: Partido, tipo: Inscripcion) {
    tipo.jugador = this
    tipo.partido = partido
    inscripciones += tipo
    tipo.inscribir()
  }

  def bajarse(partido: Partido, reemplazo: Jugador) {
    val inscripcion = inscripciones.find(_.partido == partido).get
    inscripcion.desinscribir(reemplazo)
    inscripciones -= inscripcion
  }

  def bajarse(partido: Partido) {
    val inscripcion = inscripciones.find(_.partido == partido).get
    inscripcion.desinscribir()
    inscripciones -= inscripcion
  }

  def agregarAmigo(amigo: Jugador) {
    if (!amigos.contains(amigo)) {
      amigos += amigo
    }
  }

  def quitarAmigo(amigo: Jugador) {
    amigos -= amigo
  }

  def proponer(admin: Administrador, partido: Partido, jugador: Jugador, tipo: Inscripcion) = {
    val propuesta = new Propuesta(jugador, partido, tipo)
    admin.propuestos += propuesta
    propuesta
  }

  def calificar(partido: Partido, jugador: Jugador, valor: Int, critica: String) {
    if (!partido.partidoConfirmado)
      throw new PartidoConfirmadoException("No se puede calificar a jugadores de un partido no confirmado.")
    if (!partido.jugadores.contains(this))
      throw new CalificacionException("No se puede calificar en un partido en el que no se jugó.")
    if (!partido.jugadores.contains(jugador))
      throw new CalificacionException("No se puede calificar a un jugador que no jugó un partido.")

    jugador.calificaciones += new Calificacion(this, partido, jugador, valor, critica)
  }

  def edad = new Period(new DateTime(fechaNacimiento), DateTime.now, PeriodType.yearMonthDay).getYears

  def partidosJugados = inscripciones.filter(_.haJugado).map(_.partido)

  def promedioUltimoPartido = new CriterioPromedioUltimoPartido().valuarJugador(this).toString.slice(0, 4)

  def promedioTotal = new CriterioPromedioNPartidos().setN(this.inscripciones.size).valuarJugador(this).toString.slice(0, 4)

  def buenHandicap = handicap > 8

  override def toString = "Nombre: " + nombre + " Mail: " + mail
}