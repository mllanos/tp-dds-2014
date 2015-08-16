package ar.edu.utn.frba.dds.OPF5.dao

import org.hibernate.{ Session, Transaction, Query }
import java.util.List
import scala.collection.JavaConversions._

trait DAO[T] {

  def save(obj: T) {
    var session: Session = null
    var tx: Transaction = null

    try {
      session = Repository.getSessionFactory.openSession()
      tx = session.beginTransaction
      session.save(obj)
      tx.commit()
    } catch {
      case e: Exception =>
        tx.rollback()
        throw e
    } finally {
      if (session != null)
        session.close()
    }
  }

  def save(list: List[T]) {
    var session: Session = null
    var tx: Transaction = null

    try {
      session = Repository.getSessionFactory.openSession()
      tx = session.beginTransaction
      for (obj <- list)
        session.save(obj)
      tx.commit()
    } catch {
      case e: Exception =>
        tx.rollback()
        throw e
    } finally {
      if (session != null)
        session.close()
    }
  }

  def update(obj: T) {
    var session: Session = null
    var tx: Transaction = null

    try {
      session = Repository.getSessionFactory.openSession()
      tx = session.beginTransaction
      session.update(obj)
      tx.commit()
    } catch {
      case e: Exception =>
        tx.rollback()
        throw e
    } finally {
      if (session != null)
        session.close()
    }
  }

  def update(list: List[T]) {
    var session: Session = null
    var tx: Transaction = null

    try {
      session = Repository.getSessionFactory.openSession()
      tx = session.beginTransaction
      for (obj <- list)
        session.update(obj)
      tx.commit()
    } catch {
      case e: Exception =>
        tx.rollback()
        throw e
    } finally {
      if (session != null)
        session.close()
    }
  }

  def find(table: String, args: Array[String] = Array[String]()) = {
    var sql = "from " + table

    if (args.size > 0) {
      sql += " where " + args(0)

      for (str <- args.slice(1, args.size))
        sql += " and " + str
    }

    query(sql)
  }

  def findUnique(table: String, args: Array[String] = Array[String](), orderBy: String = null) = {
    var sql = "from " + table

    if (args.size > 0) {
      sql += " where " + args(0)

      for (str <- args.slice(1, args.size))
        sql += " and " + str
    }

    if (orderBy != null)
      sql += " " + orderBy

    var session: Session = null
    var tx: Transaction = null
    var query: Query = null

    try {
      session = Repository.getSessionFactory.openSession()
      tx = session.beginTransaction()
      query = session.createQuery(sql).setMaxResults(1)
      tx.commit()
      query.uniqueResult().asInstanceOf[T]

    } catch {
      case e: Exception =>
        tx.rollback()
        throw e
    } finally {
      if (session != null)
        session.close()
    }
  }

  private def query(sql: String): List[T] = {
    var session: Session = null
    var tx: Transaction = null
    var query: Query = null

    try {
      session = Repository.getSessionFactory.openSession()
      tx = session.beginTransaction()
      query = session.createQuery(sql)
      tx.commit()
      query.list().asInstanceOf[List[T]]
    } catch {
      case e: Exception =>
        tx.rollback()
        throw e
    } finally {
      if (session != null)
        session.close()
    }
  }

}