package com.example.giziku.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.giziku.MainActivity
import com.example.giziku.R
import com.example.loginregist.DataHelper

class InfoFragment : Fragment() {

    companion object {
        const val EDIT_PROFILE_REQUEST_CODE = 1001 // Atur angka sesuai kebutuhan Anda
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialized(view)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val editedName = data?.getStringExtra("editedName")
            val editedEmail = data?.getStringExtra("editedEmail")
            val editedPassword = data?.getStringExtra("editedPassword")

            val nama = view?.findViewById<TextView>(R.id.result_username)
            val email = view?.findViewById<TextView>(R.id.result_email)
            val password = view?.findViewById<TextView>(R.id.result_password)

            nama?.text = editedName
            email?.text = editedEmail
            password?.text = editedPassword
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun initialized (v : View){
        val db = DataHelper(v.context)
        view?.findViewById<Button>(R.id.btn_logout)?.setOnClickListener {
            val intent = Intent(this@InfoFragment.requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        view?.findViewById<Button>(R.id.btn_edit)?.setOnClickListener {
            //val intent = Intent(this@Profile_Fragment.requireContext(), Login::class.java)
            val intent = Intent(this@InfoFragment.requireContext(), EditProfile::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        val editButton = view?.findViewById<Button>(R.id.btn_edit)

        editButton?.setOnClickListener {
            val intent = Intent(this@InfoFragment.requireContext(), EditProfile::class.java)
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
        }

        val nama = view?.findViewById<TextView>(R.id.result_username)
        val email = view?.findViewById<TextView>(R.id.result_email)
        val password = view?.findViewById<TextView>(R.id.result_password)

        val idUser = activity?.intent!!.getStringExtra("iduser").toString()
        val profile = db.profile(idUser.toInt())

        if (nama != null) {
            nama.text = profile[0]
        }
        if (email != null) {
            email.text = profile[1]
        }
        if (password != null) {
            password.text = profile[2]
        }
    }

}