package com.proyecto.JuggerCo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var intent=Intent(this,SignInActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(intent)
                finish()
            },
            3000
        )
    }
}