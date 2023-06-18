package com.example.servicioweb4

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_peliculas.*
import kotlinx.android.synthetic.main.activity_ver_pelicula.*
import org.json.JSONException

class ListadoPeliculas : AppCompatActivity() {
    var llenarLista = ArrayList<Usuarios>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_peliculas)
        cargarLista()

        val btnIrAotraVista = findViewById<Button>(R.id.btnAgregar)
        btnIrAotraVista.setOnClickListener {
            // Código para abrir la nueva vista
            val intent = Intent(this, verPelicula::class.java)
            startActivity(intent)
        }
        val txtBuscar = findViewById<EditText>(R.id.txtBuscar)
        txtBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarPeliculas(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    fun cargarLista() {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)

        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/usuarios"
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val usuario =
                                response.getJSONObject(i).getString("usuario")
                            val clave =
                                response.getJSONObject(i).getString("clave")
                            val estado =
                                response.getJSONObject(i).getString("estado")

                            llenarLista.add(Usuarios(id.toInt(),usuario, clave, estado))
                        }
                        val adapter = AdaptadorElementos(llenarLista)
                        lista.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }
    fun filtrarPeliculas(busqueda: String) {
        val listaFiltrada = ArrayList<Usuarios>()

        for (usuario in llenarLista) {
            if (usuario.usuario.toLowerCase().startsWith(busqueda.toLowerCase())) {
                listaFiltrada.add(usuario)
            }
        }

        val adapter = AdaptadorElementos(listaFiltrada)
        lista.adapter = adapter
    }
}