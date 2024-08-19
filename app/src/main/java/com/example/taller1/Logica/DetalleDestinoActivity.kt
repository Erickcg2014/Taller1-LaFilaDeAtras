package com.example.taller1.Logica

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetalleDestinoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_destino)

        // Referencias a los elementos de la interfaz
        val txtNombreDestino: TextView = findViewById(R.id.txt_nombre_destino)
        val txtPaisDestino: TextView = findViewById(R.id.txt_pais_destino)
        val txtCategoriaDestino: TextView = findViewById(R.id.txt_categoria_destino)
        val txtPlanDestino: TextView = findViewById(R.id.txt_plan_destino)
        val txtPrecioDestino: TextView = findViewById(R.id.txt_precio_destino)
        val txtClimaDestino: TextView = findViewById(R.id.txt_clima_destino) // Añadido para el clima
        val btnAgregarFavoritos: Button = findViewById(R.id.btn_agregar_favoritos)
        val btnAtras: Button = findViewById(R.id.btn_atras)

        // Recuperar los datos del intent
        val nombreDestino = intent.getStringExtra("nombre_destino")
        val paisDestino = intent.getStringExtra("pais_destino")
        val categoriaDestino = intent.getStringExtra("categoria_destino")
        val planDestino = intent.getStringExtra("plan_destino")
        val precioDestino = intent.getStringExtra("precio_destino")

        // Mostrar los detalles del destino en la interfaz
        txtNombreDestino.text = nombreDestino
        txtPaisDestino.text = paisDestino
        txtCategoriaDestino.text = categoriaDestino
        txtPlanDestino.text = planDestino
        txtPrecioDestino.text = "USD $precioDestino"

        // Configurar el botón para agregar a favoritos
        btnAgregarFavoritos.setOnClickListener {
            val destino = mapOf(
                "nombre" to nombreDestino,
                "pais" to paisDestino,
                "categoria" to categoriaDestino,
                "plan" to planDestino,
                "precio" to precioDestino
            )

            // Verificar si el destino ya está en favoritos
            if (!MainActivity.listaFavoritos.any { it["nombre"] == nombreDestino }) {
                MainActivity.listaFavoritos.add(destino as Map<String, String>)
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                btnAgregarFavoritos.isEnabled = false // Desactivar el botón para evitar múltiples adiciones
            } else {
                Toast.makeText(this, "Este destino ya está en tus favoritos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el botón "Atrás" para regresar a la actividad anterior
        btnAtras.setOnClickListener {
            finish()  // Cierra la actividad actual y regresa a la anterior
        }

        // Llamar a la API para obtener el clima, especificando la ciudad correspondiente
        val cityForWeather = getCityForWeatherQuery(nombreDestino)
        obtenerClimaActual(cityForWeather, txtClimaDestino)
    }

    private fun obtenerClimaActual(city: String, txtClimaDestino: TextView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())  // Usar Gson para JSON
            .build()

        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeather(city, "56604cef2882a648df227dd432b77233", "json", "metric") // Solicita JSON

        Log.d("DetalleDestinoActivity", "Llamando a la API de clima para $city")

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    Log.d("DetalleDestinoActivity", "Respuesta de la API (JSON): $weather")

                    if (weather != null) {
                        txtClimaDestino.text = "Clima actual: ${weather.main?.temp}°C, ${weather.weather?.get(0)?.description}"
                    } else {
                        txtClimaDestino.text = "No se pudo obtener el clima (respuesta nula)"
                        Log.e("DetalleDestinoActivity", "Respuesta nula de la API")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    txtClimaDestino.text = "No se pudo obtener el clima"
                    Log.e("DetalleDestinoActivity", "Error en la respuesta de la API: $errorBody")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                txtClimaDestino.text = "Error al obtener el clima"
                Log.e("DetalleDestinoActivity", "Fallo al llamar a la API", t)
            }
        })
    }

    // Método auxiliar para mapear los destinos a ciudades para la consulta del clima
    private fun getCityForWeatherQuery(destino: String?): String {
        return when (destino) {
            "Baia do Sancho" -> "Fernando de Noronha, BR"
            "Eagle Beach" -> "Oranjestad, AW"
            "El Nido" -> "El Nido, PH"
            "Monte Everest" -> "Lukla, NP"
            "Mont Blanc" -> "Chamonix, FR"
            "Aconcagua" -> "Mendoza, AR"
            "Machu Picchu" -> "Cusco, PE"
            "Roma" -> "Rome, IT"
            "Petra" -> "Wadi Musa, JO"
            "Gran Muralla China" -> "Beijing, CN"
            "Taj Mahal" -> "Agra, IN"
            "Pirámides de Giza" -> "Giza, EG"
            "Amazonas" -> "Manaus, BR"
            "Congo" -> "Kinshasa, CD"
            "Borneo" -> "Kota Kinabalu, MY"
            else -> "Paris, FR" // Valor predeterminado si no se encuentra el destino
        }
    }
}
