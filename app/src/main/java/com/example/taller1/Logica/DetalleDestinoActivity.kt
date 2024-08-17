package com.example.taller1.Logica

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.R

class DetalleDestinoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_destino)

        // Obtener las referencias a los elementos de la interfaz
        val txtNombreDestino: TextView = findViewById(R.id.txt_nombre_destino)
        val txtPaisDestino: TextView = findViewById(R.id.txt_pais_destino)
        val txtCategoriaDestino: TextView = findViewById(R.id.txt_categoria_destino)
        val txtPlanDestino: TextView = findViewById(R.id.txt_plan_destino)
        val txtPrecioDestino: TextView = findViewById(R.id.txt_precio_destino)
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
            btnAgregarFavoritos.isEnabled = false

            val destino = mapOf(
                "nombre" to nombreDestino,
                "pais" to paisDestino,
                "categoria" to categoriaDestino,
                "plan" to planDestino,
                "precio" to precioDestino
            )

            MainActivity.listaFavoritos.add(destino as Map<String, String>)
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
        }

        // Configurar el botón "Atrás" para regresar a la actividad anterior
        btnAtras.setOnClickListener {
            finish()  // Cierra la actividad actual y regresa a la anterior
        }
    }
}
