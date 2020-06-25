package com.gamma

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.gamma.weatherapi.WeatherClientImpl
import com.gamma.db._

import scala.concurrent.ExecutionContext
import scala.util.Failure
import scala.util.Success

object Main {
  private def startHttpServer(routes: Route)(implicit sys: ActorSystem, ec: ExecutionContext): Unit = {
    val futureBinding = Http().bindAndHandle(routes, "localhost", 8080)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        sys.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        sys.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        sys.terminate()
    }
  }
  def main(args: Array[String]): Unit = {
    implicit val ec = scala.concurrent.ExecutionContext.global
    implicit val sys = ActorSystem("WeatherApi", None, None, Some(ec))
    implicit val mat = ActorMaterializer()
    val apiKey = System.getenv("WEATHER_API_KEY")
    if(apiKey == null) {
      System.out.println("WEATHER_API_KEY environment variable must be set. Exiting ...")
      System.exit(1)
    }
    val ds = dataSource(System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"))
    migrate(ds)

    val issueRepository = new AnormIssueRepository(ds)

    val client = new WeatherClientImpl(apiKey)

    val routes = new ApiRoutes(client, issueRepository)
    startHttpServer(routes.routes)
  }
}
