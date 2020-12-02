package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class home_activity : AppCompatActivity()
{
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val logout=findViewById<Button>(R.id.logout)
        val txt1=findViewById<TextView>(R.id.mail)
        val txt2=findViewById<TextView>(R.id.pass)
        preferences=getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val mailid=preferences.getString("MAILID", null)
        val password=preferences.getString("PASSWORD", "")
        Log.e("mailid", "mailiddd    " + mailid + password)
        txt1.setText(mailid)

        txt2.setText(password)




        logout.setOnClickListener {
            val editor:SharedPreferences.Editor=preferences.edit()
            editor.clear()
            editor.apply()

            val intent=Intent(this, mainlogin::class.java)
            startActivity(intent)
            finish()
        }


    }



}
