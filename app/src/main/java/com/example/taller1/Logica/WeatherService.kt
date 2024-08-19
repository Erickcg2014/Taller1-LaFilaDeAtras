package com.example.taller1.Logica

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("mode") mode: String = "xml",
        @Query("units") units: String = "metric"
    ): Call<WeatherResponse>
}
