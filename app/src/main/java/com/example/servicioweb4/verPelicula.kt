package com.example.servicioweb4

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_ver_pelicula.*

class verPelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_pelicula)

        val estado = arrayOf("A", "X")
        cmbCategoria.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, estado
            )
        )
        btnGuardar.setOnClickListener() {
            agregarPelicula()
        }

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            txtId.setText(bundle.getString("id").toString())
            txtNombre.setText(bundle.getString("usuario").toString())
            when (bundle.getString("estado").toString()) {
                "A" -> cmbCategoria.setSelection(0)
                "X" -> cmbCategoria.setSelection(1)
            }
            txtClave2.setText(bundle.getString("clave").toString())

            btnGuardar.setEnabled(false)
            btnEliminar.setEnabled(true)
        } else {
            btnGuardar.setEnabled(true)
            btnEliminar.setEnabled(false)
            btnActivar.setEnabled(false)
        }
        btnEliminar.setOnClickListener() {
            eliminarPelicula()
        }
        btnActivar.setOnClickListener(){
            actualizarPelicula()
        }

    }

    fun agregarPelicula() {
        AsyncTask.execute {

            val id = txtId.text.toString()
            val usuario = txtNombre.text.toString()
            val estado = cmbCategoria.selectedItem.toString()
            val clave = txtClave2.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios"
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro agregado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al guardar los datos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["id"] = id
                    params["usuario"] = usuario
                    params["estado"] = estado
                    params["clave"] = clave
                    return params
                }
            }
            queue.add(postRequest)
        }
    }

    fun eliminarPelicula() {
        AsyncTask.execute {
            val id = txtId.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios/" + id
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.DELETE, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro eliminado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al eliminar el registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {}
            queue.add(postRequest)
        }

    }
     fun actualizarPelicula() {
        val id = txtId.text.toString()
        val usuario = txtNombre.text.toString()
        val estado = cmbCategoria.selectedItem.toString()
        val clave = txtClave2.text.toString()

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.urlAPI) + "/usuarios/$id"

        val putRequest = object : StringRequest(
            Request.Method.PUT, url,
            Response.Listener { response ->
                Toast.makeText(
                    applicationContext,
                    "usuario actualizado correctamente",
                    Toast.LENGTH_LONG
                ).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    "Error al actualizar el usuario",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                params["id"] = id
                params["usuario"] = usuario
                params["estado"] = estado
                params["clave"] = clave
                return params
            }
        }

        queue.add(putRequest)
    }
}