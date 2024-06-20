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
import com.google.firebase.auth.UserProfileChangeRequest

class Register : AppCompatActivity() {
    private lateinit var namaDaftar: EditText
    private lateinit var emailDaftar: EditText
    private lateinit var passwordDaftar: EditText
    private lateinit var regist: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var loginNow: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        namaDaftar = findViewById(R.id.nama_daftar)
        emailDaftar = findViewById(R.id.email)
        passwordDaftar = findViewById(R.id.password)
        regist = findViewById(R.id.regist)
        progressBar = findViewById(R.id.progresBar)

        regist.setOnClickListener {
            val nama = namaDaftar.text.toString()
            val email = emailDaftar.text.toString()
            val password = passwordDaftar.text.toString()

            if (TextUtils.isEmpty(nama)) {
                Toast.makeText(this@Register, "Masukkan Nama Anda", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@Register, "Masukkan Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@Register, "Masukkan Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(nama)
                            .build()
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    Toast.makeText(this@Register, "Akun Berhasil Dibuat", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@Register, MainActivity::class.java))
                                    finish()
                                }
                            }
                    } else {
                        Toast.makeText(this@Register, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
