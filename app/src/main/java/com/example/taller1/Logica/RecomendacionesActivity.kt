package com.example.taller1.Logica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.R
import kotlin.random.Random

class RecomendacionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)

        val txtNombreRecomendacion: TextView = findViewById(R.id.txt_nombre_recomendacion)
        val txtPaisRecomendacion: TextView = findViewById(R.id.txt_pais_recomendacion)
        val txtCategoriaRecomendacion: TextView = findViewById(R.id.txt_categoria_recomendacion)
        val txtPlanRecomendacion: TextView = findViewById(R.id.txt_plan_recomendacion)
        val txtPrecioRecomendacion: TextView = findViewById(R.id.txt_precio_recomendacion)
        val btnAtras: Button = findViewById(R.id.btn_atras_recomendaciones)

        // Verificar si hay destinos favoritos
        if (MainActivity.listaFavoritos.isEmpty()) {
            txtNombreRecomendacion.text = "NA"
            txtPaisRecomendacion.text = ""
            txtCategoriaRecomendacion.text = ""
            txtPlanRecomendacion.text = ""
            txtPrecioRecomendacion.text = ""
            return
        }

        // Contar las categorías de los destinos favoritos
        val categoriasContador = MainActivity.listaFavoritos.groupingBy { it["categoria"] }.eachCount()

        // Encontrar la categoría más frecuente
        val categoriaMasFrecuente = categoriasContador.maxByOrNull { it.value }?.key

        // Filtrar los destinos que pertenecen a la categoría más frecuente
        val destinosFiltrados = MainActivity.listaFavoritos.filter { it["categoria"] == categoriaMasFrecuente }

        // Seleccionar un destino aleatorio de la lista filtrada
        val destinoRecomendado = destinosFiltrados[Random.nextInt(destinosFiltrados.size)]

        // Mostrar la información completa del destino recomendado
        txtNombreRecomendacion.text = destinoRecomendado["nombre"]
        txtPaisRecomendacion.text = destinoRecomendado["pais"]
        txtCategoriaRecomendacion.text = destinoRecomendado["categoria"]
        txtPlanRecomendacion.text = destinoRecomendado["plan"]
        txtPrecioRecomendacion.text = "USD ${destinoRecomendado["precio"]}"

        // Configurar el botón "Atrás" para regresar a la actividad principal
        btnAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}

