package com.gamma.weatherapi

import spray.json._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source

class JsonSpec extends AnyFlatSpec with Matchers {
  import com.gamma.JsonFormats._
  "json" should "parse" in {
    val jsonString = Source.fromInputStream(getClass.getResourceAsStream("response.json")).getLines().mkString
    val forecast = jsonString.parseJson.convertTo[ForecastResponse]

    val details = forecast.list.head.main

    details.temp shouldBe 24.54
    details.feels_like shouldBe 24.03
    details.temp_min shouldBe 24.54

    val city = forecast.city

    city.name shouldBe "London"

  }
}
