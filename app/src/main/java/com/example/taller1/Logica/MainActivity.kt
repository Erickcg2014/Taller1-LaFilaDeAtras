package com.example.taller1.Logica

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.R

class MainActivity : AppCompatActivity() {

    companion object {
        val listaFavoritos: MutableList<Map<String, String>> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar el Spinner con las categorías de viajes
        val spinner: Spinner = findViewById(R.id.spinner_categorias)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.categorias_viajes,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Botones en la actividad principal
        val explorarButton: Button = findViewById(R.id.btn_explorar_destinos)
        val favoritosButton: Button = findViewById(R.id.btn_favoritos)
        val recomendacionesButton: Button = findViewById(R.id.btn_recomendaciones)

        explorarButton.setOnClickListener {
            val intent = Intent(this, ExplorarDestinosActivity::class.java)
            val categoriaSeleccionada = spinner.selectedItem.toString()
            intent.putExtra("categoria", categoriaSeleccionada)
            startActivity(intent)
        }

        favoritosButton.setOnClickListener {
            val intent = Intent(this, FavoritosActivity::class.java)
            startActivity(intent)
        }

        recomendacionesButton.setOnClickListener {
            val intent = Intent(this, RecomendacionesActivity::class.java)
            startActivity(intent)
        }

    }
}