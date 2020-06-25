package com.gamma

import java.io.PrintWriter
import java.sql.Connection
import java.util.Properties

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import javax.sql.DataSource
import org.flywaydb.core.Flyway

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

package object db {
  def dataSource(username: String, password: String): DataSource = {
    val props = new Properties()
    props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
    props.setProperty("dataSource.user", username)
    props.setProperty("dataSource.password", password)
    props.put("dataSource.logWriter", new PrintWriter(System.out))

    new HikariDataSource(new HikariConfig(props))
  }

  def migrate(ds: DataSource): Int = Flyway.configure().dataSource(ds).load().migrate()

  def withConnection[T](ds: DataSource)(implicit fn: Connection => T): Try[T] = {
    var conn: Connection = null
    try {
      conn = ds.getConnection
      val result = fn(conn)
      Success(result)
    } catch {
      case NonFatal(e) =>
        Failure(e)
    } finally {
      if(conn!=null) {
        try {
          conn.close()
          conn = null
        } catch {
          case NonFatal(_) =>
        }
      }
    }
  }
}
