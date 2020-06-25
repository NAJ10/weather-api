package com.gamma.weatherapi

import scala.concurrent.Future

trait WeatherClient {
  def forecast(city: String): Future[ForecastResponse]
}
