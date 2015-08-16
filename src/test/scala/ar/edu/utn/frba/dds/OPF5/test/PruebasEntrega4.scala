package ar.edu.utn.frba.dds.OPF5.test

import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._
import ar.edu.utn.frba.dds.OPF5.domain.division._
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import ar.edu.utn.frba.dds.OPF5.domain.exceptions._
import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import ar.edu.utn.frba.dds.OPF5.domain.Administrador

class PruebasEntrega4 extends UnitSpec {

  "A player" should "not be able to join a confirmed game." in new Fixture {

    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Estandar)

    admin.finalizarInscripcion(partidoPermisivo)

    a[PartidoConfirmadoException] should be thrownBy {
      jugadorExtra.inscribirse(partidoPermisivo, new Estandar)
    }
  }

  it should "not be able to leave a confirmed game." in new Fixture {
    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Estandar)

    admin.finalizarInscripcion(partidoPermisivo)

    a[PartidoConfirmadoException] should be thrownBy {
      jugador10.bajarse(partidoPermisivo)
    }
  }

  "A game" should "be able to organize its groups by the parity criteria." in new Fixture {

    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Estandar)

    admin.finalizarInscripcion(partidoPermisivo)
    admin.generarEquiposTentativos(partidoPermisivo, new CriterioParidad)

    val equipoA = new ArrayBuffer[Jugador]
    equipoA += (jugador1, jugador3, jugador5, jugador7, jugador9)
    val equipoB = new ArrayBuffer[Jugador]
    equipoB += (jugador2, jugador4, jugador6, jugador8, jugador10)

    partidoPermisivo.equipoA.sameElements(equipoA) should be(true)
    partidoPermisivo.equipoB.sameElements(equipoB) should be(true)
  }

  it should "be able to organize its groups by the 14589 criteria." in new Fixture {

    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Estandar)

    admin.finalizarInscripcion(partidoPermisivo)
    admin.generarEquiposTentativos(partidoPermisivo, new Criterio14589)

    val equipoA = new ArrayBuffer[Jugador] +=
      (jugador1, jugador4, jugador5, jugador8, jugador9)
    val equipoB = new ArrayBuffer[Jugador] +=
      (jugador2, jugador3, jugador6, jugador7, jugador10)

    partidoPermisivo.equipoA.sameElements(equipoA) should be(true)
    partidoPermisivo.equipoB.sameElements(equipoB) should be(true)
  }

  it should "be able to organize its players by the handicap criteria." in new FixtureHandicap {

    for (j <- jugadores)
      j.inscribirse(partidoParaHandicap, new Estandar)

    admin.finalizarInscripcion(partidoParaHandicap)
    admin.organizarJugadores(partidoParaHandicap, new CriterioHandicap)

    for (i <- 1 to 10)
      partidoParaHandicap.jugadores(i - 1).handicap should be equals (jugadores.reverse(i - 1).handicap)
  }

  it should "be able to organize its players by the last game criteria." in new FixtureUltimoJuego {

    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Estandar)

    admin.finalizarInscripcion(partidoPermisivo)
    admin.organizarJugadores(partidoPermisivo, new CriterioPromedioUltimoPartido)

    val jugadoresPorCriterio = new ArrayBuffer[Jugador] +=
      (jugador1, jugador2, jugador3, jugador4, jugador5, jugador6, jugador7, jugador8, jugador9, jugador10)

    partidoPermisivo.jugadores.sameElements(jugadoresPorCriterio) should be(true)
  }

  it should "be able to organize its players by the last n games criteria (n=2)." in new Fixture2JuegosTerminados {

    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Estandar)

    admin.finalizarInscripcion(partidoPermisivo)
    admin.organizarJugadores(partidoPermisivo, new CriterioPromedioNPartidos().setN(2))

    val jugadoresPorCriterio = new ArrayBuffer[Jugador] +=
      (jugador2, jugador4, jugador1, jugador6, jugador8, jugador5, jugador10, jugador7, jugador3, jugador9)

    partidoPermisivo.jugadores.sameElements(jugadoresPorCriterio) should be(true)
  }

  it should "be able to organize its players by the mix criteria (handicap + last game)." in new FixtureHandicapUltimoJuego {

    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Estandar)

    admin.finalizarInscripcion(partidoPermisivo)
    admin.organizarJugadores(partidoPermisivo, new CriterioMix().setCriterios(criterios))

    val jugadoresPorCriterio = new ArrayBuffer[Jugador] +=
      (jugador10, jugador9, jugador8, jugador7, jugador6, jugador5, jugador4, jugador3, jugador2, jugador1)

    partidoPermisivo.jugadores.sameElements(jugadoresPorCriterio) should be(true)
  }

  it should "be able to organize its players by the mix criteria (handicap + last n games(n=2))." in new FixtureHandicapUltimos2Juegos {

    for (j <- jugadores)
      j.inscribirse(partidoPermisivo, new Estandar)

    admin.finalizarInscripcion(partidoPermisivo)
    admin.organizarJugadores(partidoPermisivo, new CriterioMix().setCriterios(criterios))

    val jugadoresPorCriterio = new ArrayBuffer[Jugador] +=
      (jugador10, jugador9, jugador8, jugador7, jugador6, jugador5, jugador4, jugador3, jugador2, jugador1)

    partidoPermisivo.jugadores.sameElements(jugadoresPorCriterio) should be(true)
  }

}