package com.proyecto.JuggerCo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.proyecto.JuggerCo.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }
    private fun setup(){
        // Obteniendo objetos del activity
        var name=binding.nameEditTextSignUp;
        var email=binding.emailEditTextSignUp;
        var password=binding.passwordEditTextSignUp;
        var loginBtn=binding.loginButton;
        var register=binding.SignInTextViewSignUp;
        var user=Firebase.auth.currentUser;
        var intent= Intent(this,HomeActivity::class.java)
        var intent2= Intent(this,SignInActivity::class.java);

        // validando si ya existe una sesion activa
        if( user!=null && user?.isEmailVerified===true){
            startActivity(intent)
            finish()
        }
        // Volviendo a iniciar sesion
        register.setOnClickListener{
            startActivity(intent2)
            finish()
        }

        loginBtn.setOnClickListener{
            if(email.text!!.isNotEmpty() && password.text!!.isNotEmpty()) {

                // Logica para registrar Usuario
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.text.toString()
                        ,password.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            var user= Firebase.auth.currentUser;

                            // Enviando correo de verificacion
                            user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Snackbar.make(loginBtn,"Hemos enviado un mensaje a tu correo electronico",
                                            Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                                        }.show()
                                    }
                                }

                            // Agregando nombre a la cuenta
                            var newName= userProfileChangeRequest{
                                displayName = name.text.toString()
                            }
                            user!!.updateProfile(newName)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Snackbar.make(loginBtn,"Tu cuenta se creo correctamente",
                                            Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                                        }.show()
                                    }
                                }
                            Firebase.auth.signOut()
                            Snackbar.make(loginBtn,"Revisa tu correo electronico para verificar tu cuenta",
                                Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                            }.show()
                        }
                        else
                        {
                            Snackbar.make(loginBtn,"Hubo un error en la creacion de la cuenta",
                                Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                            }.show()
                        }
                    }
            }
            else{
                Snackbar.make(loginBtn,"Debes llenar todos los campos", Snackbar.LENGTH_INDEFINITE).setAction("Esconder"){
                }.show()
            }
        }
    }
}