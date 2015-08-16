package ar.edu.utn.frba.dds.OPF5.domain.informes

import ar.edu.utn.frba.dds.OPF5.domain.inscripcion.Inscripcion
import ar.edu.utn.frba.dds.OPF5.domain._
import org.uqbar.commons.utils.Observable
import javax.persistence._

@Entity
@Table(name = "propuesta")
@Observable
class Propuesta(jd: Jugador, pd: Partido, ti: Inscripcion) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_propuesta")
  var id: Long = _

  @OneToOne(optional = false)
  @JoinColumn(name = "id_jugador")
  val jugador = jd

  @OneToOne(optional = false)
  @JoinColumn(name = "id_partido")
  val partido = pd

  @OneToOne(optional = false)
  @JoinColumn(name = "id_inscripcion")
  val tipo = ti
  
  @ManyToOne
  @JoinColumn(name = "id_administrador")
  private var administrador: Administrador = _
  
  private def this() = this(null, null, null)
  
}