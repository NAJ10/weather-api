package com.gamma

import com.gamma.weatherapi._
import spray.json.RootJsonFormat

import spray.json.DefaultJsonProtocol

object JsonFormats  {
  import DefaultJsonProtocol._

  implicit val detailsJsonFormat: RootJsonFormat[Details] = jsonFormat9(Details)
  implicit val weatherJsonFormat: RootJsonFormat[Weather] = jsonFormat4(Weather)
  implicit val cloudsJsonFormat: RootJsonFormat[Clouds] = jsonFormat1(Clouds)
  implicit val windJsonFormat: RootJsonFormat[Wind] = jsonFormat2(Wind)
  implicit val forecastJsonFormat: RootJsonFormat[Forecast] = jsonFormat5(Forecast)
  implicit val coordJsonFormat: RootJsonFormat[Coord] = jsonFormat2(Coord)
  implicit val cityJsonFormat: RootJsonFormat[City] = jsonFormat8(City)
  implicit val forecastResponseJsonFormat: RootJsonFormat[ForecastResponse] = jsonFormat5(ForecastResponse)
  implicit val issueJsonFormat: RootJsonFormat[Issue] = jsonFormat3(Issue)
}
