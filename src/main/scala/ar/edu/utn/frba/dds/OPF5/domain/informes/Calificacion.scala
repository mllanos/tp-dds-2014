package ar.edu.utn.frba.dds.OPF5.domain.informes

import ar.edu.utn.frba.dds.OPF5.domain.{ Jugador, Partido }
import javax.persistence._

@Entity
@Table(name = "calificacion")
class Calificacion(cl: Jugador, pd: Partido, jd: Jugador, vl: Int, cr: String) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_calificacion")
  var id: Long = _

  @ManyToOne
  @JoinColumn(name = "id_calificador")
  val calificador = cl

  @ManyToOne
  @JoinColumn(name = "id_partido")
  val partido = pd

  @Column(name = "valor")
  val valor = vl

  @Column(name = "critica")
  val critica = cr

  @ManyToOne
  @JoinColumn(name = "id_jugador")
  val jugador: Jugador = jd

  def this() = this(null, null, null, 0, null)
}