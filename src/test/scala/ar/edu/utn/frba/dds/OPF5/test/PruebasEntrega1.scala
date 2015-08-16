package ar.edu.utn.frba.dds.OPF5.test

import ar.edu.utn.frba.dds.OPF5.domain.exceptions._
import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._

class PruebasEntrega1 extends UnitSpec {

  "A player" should "be able to join a game." in new Fixture {
    jugador1.inscribirse(partidoPermisivo, new Estandar)

    partidoPermisivo.jugadores should contain(jugador1)
  }

  "A game" should "be empty on startup." in new Fixture {

    partidoPermisivo.jugadores should be('empty)
  }

  it should "allow 10 standard players at most." in new Fixture {
    for (j <- jugadores) {
      j.inscribirse(partidoPermisivo, new Estandar)
    }

    a[PartidoCompletoException] should be thrownBy {
      jugadorExtra.inscribirse(partidoPermisivo, new Estandar)
    }

    partidoPermisivo.jugadores.size should be(10)
  }

  it should "block players on condition." in new Fixture {

    a[NoCumpleCondicionException] should be thrownBy {
      jugadorJoven.inscribirse(partidoParaMayoresDe20, new Condicional)
    }

    partidoParaMayoresDe20.jugadores should not contain (jugadorJoven)
  }

  it should "allow a number of players on condition." in new Fixture {
    for (j <- jugadores.slice(0, 5))
      j.inscribirse(partidoParaMax5JugadoresConE, new Condicional)

    partidoParaMax5JugadoresConE.jugadores.size should be(5)

    a[NoCumpleCondicionException] should be thrownBy {
      jugador6.inscribirse(partidoParaMax5JugadoresConE, new Condicional)
    }

    partidoParaMax5JugadoresConE.jugadores should not contain (jugador6)
  }

  it should "allow a tenth standard player to join with 9 standards and a solidary." in new Fixture {
    for (j <- jugadores.slice(0, 9))
      j.inscribirse(partidoPermisivo, new Estandar)

    jugador10.inscribirse(partidoPermisivo, new Solidaria)

    jugadorExtra.inscribirse(partidoPermisivo, new Estandar)

    partidoPermisivo.jugadores should contain(jugadorExtra)
  }

  it should "allow a tenth standard player to join with 9 standards and a conditional." in new Fixture {
    for (j <- jugadores.slice(0, 9)) {
      j.inscribirse(partidoPermisivo, new Estandar)
    }

    jugador10.inscribirse(partidoPermisivo, new Condicional)
    jugadorExtra.inscribirse(partidoPermisivo, new Estandar)

    partidoPermisivo.jugadores should contain(jugadorExtra)
  }

}