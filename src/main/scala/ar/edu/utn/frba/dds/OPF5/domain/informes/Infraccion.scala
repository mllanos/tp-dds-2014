package ar.edu.utn.frba.dds.OPF5.domain.informes

import org.joda.time._
import java.util.Date
import org.uqbar.commons.utils.Observable
import javax.persistence._
import ar.edu.utn.frba.dds.OPF5.domain.{ Jugador, Partido }

@Entity
@Table(name = "infraccion")
@Observable
class Infraccion(mt: String, jd: Jugador) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_infraccion")
  var id: Long = _

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "dia")
  val dia: Date = new Date()

  @Column(name = "motivo")
  val motivo = mt

  @ManyToOne
  @JoinColumn(name = "id_jugador")
  val jugador = jd
  
  private def this() = this(null, null)

  def fecha = new DateTime(dia).toString("dd-MM-yyyy")

  def hora = new DateTime(dia).toString("hh:mm:ss")
}