package ar.edu.utn.frba.dds.OPF5.dao

import ar.edu.utn.frba.dds.OPF5.domain._
import ar.edu.utn.frba.dds.OPF5.domain.condicion._
import ar.edu.utn.frba.dds.OPF5.domain.informes._
import ar.edu.utn.frba.dds.OPF5.domain.inscripcion._
import ar.edu.utn.frba.dds.OPF5.domain.mail._
import org.hibernate.cfg.AnnotationConfiguration

object Repository {

  private val sessionFactory = new AnnotationConfiguration()
    .configure()
    .addAnnotatedClass(classOf[Jugador])
    .addAnnotatedClass(classOf[Partido])
    .addAnnotatedClass(classOf[Condicion])
    .addAnnotatedClass(classOf[ParaJugadoresConApodoDeTamanio])
    .addAnnotatedClass(classOf[ParaMaxNJugadoresConLetra])
    .addAnnotatedClass(classOf[ParaMayoresDeN])
    .addAnnotatedClass(classOf[Permisivo])
    .addAnnotatedClass(classOf[Calificacion])
    .addAnnotatedClass(classOf[Denegacion])
    .addAnnotatedClass(classOf[Infraccion])
    .addAnnotatedClass(classOf[Propuesta])
    .addAnnotatedClass(classOf[Inscripcion])
    .addAnnotatedClass(classOf[Estandar])
    .addAnnotatedClass(classOf[Solidaria])
    .addAnnotatedClass(classOf[Condicional])
    .addAnnotatedClass(classOf[Administrador])
    .buildSessionFactory()

  def getSessionFactory = sessionFactory
}