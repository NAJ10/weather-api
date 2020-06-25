package com.gamma

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.gamma.db.IssueRepository
import com.gamma.weatherapi._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Future

class ApiRoutesSpec extends AnyFlatSpec with Matchers with ScalaFutures with SprayJsonSupport with ScalatestRouteTest {
  import JsonFormats._
  override def createActorSystem(): akka.actor.ActorSystem = ActorSystem("Test")

  private var last : Option[Issue] = None
  private val issueRepositoryStub = new IssueRepository {
    override def createIssue(issue: Issue): Unit = last = Some(issue)
  }

  private val stubbedResponse = ForecastResponse(
    "cod",
    200,
    0,
    List.empty[Forecast],
    City(0,
      "Swansea",
      Coord(52.0,0.0),
      "GB",
      1000000,
      3600,
      1,2
    )
  )

  private val weatherClientStub = new WeatherClient {
    override def forecast(city: String): Future[ForecastResponse] = Future.successful(
      stubbedResponse
    )
  }

  private val routes = new ApiRoutes(weatherClientStub, issueRepositoryStub).routes

  "api" should "respond to query" in {
    val request = HttpRequest(uri = "/weather/Cardiff")

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      contentType should ===(ContentTypes.`application/json`)
      entityAs[ForecastResponse] shouldBe stubbedResponse

    }
  }

  it should "process issue request" in {
    val issue = Issue("A", "B", "C")
    val issueEntity = Marshal(issue).to[MessageEntity].futureValue

    val request = Post("/issue").withEntity(issueEntity)

    request ~> routes ~> check {
      status should ===(StatusCodes.Created)
      last should ===(Some(issue))
    }
  }
}
