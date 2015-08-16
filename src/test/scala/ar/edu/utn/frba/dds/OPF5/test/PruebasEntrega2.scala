package ar.edu.utn.frba.dds.OPF5.test

import ar.edu.utn.frba.dds.OPF5.domain.exceptions._
import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._
import ar.edu.utn.frba.dds.OPF5.domain.mail.StubMailSender
import ar.edu.utn.frba.dds.OPF5.domain.Administrador

class PruebasEntrega2 extends UnitSpec {

  "A player" should "be able to add a friend." in new Fixture {
    jugador1.agregarAmigo(jugador2)

    jugador1.amigos should contain(jugador2)
  }

  it should "be able to remove a friend." in new Fixture {
    jugador1.agregarAmigo(jugador2)
    jugador1.quitarAmigo(jugador2)

    jugador1.amigos should not contain (jugador2)
  }

  it should "be able to leave a game and offer a substitute." in new Fixture {
    jugador1.inscribirse(partidoPermisivo, new Estandar)
    jugador1.bajarse(partidoPermisivo, jugador2)

    partidoPermisivo.jugadores should contain(jugador2)
  }

  it should "get an infraction should he leave without offering a substitute." in new Fixture {
    jugador1.inscribirse(partidoPermisivo, new Estandar)
    jugador1.bajarse(partidoPermisivo)

    jugador1.infracciones.size should be(1)
  }

  it should "notify all his friends when joining a new game." in new Fixture {
    jugador1.agregarAmigo(jugador2)
    jugador1.inscribirse(partidoPermisivo, new Estandar)

    StubMailSender.casillaDe(jugador2.mail).size should be(1)
  }

  it should "be able to be notified by several friends at once." in new Fixture {
    jugador1.agregarAmigo(jugador3)
    jugador1.inscribirse(partidoPermisivo, new Estandar)
    jugador2.agregarAmigo(jugador3)
    jugador2.inscribirse(partidoPermisivo, new Estandar)

    StubMailSender.casillaDe(jugador3.mail).size should be(2)
  }

  it should "be able to notify his friends without others notifying." in new Fixture {
    jugador1.agregarAmigo(jugador2)
    jugador2.agregarAmigo(jugador3)
    jugador3.agregarAmigo(jugador4)

    val size2 = StubMailSender.casillaDe(jugador2.mail).size
    val size3 = StubMailSender.casillaDe(jugador3.mail).size

    jugador1.inscribirse(partidoPermisivo, new Estandar)
    jugador2.inscribirse(partidoPermisivo, new Estandar)

    StubMailSender.casillaDe(jugador2.mail).size should be(size2 + 1)
    StubMailSender.casillaDe(jugador3.mail).size should be(size3 + 1)
  }

  "A game" should "notify the admin if a confirmed player leaves in a fully confirmed game." in new Fixture {
    for (j <- jugadores.slice(0, 10))
      j.inscribirse(partidoPermisivo, new Estandar)

    val size = StubMailSender.casillaDe(admin.mail).size

    partidoPermisivo.jugadores should contain(jugador10)

    jugador10.bajarse(partidoPermisivo)

    StubMailSender.casillaDe(admin.mail).size should be(size + 1)
  }

}