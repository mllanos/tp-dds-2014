package ar.edu.utn.frba.dds.OPF5.domain.condicion

import scala.collection.mutable.ArrayBuffer
import ar.edu.utn.frba.dds.OPF5.domain.Jugador
import javax.persistence._

@Entity
@Table(name = "condicion")
@DiscriminatorColumn(name = "tipo_condicion", discriminatorType = DiscriminatorType.STRING)
abstract class Condicion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_condicion")
  var id: Long = _

  def apply(jugadores: ArrayBuffer[Jugador]): Boolean
}
