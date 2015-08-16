package ar.edu.utn.frba.dds.OPF5.domain.informes

import java.util.Date
import ar.edu.utn.frba.dds.OPF5.domain._
import javax.persistence._

@Entity
@Table(name = "denegacion")
class Denegacion(jd: Jugador, mt: String) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_denegacion")
  var id: Long = _

  @ManyToOne
  @JoinColumn(name = "id_jugador")
  val jugador = jd

  @Column(name = "dia")
  val dia = new Date()

  @Column(name = "motivo")
  val motivo = mt

  @ManyToOne
  @JoinColumn(name = "id_administrador")
  private var administrador: Administrador = _

  private def this() = this(null, null)

}