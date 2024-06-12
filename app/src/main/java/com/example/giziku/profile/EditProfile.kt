package com.example.giziku.profile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.giziku.R
import com.example.loginregist.DataHelper
import com.example.loginregist.User
import java.text.SimpleDateFormat
import java.util.*

class EditProfile : AppCompatActivity() {
    private lateinit var dataHelper: DataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        dataHelper = DataHelper(this)

        // Temukan komponen-komponen dari layout XML (EditText, Button, dsb.)
        val nameEditText: EditText = findViewById(R.id.editUsername)
        val emailEditText: EditText = findViewById(R.id.editEmail)
        val password: EditText = findViewById(R.id.editPassword)

        val saveButton: Button = findViewById(R.id.update)

        saveButton.setOnClickListener {
            val newName = nameEditText.text.toString()
            val newEmail = emailEditText.text.toString()
            val newPw = password.text.toString()

            val idUserToEdit = 1 // Ganti dengan ID user yang ingin diubah
            val newUser = User(newName, newEmail, newPw)

            dataHelper.editUser(
                idUserToEdit,
                newUser
            )

            val resultIntent = Intent()
            resultIntent.putExtra("editedName", newName)
            resultIntent.putExtra("editedEmail", newEmail)
            resultIntent.putExtra("editedPassword", newPw)
// ... tambahkan data lain jika diperlukan

// Set hasil data yang diubah sebagai result dari Edit_Profile
            setResult(Activity.RESULT_OK, resultIntent)

// Tutup Edit_Profile dan kembali ke fragment_profile_
            finish()

        }
    }
}