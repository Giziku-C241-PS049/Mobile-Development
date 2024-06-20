package com.example.giziku.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.giziku.MainActivity
import com.example.giziku.R
import com.example.giziku.auth.Auth

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        @Suppress("DEPRECATED")
        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashScreenActivity, Auth::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}