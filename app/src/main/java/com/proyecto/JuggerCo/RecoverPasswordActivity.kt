package com.proyecto.JuggerCo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyecto.JuggerCo.databinding.ActivityRecoverPasswordBinding

class RecoverPasswordActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRecoverPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRecoverPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }
    private fun setup(){
        //Obteniendo objetos del activity
        val emailAddress = binding.emailEditText;
        val botonRecuperar=binding.buttonRecovered;
        val volver=binding.volver;
        val user=Firebase.auth.currentUser;
        var intent = Intent(this,SignInActivity::class.java)
        var intent2 = Intent(this,HomeActivity::class.java)
        // validando si ya existe una sesion activa
        if( user!=null && user?.isEmailVerified===true){
            startActivity(intent2)
            finish()
        }
        //Volver al activity de iniciar sesion
        volver.setOnClickListener{
            startActivity(intent)
            finish()
        }
        botonRecuperar.setOnClickListener{

            //Logica para recuperar contraseña
            Firebase.auth.sendPasswordResetEmail(emailAddress.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Snackbar.make(botonRecuperar,"Se envio un link para recuperar tu contraseña",
                            Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                        }.show()
                    }
                }
        }
    }
}