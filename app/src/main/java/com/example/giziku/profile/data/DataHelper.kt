package com.example.loginregist


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.util.Pair
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class DataHelper (var context: Context) : SQLiteOpenHelper(context,
    "data-bimbel", null,1)
    {
        private  val db = this.writableDatabase
        override fun onCreate(db: SQLiteDatabase?) {
            val createTableUser = "CREATE TABLE user ( "+
            " id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " nama VARCHAR(50), " +
                    " email VARCHAR(50), " +
                    " password VARCHAR(50)); "

            val insertdatauser = "INSERT INTO user (nama,email,password) VALUES ('kelompok_dua','kelompok_dua','kelompok_dua' , '085338699084','2001-12-09','kelompok_dua'); "
            db?.execSQL(createTableUser)
            db?.execSQL(insertdatauser)

        }
        override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion:
        Int) {
            }
        fun insertUser (user: User, tglLahir : String) {
            val cv = ContentValues()
            cv.put("nama", user.nama)
            cv.put("email",user.email)
            cv.put("password",user.password)
            val result = db.insert("user", null, cv)
            if (result == (-1).toLong())
                Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "ADD ACCOUNT SUCCESS", Toast.LENGTH_SHORT).show()
        }

        @SuppressLint("Range")
        fun checkUser(email: String, password: String): Int {
            val query = "SELECT * FROM user WHERE email='$email' AND password='$password'"
            val rs = db.rawQuery(query, null)
            if (rs.moveToFirst()) {
                val idUser = rs.getInt(rs.getColumnIndex("id_user"))
                rs.close()

                return idUser
            }
            return -1
        }



        @SuppressLint("Range")
        fun profile(id: Int) : Array<String>{
            val query = "SELECT nama,email,password,nomor,tglLahir,asal FROM user WHERE id_user=$id"
            val rs = db.rawQuery(query,null)
            if(rs.moveToFirst()) {
                val nama = rs.getString(rs.getColumnIndex("nama"))
                val email = rs.getString(rs.getColumnIndex("email"))
                val password = rs.getString(rs.getColumnIndex("password"))
                return arrayOf(nama,email,password)
            }
            return arrayOf("","","")
        }


        fun editUser(id: Int, user: User) {
            val cv = ContentValues()
            cv.put("nama", user.nama)
            cv.put("email", user.email)
            cv.put("password", user.password)

            val result = this.db.update("user", cv, "id_user=?", arrayOf(id.toString()))

            if (result == 1)
                Toast.makeText(context, "Data user berhasil diubah", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Gagal mengubah data user", Toast.LENGTH_SHORT).show()
        }

        fun deleteUser(id: Int) {
            try {
                val query = "DELETE FROM user WHERE id_user=$id"
                db.execSQL(query)
                Toast.makeText(context, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Gagal menghapus akun", Toast.LENGTH_SHORT).show()
                Log.e("DataHelper", "Error deleting account", e)
            }
        }

    }