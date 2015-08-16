package ar.edu.utn.frba.dds.OPF5.dao

import scala.collection.mutable.ArrayBuffer

import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.condicion._
import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._
import ar.edu.utn.frba.dds.OPF5.domain.orden._
import ar.edu.utn.frba.dds.OPF5.domain.division._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.util.Random

object Fixture {

  def insertarAdministrador() {
    AdminDAO.save(new Administrador("Nelson", "nelson@opf5.com.ar"))
  }

  def insertarPartidos() {
    val admin = AdminDAO.byName("Nelson")
    val partidos = Array(
      new Partido(admin, "Fútbol Madero", new Permisivo, "2014-11-30T18:00:00"),
      new Partido(admin, "Fútbol Vieytes", new ParaMayoresDeN(20), "2014-11-30T18:00:00"),
      new Partido(admin, "Barrio Parque Fútbol Club", new ParaMaxNJugadoresConLetra(5, "E"), "2014-11-30T18:00:00"),
      new Partido(admin, "Fútbol Urbano", new ParaJugadoresConApodoDeTamanio(5), "2014-12-06T16:00:00"))
    PartidoDAO.save(partidos.toList)
  }

  def insertarJugadores() {
    val jugadores = Array(
      new Jugador("Elias", "El pibe", "1981-01-04", "elias81@gmail.com"),
      new Jugador("Emanuel", "Manos", "1986-02-05", "emanuel86@inbox.com"),
      new Jugador("Elmer", "Fudd", "1982-04-02", "elmer82@yandex.com"),
      new Jugador("Emilio", "Millo", "1989-03-05", "emilio89@yahoo.com"),
      new Jugador("Eric", "Ricky", "1990-03-03", "eric90@hotmail.com"),
      new Jugador("Ernesto", "El Che", "1985-04-05", "ernesto85@zoho.com"),
      new Jugador("Esteban", "Quito", "1990-09-09", "esteban90@live.com"),
      new Jugador("Ezequiel", "Zeke", "1986-01-09", "ezequiel86@aol.com"),
      new Jugador("Eddie", "Doble D", "1989-04-03", "eddie89@mail.com"),
      new Jugador("Edison", "El inventor", "1986-03-09", "edison86@gmx.com"))
    JugadorDAO.save(jugadores.toList)
  }

  def asignarHandicaps() {
    val jugadores = JugadorDAO.allInstances
    val admin = AdminDAO.byName("Nelson")
    for (j <- jugadores)
      admin.asignarHandicap(j, Math.abs(new Random().nextInt % 10) + 1)
    JugadorDAO.update(jugadores)
  }

  def generarAmistades() {
    val jugadores = JugadorDAO.allInstances
    val partidos = PartidoDAO.allInstances

    for (j <- jugadores; j0 <- jugadores)
      if (j != j0)
        j.agregarAmigo(j0)

    JugadorDAO.update(jugadores)
  }

  def inscribirJugadores() {
    val partidos = PartidoDAO.allInstances
    val jugadores = JugadorDAO.allInstances

    for (j <- jugadores; p <- partidos)
      j.inscribirse(p, new Estandar)

    JugadorDAO.update(jugadores)
    PartidoDAO.update(partidos)
  }

  def generarInfracciones() {
    val partidos = PartidoDAO.allInstances
    val jugadores = JugadorDAO.allInstances

    for (p <- partidos.slice(0, 2); j <- p.jugadores.slice(0, 4)) {
      j.bajarse(p)
      j.inscribirse(p, new Solidaria)
    }

    JugadorDAO.update(jugadores)
    PartidoDAO.update(partidos)
  }

  def cerrarInscripciones() {
    val admin = AdminDAO.byName("Nelson")
    val partidos = PartidoDAO.allInstances

    for (p <- partidos) {
      admin.finalizarInscripcion(p)
      admin.organizarJugadores(p, new CriterioHandicap)
      admin.generarEquiposTentativos(p, new Criterio14589)
      admin.confirmarEquipos(p)
    }

    PartidoDAO.update(partidos)
  }

  def calificarJugadores() {
    val partidos = PartidoDAO.allInstances

    for (p <- partidos; j <- p.jugadores; j0 <- p.jugadores)
      if (j != j0)
        j.calificar(p, j0, Math.abs(new Random().nextInt % 10) + 1, "Calificación autogenerada.")

    PartidoDAO.update(partidos)
  }

  def generarNuevoPartido() {
    val admin = AdminDAO.byName("Nelson")
    val jugadores = JugadorDAO.allInstances
    val partido = new Partido(admin, "Salguero Fútbol", new Permisivo, "2014-12-15T18:00:00")
    val jugador1 = new Jugador("Sebastian", "Seba", "1990-01-04", "seba90@facebook.com")
    val jugador2 = new Jugador("Carlos", "Carlitos", "1993-01-04", "carlos93@gmail.com")
    jugador1.handicap = 8
    jugador2.handicap = 7
    
    JugadorDAO.save(jugador1)
    JugadorDAO.save(jugador2)
    jugador1.inscribirse(partido, new Solidaria)
    jugador2.inscribirse(partido, new Condicional)
    jugador1.agregarAmigo(jugador2)
    jugador2.agregarAmigo(jugador1)
    
    for (j <- jugadores)
      j.inscribirse(partido, new Estandar)

    PartidoDAO.save(partido)
    JugadorDAO.update(jugadores)
    JugadorDAO.update(jugador1)
    JugadorDAO.update(jugador2)
  }

  def main(args: Array[String]) {
    insertarAdministrador()
    insertarPartidos()
    insertarJugadores()
    asignarHandicaps()
    generarAmistades()
    inscribirJugadores()
    generarInfracciones()
    cerrarInscripciones()
    calificarJugadores()
    generarNuevoPartido()
    println("Done!")
  }

}