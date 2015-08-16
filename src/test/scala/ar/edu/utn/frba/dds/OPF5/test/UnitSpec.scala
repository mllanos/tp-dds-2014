package ar.edu.utn.frba.dds.OPF5.test

import scala.collection.mutable.ArrayBuffer

import org.scalatest._

import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.division._
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import ar.edu.utn.frba.dds.OPF5.domain.condicion._
import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._

abstract class UnitSpec extends FlatSpec with Matchers with OptionValues with Inside with Inspectors {

  trait Fixture {

    val admin = new Administrador("Nelson", "nelson@opf5.com.ar")

    val partidoPermisivo = new Partido(admin, "San Carlos Club", new Permisivo, "2014-11-30T18:00:00")
    val partidoParaMayoresDe20 = new Partido(admin, "Nikkei Fútbol 5", new ParaMayoresDeN(20), "2014-11-30T18:00:00")
    val partidoParaMax5JugadoresConE = new Partido(admin, "City Stadium", new ParaMaxNJugadoresConLetra(5, "E"), "2014-11-30T18:00:00")
    val partidoParaApodoMaximo5Caracteres = new Partido(admin, "Campus Sport Arena", new ParaJugadoresConApodoDeTamanio(5), "2014-12-06T16:00:00")

    val jugador1 = new Jugador("Elias", "El pibe", "1981-01-04", "elias81@gmail.com")
    val jugador2 = new Jugador("Emanuel", "Manos", "1986-02-05", "emanuel86@inbox.com")
    val jugador3 = new Jugador("Elmer", "Fudd", "1982-04-02", "elmer82@yandex.com")
    val jugador4 = new Jugador("Emilio", "Millo", "1989-03-05", "emilio89@yahoo.com")
    val jugador5 = new Jugador("Eric", "Ricky", "1990-03-03", "eric90@hotmail.com")
    val jugador6 = new Jugador("Ernesto", "El Che", "1985-04-05", "ernesto85@zoho.com")
    val jugador7 = new Jugador("Esteban", "Quito", "1990-09-09", "esteban90@live.com")
    val jugador8 = new Jugador("Ezequiel", "Zeke", "1986-01-09", "ezequiel86@aol.com")
    val jugador9 = new Jugador("Eddie", "Doble D", "1989-04-03", "eddie89@mail.com")
    val jugador10 = new Jugador("Edison", "El inventor", "1986-03-09", "edison86@gmx.com")
    val jugadorExtra = new Jugador("Eduardo", "Edu", "1980-05-25", "eduardo80@opera.com")
    val jugadorJoven = new Jugador("Carlos", "Carlitos", "1996-03-05", "carlos96@hushmail.com")

    val jugadores = new ArrayBuffer[Jugador]
    jugadores += (jugador1, jugador2, jugador3, jugador4, jugador5, jugador6, jugador7, jugador8, jugador9, jugador10)
  }

  trait FixtureHandicap extends Fixture {
    val partidoParaHandicap = new Partido(admin, "San José Fútbol", new Permisivo, "2014-11-30T12:00:00")

    for (i <- 1 to 10) {
      admin.asignarHandicap(jugadores(i - 1), i)
    }

    /* HANDICAPS ASIGNADOS
      * JugadorN: N, N: 1..10
      */
  }

  trait FixtureUltimoJuego extends Fixture {
    val ultimoJuegoJugado = new Partido(admin, "Nueva Generación", new Permisivo, "2014-11-30T12:00:00")

    for (j <- jugadores)
      j.inscribirse(ultimoJuegoJugado, new Estandar)

    admin.finalizarInscripcion(ultimoJuegoJugado)

    for (i <- 1 to 10)
      for (oj <- jugadores)
        if (jugadores(i - 1) != oj)
          jugadores(i - 1).calificar(ultimoJuegoJugado, oj, i, "Critica test.")

    /* CALIFICACIONES PARTIDOPARAULTIMOJUEGO
      * Jugador1: sumatoria 54, cantidad 9, promedio 6.00
      * Jugador2: sumatoria 53, cantidad 9, promedio 5.88
      * Jugador3: sumatoria 52, cantidad 9, promedio 5.77
      * Jugador4: sumatoria 51, cantidad 9, promedio 5.66
      * Jugador5: sumatoria 50, cantidad 9, promedio 5.55
      * Jugador6: sumatoria 49, cantidad 9, promedio 5.44
      * Jugador7: sumatoria 48, cantidad 9, promedio 5.33
      * Jugador8: sumatoria 47, cantidad 9, promedio 5.22
      * Jugador9: sumatoria 46, cantidad 9, promedio 5.11
      * Jugador10: sumatoria 45, cantidad 9, promedio 5.00
      * */
  }

  trait FixtureUltimoJuego2 extends Fixture {
    val ultimoJuegoJugado2 = new Partido(admin, "La Plaza Fútbol", new Permisivo, "2014-11-30T12:00:00")

    for (j <- jugadores)
      j.inscribirse(ultimoJuegoJugado2, new Estandar)

    admin.finalizarInscripcion(ultimoJuegoJugado2)

    jugador1.calificar(ultimoJuegoJugado2, jugador2, 10, "Re bien.")
    jugador1.calificar(ultimoJuegoJugado2, jugador4, 10, "Re bien.")
    jugador1.calificar(ultimoJuegoJugado2, jugador6, 10, "Re bien.")
    jugador1.calificar(ultimoJuegoJugado2, jugador8, 10, "Re bien.")
    jugador1.calificar(ultimoJuegoJugado2, jugador10, 10, "Re bien.")
    jugador5.calificar(ultimoJuegoJugado2, jugador1, 6, "Puede mejorar.")
    jugador7.calificar(ultimoJuegoJugado2, jugador3, 1, "Terrible.")

    /* CALIFICACIONES PARTIDOPARAULTIMOJUEGO2
      * Jugador1: sumatoria 6, cantidad 1, promedio 6.00
      * Jugador2: sumatoria 10, cantidad 1, promedio 10.00
      * Jugador3: sumatoria 1, cantidad 1, promedio 1.00
      * Jugador4: sumatoria 10, cantidad 1, promedio 10.00
      * Jugador5: sumatoria 0, cantidad 0, promedio 0.00
      * Jugador6: sumatoria 10, cantidad 1, promedio 10.00
      * Jugador7: sumatoria 0, cantidad 0, promedio 0.00
      * Jugador8: sumatoria 10, cantidad 1, promedio 10.00
      * Jugador9: sumatoria 0, cantidad 0, promedio 0.00
      * Jugador10: sumatoria 10, cantidad 1, promedio 10.00
      * */
  }

  trait Fixture2JuegosTerminados extends FixtureUltimoJuego with FixtureUltimoJuego2 {

    /* CALIFICACIONES DE LOS ULTIMOS 2 PARTIDOS
      * Jugador1: sumatoria 60, cantidad 10, promedio 6.00
      * Jugador2: sumatoria 63, cantidad 10, promedio 6.30
      * Jugador3: sumatoria 53, cantidad 10, promedio 5.30
      * Jugador4: sumatoria 61, cantidad 10, promedio 6.10
      * Jugador5: sumatoria 50, cantidad 9, promedio 5.55
      * Jugador6: sumatoria 59, cantidad 10, promedio 5.90
      * Jugador7: sumatoria 48, cantidad 9, promedio 5.33
      * Jugador8: sumatoria 57, cantidad 10, promedio 5.70
      * Jugador9: sumatoria 46, cantidad 9, promedio 5.11
      * Jugador10: sumatoria 55, cantidad 10, promedio 5.50
      * */

  }

  trait FixtureHandicapUltimoJuego extends FixtureHandicap with FixtureUltimoJuego {

    val criterios = new ArrayBuffer[CriterioOrden] +=
      (new CriterioHandicap, new CriterioPromedioUltimoPartido)

    /* VALORES A CONSIDERAR
      * Jugador1: handicap 1, promedio 6.00 => promedio final: 3.5
      * Jugador2: handicap 2, promedio 5.88 => promedio final: 3.94
      * Jugador3: handicap 3, promedio 5.77 => promedio final: 4.385
      * Jugador4: handicap 4, promedio 5.66 => promedio final: 4.83
      * Jugador5: handicap 5, promedio 5.55 => promedio final: 5.275
      * Jugador6: handicap 6, promedio 5.44 => promedio final: 5.72
      * Jugador7: handicap 7, promedio 5.33 => promedio final: 6.165
      * Jugador8: handicap 8, promedio 5.22 => promedio final: 6.609
      * Jugador9: handicap 9, promedio 5.11 => promedio final: 7.055
      * Jugador10: handicap 10, promedio 5.00 => promedio final: 7.5
      * */
  }

  trait FixtureHandicapUltimos2Juegos extends FixtureHandicap with Fixture2JuegosTerminados {

    val criterios = new ArrayBuffer[CriterioOrden] +=
      (new CriterioHandicap, new CriterioPromedioNPartidos().setN(2))

    /* VALORES A CONSIDERAR
      * Jugador1: handicap 1, promedio 6.00 => promedio final: 3.5
      * Jugador2: handicap 2, promedio 6.30 => promedio final: 4.15
      * Jugador3: handicap 3, promedio 5.30 => promedio final: 4.15
      * Jugador4: handicap 4, promedio 6.10 => promedio final: 5.05
      * Jugador5: handicap 5, promedio 5.55 => promedio final: 5.275
      * Jugador6: handicap 6, promedio 5.90 => promedio final: 5.95
      * Jugador7: handicap 7, promedio 5.33 => promedio final: 6.165
      * Jugador8: handicap 8, promedio 5.70 => promedio final: 6.85
      * Jugador9: handicap 9, promedio 5.11 => promedio final: 7.055
      * Jugador10: handicap 10, promedio 5.50 => promedio final: 7.75
      * */
  }

}