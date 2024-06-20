package com.example.giziku.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import com.example.giziku.R

class Auth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val login = findViewById<Button>(R.id.login)
        val regist = findViewById<Button>(R.id.regist)

        login.setOnClickListener {
            val intent = Intent(this@Auth, Login::class.java)
            startActivity(intent)
        }

        regist.setOnClickListener {
            val intent = Intent(this@Auth, Register::class.java)
            startActivity(intent)
        }
    }
}