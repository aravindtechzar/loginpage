package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class splash : AppCompatActivity() {
    lateinit var preferences:SharedPreferences
    private val SPLASH_TIME_OUT:Long=1000 // 3 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        preferences=getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        if(preferences.getString("login", "no").equals("yes"))
        {
           val intent=Intent(this,Navigation ::class.java)
            startActivity(intent)
        }
        else{
            val intent=Intent(this,mainlogin::class.java)
            startActivity(intent)
            //login value is no, so start loginactivity
        }
       // val password=preferences.getString("PASSWORD", "")



        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

finish()
            // close this activity

        }, SPLASH_TIME_OUT)
    }
}
