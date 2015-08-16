package ar.edu.utn.frba.dds.OPF5.dao

import org.hibernate._
import java.util._
import ar.edu.utn.frba.dds.OPF5.domain._

object AdminDAO extends DAO[Administrador] {

  def byName(name: String) = findUnique("Administrador", Array("nombre = '" + name + "'")) 
  
  def byMail(mail: String) = findUnique("Administrador", Array("mail = '" + mail + "'"))
  
  def allInstances() = find("Administrador")

}