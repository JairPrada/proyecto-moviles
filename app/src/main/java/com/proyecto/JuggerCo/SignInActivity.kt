package com.proyecto.JuggerCo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyecto.JuggerCo.databinding.ActivitySignInBinding
import com.proyecto.JuggerCo.databinding.ActivitySignUpBinding


class SignInActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Analitics evento
        var analytics= FirebaseAnalytics.getInstance(this)
        val bundle= Bundle()
        bundle.putString("message","Integracion completada")
        analytics.logEvent("InitScreen",bundle)

        // setup
        setup()
    }
    private fun setup(){

        // Obteniendo Objetos del activity
        var email=binding.emailEditTextSignIn;
        var password=binding.passwordEditTextSignIn;
        var loginBtn=binding.loginButton;
        var register=binding.SignUpTextViewSignIn;
        var recovered=binding.recoverPasswordTextViewSignIn
        var user= Firebase.auth.currentUser;
        var intent= Intent(this,SignUpActivity::class.java)
        var intent2= Intent(this,HomeActivity::class.java);
        var intent3= Intent(this,RecoverPasswordActivity::class.java);

        // validando si ya existe una sesion activa
        if( user!=null && user?.isEmailVerified===true){
            startActivity(intent2)
            finish()
        }

        // Recurar contraseña
        recovered.setOnClickListener{
            startActivity(intent3)
            finish()
        }

        // Enviando al activity de registrarse
        register.setOnClickListener{
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener{
            if(email.text!!.isNotEmpty() && password.text!!.isNotEmpty()) {

                // Logica para iniciar sesion
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.text.toString()
                        ,password.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            var emailVerificado= Firebase.auth.currentUser?.isEmailVerified;
                            // validando si ya verifico el correo electronico
                            if(emailVerificado === true){
                                startActivity(intent2)
                                finish()
                            }

                            else{
                                Snackbar.make(loginBtn,"Debes verificar tu correo electronico",
                                    Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                                }.show()
                            }
                        }
                        else
                        {
                            Snackbar.make(loginBtn,"Error, el correo o la contraseña no son validos",
                                Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                            }.show()
                        }
                    }
            }
            else{
                Snackbar.make(loginBtn,"Debes llenar ambos campos", Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                }.show()
            }
        }
    }
}