package com.example.loginpage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class user_view : AppCompatActivity() {
    lateinit var handler : DatabaseHelper
    lateinit var preferences : SharedPreferences
    lateinit var id : String
    lateinit var username : EditText
    lateinit var usermailid : EditText
    lateinit var userpassword : EditText
    lateinit var userphoneno : EditText
    lateinit var userage : EditText

     var user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)
        handler = DatabaseHelper(this)
         username = findViewById<EditText>(R.id.user_name)
         usermailid = findViewById<EditText>(R.id.user_maild)
         userpassword = findViewById<EditText>(R.id.user_password)
         userphoneno = findViewById<EditText>(R.id.user_phoneno)
         userage = findViewById<EditText>(R.id.user_age)
        val image=findViewById<ImageView>(R.id.profile_image)


           // val userName: String? = handler.getUserName("USERNAME")
       // editor.putString("firstname",firstName);
        //editor.putString("lastname",lastName)

            preferences=getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
             id= preferences.getString("id", "").toString()
           val gmail= preferences.getString("MAILID","").toString()
        val firstname=preferences.getString("firstname","").toString()
        val lastname=preferences.getString("lastname","")
        val img=preferences.getString("fbimage","")
        val phoneno=preferences.getString("phone","")
        val id=preferences.getString("id","")
        val fbmail=preferences.getString("email","")

            // val password=preferences.getString("PASSWORD", "")

        Log.e("shared pre", "" + id)

        Glide.with(this)
            .load(img)
            .into(image);

        usermailid.setText(fbmail)
        username.setText(firstname)
        userpassword.setText(lastname)
        userphoneno.setText(phoneno)
        userage.setText(id)

/*
       user=handler.getUser(id)
        Log.e("user", "" + user)


        username.setText(user.name)
        usermailid.setText(user.mailid)
        userpassword.setText(user.password)
        userphoneno.setText(user.phoneno)
        userage.setText(user.age)
*/
    }

}