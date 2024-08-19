package com.example.taller1.Logica

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("name")
    var cityName: String? = null,

    @SerializedName("main")
    var main: Main? = null,

    @SerializedName("wind")
    var wind: Wind? = null,

    @SerializedName("weather")
    var weather: List<Weather>? = null,

    @SerializedName("clouds")
    var clouds: Clouds? = null,

    @SerializedName("visibility")
    var visibility: Int? = null,

    @SerializedName("dt")
    var timestamp: Long? = null
)

data class Main(
    @SerializedName("temp")
    var temp: Double? = null,

    @SerializedName("feels_like")
    var feelsLike: Double? = null,

    @SerializedName("pressure")
    var pressure: Int? = null,

    @SerializedName("humidity")
    var humidity: Int? = null
)

data class Wind(
    @SerializedName("speed")
    var speed: Double? = null,

    @SerializedName("deg")
    var deg: Int? = null,

    @SerializedName("gust")
    var gust: Double? = null
)

data class Weather(
    @SerializedName("description")
    var description: String? = null,

    @SerializedName("icon")
    var icon: String? = null
)

data class Clouds(
    @SerializedName("all")
    var all: Int? = null
)
