package ar.edu.utn.frba.dds.OPF5.runnable

import org.uqbar.arena.Application
import ar.edu.utn.frba.dds.OPF5.ui._
import ar.edu.utn.frba.dds.OPF5.dao.AdminDAO

object OPF5Application extends Application with App {
  override def createMainWindow() = {
    val admin = AdminDAO.byName("Nelson")
    new OPF5Window(this, admin)
  }
  start()
}