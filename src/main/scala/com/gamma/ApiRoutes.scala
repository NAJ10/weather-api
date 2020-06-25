package com.gamma

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.gamma.db.IssueRepository
import com.gamma.weatherapi.{ForecastResponse, WeatherClient}
import spray.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class ApiRoutes(client: WeatherClient, issueRepository: IssueRepository)(implicit val system: ActorSystem, ec: ExecutionContext) extends DefaultJsonProtocol {
  import JsonFormats._

  def getWeather(name: String): Future[ForecastResponse] = client.forecast(name)

  def insertIssue(issue: Issue) : Future[Unit] = Future(issueRepository.createIssue(issue))

  val routes : Route = concat(
    pathPrefix("weather") {
      path(Segment) {  name =>
        onComplete(getWeather(name)) {
          case Success(value) => complete(value)
          case Failure(ex)    => complete((StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}"))
        }
      }
    },
    pathPrefix("issue") {
      post {
        decodeRequest {
          entity(as[Issue]) { issue =>
            onComplete(insertIssue(issue)) {
              case Success(_) => complete(StatusCodes.Created)
              case Failure(ex)    => complete((StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}"))
            }

          }
        }
      }
    }
  )
}
