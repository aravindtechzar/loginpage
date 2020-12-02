 package com.example.loginpage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

 class signup : AppCompatActivity() {
     lateinit var handler: DatabaseHelper
     var selectedgender: String = ""
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.signup)
         handler = DatabaseHelper(this)

         val submit = findViewById(R.id.submit) as Button
         val name = findViewById(R.id.name) as EditText
         val mailid = findViewById(R.id.mailid) as EditText
         val password = findViewById(R.id.password) as EditText
         val phoneno: EditText = findViewById(R.id.phoneno) as EditText
         val age: EditText = findViewById(R.id.age) as EditText
         val gender = findViewById<Spinner>(R.id.gender)

         val items = arrayOf("MALE", "FEMALE")
         val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
         gender.adapter = adapter

         gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                 val selectedItem = parent.getItemAtPosition(position).toString()
                 when (position) {
                     0 -> selectedgender = selectedItem
                     1 -> selectedgender = selectedItem
                     else -> { // Note the block
                         print("x is neither 1 nor 2")
                     }
                 }
             } // to close the onItemSelected

             override fun onNothingSelected(parent: AdapterView<*>) {

             }
         }

         submit.setOnClickListener {

             if (name.length() == 0) {
                 name.requestFocus();
                 name.setError("ENTER THE NAME");
             }
             if (password.length() == 0) {
                 password.requestFocus();
                 password.setError("ENTER THE PASSWORD");
             }
             if (mailid.length() == 0) {
                 mailid.requestFocus();
                 mailid.setError("ENTER THE MAIL ID");
             }
             if (age.length() == 0) {
                 age.requestFocus();
                 age.setError("ENTER THE AGE");
             }
             if (phoneno.length() == 0) {
                 phoneno.requestFocus();
                 phoneno.setError("ENTER THE PHONE NO.");
             }



             if (name.length() == 0 && mailid.length() == 0 && password.length() == 0 && phoneno.length() == 0 && age.length() == 0) {
                 Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
             } else {

                 handler.insertUserData(name.text.toString(), mailid.text.toString(), password.text.toString(), phoneno.text.toString(), age.text.toString())

                 // build alert dialog
                 val dialogBuilder = AlertDialog.Builder(this)

                 // set message of alert dialog
                 dialogBuilder.setMessage("Your Details is Sucessfully Registered")
                     // if the dialog is cancelable
                     .setCancelable(false)
                     // positive button text and action
                     .setPositiveButton("OK", DialogInterface.OnClickListener {
                             dialog, id -> finish()

                         val intent=Intent(this,mainlogin::class.java)
                         startActivity(intent)
                     })
                     // negative button text and action


                 // create dialog box
                 val alert = dialogBuilder.create()
                 // set title for alert dialog box
                 alert.setTitle("Signup Dialog Box")
                 // show alert dialog
                 alert.show()

             }
         }

     }
 }




