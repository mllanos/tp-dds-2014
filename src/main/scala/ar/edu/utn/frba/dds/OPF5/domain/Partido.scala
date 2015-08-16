package ar.edu.utn.frba.dds.OPF5.domain

import scala.collection.mutable.ArrayBuffer
import ar.edu.utn.frba.dds.OPF5.domain.exceptions._
import ar.edu.utn.frba.dds.OPF5.domain.observers._
import org.joda.time.DateTime
import ar.edu.utn.frba.dds.OPF5.domain.division.CriterioDivision
import ar.edu.utn.frba.dds.OPF5.domain.orden.CriterioOrden
//import org.uqbar.commons.model.Entity
import org.uqbar.commons.utils.Observable
import ar.edu.utn.frba.dds.OPF5.domain.condicion.Condicion
import javax.persistence._
import scala.collection.JavaConversions._
import scala.beans.BeanProperty
import org.hibernate.annotations.WhereJoinTable

import java.util._
import javax.persistence.CascadeType.ALL
import javax.persistence.GenerationType.IDENTITY
import java.util.Date

@Entity
@Table(name = "partido")
@Observable
class Partido(ad: Administrador, ub: String, cd: Condicion, fe: String) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_partido")
  var id: Long = _
  
  @ManyToOne(cascade = Array(ALL), optional = false)
  @JoinColumn(name = "id_administrador")
  val admin = ad
  
  @Column(name = "ubicacion")
  val ubicacion = ub

  @OneToOne(cascade = Array(ALL), optional = false)
  @JoinColumn(name = "id_condicion")
  val condicion = cd

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha")
  val fecha: Date = new DateTime(fe).toDate

  @Transient
  val observers = new ArrayBuffer[PartidoObserver] +=
    (new AdminObserver, new AmigosObserver)

  @ManyToMany(fetch = FetchType.EAGER, cascade = Array(ALL))
  @JoinTable(name = "jugador_x_partido",
    joinColumns = Array(new JoinColumn(name = "id_partido")),
    inverseJoinColumns = Array(new JoinColumn(name = "id_jugador")))
  var jugadores: List[Jugador] = new ArrayList[Jugador]

  @ManyToMany(fetch = FetchType.EAGER, cascade = Array(ALL))
  @JoinTable(name = "equipo_a",
    joinColumns = Array(new JoinColumn(name = "id_partido")),
    inverseJoinColumns = Array(new JoinColumn(name = "id_jugador")))
  var equipoA: List[Jugador] = new ArrayList[Jugador]

  @ManyToMany(fetch = FetchType.EAGER, cascade = Array(ALL))
  @JoinTable(name = "equipo_b",
    joinColumns = Array(new JoinColumn(name = "id_partido")),
    inverseJoinColumns = Array(new JoinColumn(name = "id_jugador")))
  var equipoB: List[Jugador] = new ArrayList[Jugador]

  @Column(name = "partido_confirmado")
  var partidoConfirmado = false

  @Column(name = "equipos_confirmados")
  var equiposConfirmados = false

  private def this() = this(null, null, null, null)

  def jugadoresEstandar = jugadores.filter(j => j.inscripciones.find(_.partido == this).get.esEstandar)

}