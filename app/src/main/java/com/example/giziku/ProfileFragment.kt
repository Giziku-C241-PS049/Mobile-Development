package com.example.giziku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.giziku.auth.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : Fragment() {

    private lateinit var resultUsername: TextView
    private lateinit var resultEmail: TextView
    private lateinit var resultPassword: TextView
    private lateinit var btnLogout: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser!!

        // Find views
        resultUsername = view.findViewById(R.id.result_username)
        resultEmail = view.findViewById(R.id.result_email)
        resultPassword = view.findViewById(R.id.result_password)
        btnLogout = view.findViewById(R.id.btn_logout)

        // Set user data to views
        resultUsername.text = currentUser.displayName ?: "Nama tidak tersedia"
        resultEmail.text = currentUser.email ?: "Email tidak tersedia"
        resultPassword.text = "********"

        // Logout button
        btnLogout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(activity, Auth::class.java))
            activity?.finish()
        }

        return view
    }
}
