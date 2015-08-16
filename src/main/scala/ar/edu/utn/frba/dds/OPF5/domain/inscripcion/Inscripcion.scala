package ar.edu.utn.frba.dds.OPF5.domain.inscripcion

import ar.edu.utn.frba.dds.OPF5.domain.{ Jugador, Partido }
import ar.edu.utn.frba.dds.OPF5.domain.exceptions._
import ar.edu.utn.frba.dds.OPF5.domain.informes.Infraccion
import javax.persistence._
import java.util.Date
import scala.collection.JavaConversions._

@Entity
@Table(name = "inscripcion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class Inscripcion {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "id_inscripcion")
  var id: Long = _

  @ManyToOne
  @JoinColumn(name = "id_jugador")
  var jugador: Jugador = _

  @ManyToOne
  @JoinColumn(name = "id_partido")
  var partido: Partido = _

  @Column(name = "ha_jugado")
  var haJugado: Boolean = false

  def prioridad: Int

  def esEstandar: Boolean = false

  def inscribir() {
    if (partido.partidoConfirmado)
      throw new PartidoConfirmadoException("Ya no se pueden agregar jugadores.")
    if (partido.jugadoresEstandar.size >= 10)
      throw new PartidoCompletoException("El partido ya tiene 10 jugadores estándar.")
    if (!cumpleCondicion(partido, jugador))
      throw new NoCumpleCondicionException("El jugador condicional no cumple la condicion del partido.")

    partido.jugadores += jugador

    for (o <- partido.observers)
      o.notifyAlta(jugador, partido)
  }

  def desinscribir(reemplazo: Jugador) {
    if (partido.partidoConfirmado)
      throw new PartidoConfirmadoException("Ya no se pueden quitar jugadores.")

    partido.jugadores -= jugador
    partido.jugadores += reemplazo
  }

  def desinscribir() {
    if (partido.partidoConfirmado)
      throw new PartidoConfirmadoException("Ya no se pueden quitar jugadores.")

    partido.jugadores -= jugador
    jugador.infracciones += new Infraccion("Se dio de baja sin reemplazo.", jugador)

    for (o <- partido.observers)
      o.notifyBaja(jugador, partido)
  }

  def cumpleCondicion(partido: Partido, jugador: Jugador) = true
}