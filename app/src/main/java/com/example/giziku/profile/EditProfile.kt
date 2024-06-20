package com.example.giziku.profile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.giziku.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class EditProfile : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser!!

        // Find views
        nameEditText = findViewById(R.id.editUsername)
        emailEditText = findViewById(R.id.editEmail)
        passwordEditText = findViewById(R.id.editPassword)
        saveButton = findViewById(R.id.update)

        // Load current user data
        nameEditText.setText(currentUser.displayName)
        emailEditText.setText(currentUser.email)
        passwordEditText.setText("")

        // Save button click listener
        saveButton.setOnClickListener {
            val newName = nameEditText.text.toString()
            val newEmail = emailEditText.text.toString()
            val newPassword = passwordEditText.text.toString()

            if (newName.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this@EditProfile, "Nama dan Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update user profile
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { profileUpdateTask ->
                    if (profileUpdateTask.isSuccessful) {
                        // Update email if it has changed
                        if (newEmail != currentUser.email) {
                            currentUser.updateEmail(newEmail)
                                .addOnCompleteListener { emailUpdateTask ->
                                    if (emailUpdateTask.isSuccessful) {
                                        Toast.makeText(this@EditProfile, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this@EditProfile, "Gagal memperbarui email", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }

                        // Update password if it is not empty
                        if (newPassword.isNotEmpty()) {
                            currentUser.updatePassword(newPassword)
                                .addOnCompleteListener { passwordUpdateTask ->
                                    if (passwordUpdateTask.isSuccessful) {
                                        Toast.makeText(this@EditProfile, "Password berhasil diperbarui", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this@EditProfile, "Gagal memperbarui password", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }

                        // Return result to ProfileFragment
                        val resultIntent = Intent()
                        resultIntent.putExtra("editedName", newName)
                        resultIntent.putExtra("editedEmail", newEmail)
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    } else {
                        Toast.makeText(this@EditProfile, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
