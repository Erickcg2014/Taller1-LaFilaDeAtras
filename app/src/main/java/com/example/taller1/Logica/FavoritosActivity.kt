package com.example.taller1.Logica

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.R

class FavoritosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val listView: ListView = findViewById(R.id.lista_favoritos)
        val btnAtras: Button = findViewById(R.id.btn_atras_favoritos)

        // Obtener los nombres de los destinos favoritos
        val nombresFavoritos = MainActivity.listaFavoritos.map { it["nombre"] ?: "Sin Nombre" }

        // Configurar el ListView con un adaptador simple
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresFavoritos)
        listView.adapter = adapter

        // Configurar el botón "Atrás" para regresar a la actividad principal
        btnAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Finaliza la actividad actual
        }
    }
}
