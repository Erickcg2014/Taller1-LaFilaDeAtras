package com.example.taller1.Logica

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.R
import org.json.JSONObject
import java.io.InputStream

class ExplorarDestinosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorar_destinos)

        val listView: ListView = findViewById(R.id.lista_destinos)

        // Leer y cargar el archivo JSON desde assets donde está el archivo destinos.json
        val destinos = leerDestinosDesdeJSON()

        // Filtrar los destinos que ya están en favoritos
        val destinosFiltrados = destinos.filter { destino ->
            !MainActivity.listaFavoritos.any { it["nombre"] == destino["nombre"] }
        }

        // Obtener la categoría seleccionada desde la actividad anterior
        val categoriaSeleccionada = intent.getStringExtra("categoria") ?: "Todos"

        // Filtrar los destinos según la categoría seleccionada
        val destinosPorCategoria = if (categoriaSeleccionada == "Todos") {
            destinosFiltrados
        } else {
            destinosFiltrados.filter { it["categoria"] == categoriaSeleccionada }
        }

        // Obtener solo los nombres de los destinos filtrados para mostrarlos en la lista
        val nombresDestinos = destinosPorCategoria.map { it["nombre"] }

        // Configurar el ListView con un adaptador simple
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresDestinos)
        listView.adapter = adapter

        // Crear un FooterView con un botón "Atrás"
        val footerView = layoutInflater.inflate(R.layout.footer_atras, null)
        val btnAtras = footerView.findViewById<Button>(R.id.btn_atras_footer)

        // Configurar el botón "Atrás" para regresar a la actividad principal
        btnAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        // Añadir el FooterView al ListView
        listView.addFooterView(footerView)

        // Configurar el listener para abrir la actividad de detalles al hacer clic en un destino
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val destinoSeleccionado = destinosPorCategoria[position]

            // Log para Verificar que el mapa contenga todos los datos
            Log.d("ExplorarDestinosActivity", "Destino seleccionado: $destinoSeleccionado")

            val intent = Intent(this, DetalleDestinoActivity::class.java)
            intent.putExtra("nombre_destino", destinoSeleccionado["nombre"])
            intent.putExtra("pais_destino", destinoSeleccionado["pais"] ?: "No disponible")
            intent.putExtra("categoria_destino", destinoSeleccionado["categoria"] ?: "No disponible")
            intent.putExtra("plan_destino", destinoSeleccionado["plan"] ?: "No disponible")
            intent.putExtra("precio_destino", destinoSeleccionado["precio"]?.toString() ?: "0")

            // Log para verificar los datos antes de enviarlos
            Log.d("ExplorarDestinosActivity", "Datos enviados - Nombre: ${destinoSeleccionado["nombre"]}, País: ${destinoSeleccionado["pais"]}, Categoría: ${destinoSeleccionado["categoria"]}, Plan: ${destinoSeleccionado["plan"]}, Precio: ${destinoSeleccionado["precio"]}")

            startActivity(intent)
        }
    }

    // Función para leer el archivo JSON y convertirlo en una lista de mapas
    private fun leerDestinosDesdeJSON(): List<Map<String, String>> {
        val destinos = mutableListOf<Map<String, String>>()
        val inputStream: InputStream = assets.open("destinos.json")
        val json = inputStream.bufferedReader().use { it.readText() }

        // Parsear el JSONObject principal
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject.getJSONArray("destinos")

        // Iterar sobre el Array y extraer los objetos individuales
        for (i in 0 until jsonArray.length()) {
            val jsonObjectDestino = jsonArray.getJSONObject(i)
            val nombre = jsonObjectDestino.getString("nombre")
            val pais = jsonObjectDestino.getString("pais")
            val categoria = jsonObjectDestino.getString("categoria")
            val plan = jsonObjectDestino.getString("plan")
            val precio = jsonObjectDestino.getInt("precio").toString()

            destinos.add(mapOf(
                "nombre" to nombre,
                "pais" to pais,
                "categoria" to categoria,
                "plan" to plan,
                "precio" to precio
            ))
        }
        return destinos
    }
}
