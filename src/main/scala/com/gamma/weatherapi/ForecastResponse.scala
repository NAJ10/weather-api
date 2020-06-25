package com.gamma.weatherapi

case class Details (
  temp: Double,
  feels_like: Double,
  temp_min: Double,
  temp_max: Double,
  pressure: Int,
  sea_level: Int,
  grnd_level: Int,
  humidity: Int,
  temp_kf: Double
)

case class Weather(
  id: Int,
  main: String,
  description: String,
  icon: String
)

case class Clouds(all: Int)

case class Wind(speed: Double, deg: Int)

case class Forecast(
  dt: Long,
  main: Details,
  weather: Array[Weather],
  clouds: Clouds,
  wind: Wind
)

case class Coord(lat: Double, lon: Double)

case class City(
  id: Int,
  name: String,
  coord: Coord,
  country: String,
  population: Long,
  timezone: Int,
  sunrise: Long,
  sunset: Long
)

case class ForecastResponse(
  cod: String,
  message: Int,
  cnt: Int,
  list: List[Forecast],
  city: City
)
