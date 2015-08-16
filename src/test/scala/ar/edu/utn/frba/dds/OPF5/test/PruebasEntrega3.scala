package ar.edu.utn.frba.dds.OPF5.test

import ar.edu.utn.frba.dds.OPF5.domain.Administrador
import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._
import ar.edu.utn.frba.dds.OPF5.domain.command._
import scala.collection.JavaConversions._

class PruebasEntrega3 extends UnitSpec {

  "An administrator" should "be able to allow a proposed player into a game." in new Fixture {

    val propuesta = jugador1.proponer(admin, partidoPermisivo, jugador2, new Estandar)

    admin.considerarPropuesta(propuesta, new AceptarJugador)

    partidoPermisivo.jugadores should contain(jugador2)
    jugador2.inscripciones.find(_.partido == partidoPermisivo).get.isInstanceOf[Estandar] should be(true)
  }

  it should "be able to disallow a proposed player into a game." in new Fixture {

    val propuesta = jugador1.proponer(admin, partidoPermisivo, jugador2, new Estandar)

    admin.considerarPropuesta(propuesta, new DenegarJugador("Denegación de prueba."))

    partidoPermisivo.jugadores should not contain (jugador2)
  }

  "A player" should "be able to rate another player." in new Fixture {
    
    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Solidaria)
      
    admin.finalizarInscripcion(partidoPermisivo)
    
    jugador1.calificar(partidoPermisivo, jugador2, 10, "Excelente.")

    jugador2.calificaciones.size should be(1)
  }
}