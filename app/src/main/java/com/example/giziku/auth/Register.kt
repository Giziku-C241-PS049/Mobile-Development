package com.example.giziku.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.giziku.MainActivity
import com.example.giziku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {
    private lateinit var namaDaftar: EditText
    private lateinit var emailDaftar: EditText
    private lateinit var passwordDaftar: EditText
    private lateinit var regist: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        namaDaftar = findViewById(R.id.nama_daftar)
        emailDaftar = findViewById(R.id.email)
        passwordDaftar = findViewById(R.id.password)
        regist = findViewById(R.id.regist)
        progressBar = findViewById(R.id.progresBar)
        textView = findViewById(R.id.loginNow)

        textView.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        regist.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val nama = namaDaftar.text.toString()
            val email = emailDaftar.text.toString()
            val password = passwordDaftar.text.toString()

            when {
                TextUtils.isEmpty(nama) -> {
                    Toast.makeText(this@Register, "Masukkan Nama Anda", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }

                TextUtils.isEmpty(email) -> {
                    Toast.makeText(this@Register, "Masukkan Email", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }

                TextUtils.isEmpty(password) -> {
                    Toast.makeText(this@Register, "Masukkan Password", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }

                else -> {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            progressBar.visibility = View.GONE
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@Register,
                                    "Akun Berhasil Dibuat",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@Register,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
}