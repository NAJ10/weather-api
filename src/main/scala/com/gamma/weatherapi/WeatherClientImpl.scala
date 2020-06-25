package com.gamma.weatherapi

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling._
import akka.stream.Materializer
import com.gamma.JsonFormats._

import scala.concurrent.{ExecutionContext, Future}

class WeatherClientImpl(apiKey: String)(implicit val ec: ExecutionContext, sys: ActorSystem, mat: Materializer) extends WeatherClient {

  override def forecast(city: String): Future[ForecastResponse] = Http()
    .singleRequest(HttpRequest(uri=s"https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=$apiKey&units=metric"))
    .flatMap( response => Unmarshal(response.entity).to[ForecastResponse])
}
