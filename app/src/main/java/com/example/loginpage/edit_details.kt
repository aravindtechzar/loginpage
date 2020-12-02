package com.example.loginpage

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class edit_details : AppCompatActivity()
{
    private val RESULT_CODE = 3
    lateinit var handler: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_details)
        handler = DatabaseHelper(this)
        val editname=findViewById<EditText>(R.id.edit_name)
        val editmailid=findViewById<EditText>(R.id.edit_mailid)
        val editpassword=findViewById<EditText>(R.id.edit_password)
        val editphoneno=findViewById<EditText>(R.id.edit_phoneno)
        val editage=findViewById<EditText>(R.id.edit_age)

        val submit=findViewById<Button>(R.id.edit_submit)

        val intent = intent
        val extras = intent.extras
        val username = extras!!.getString("name")
        val mailid = extras!!.getString("mailid")
        val passord=extras!!.getString("password")
        val phoneno=extras!!.getString("phoneno")
        val age =extras!!.getString("age")
        val id=extras!!.getString("id")




        editname.setText(username)
        editmailid.setText(mailid)
        editpassword.setText(passord)
        editphoneno.setText(phoneno)
        editage.setText(age)

        submit.setOnClickListener {

            if (editname.length() == 0) {
                editname.requestFocus();
                editname.setError("ENTER THE NAME");
            }
            if (editpassword.length() == 0) {
                editpassword.requestFocus();
                editpassword.setError("ENTER THE PASSWORD");
            }
            if (editmailid.length() == 0) {
                editmailid.requestFocus();
                editmailid.setError("ENTER THE MAIL ID");
            }
            if (editage.length() == 0) {
                editage.requestFocus();
                editage.setError("ENTER THE AGE");
            }
            if (editphoneno.length() == 0) {
                editphoneno.requestFocus();
                editphoneno.setError("ENTER THE PHONE NO.");
            }



            if (editname.length() == 0 && editmailid.length() == 0 && editpassword.length() == 0 && editphoneno.length() == 0 && editage.length() == 0) {
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            } else {



                var i =handler.updateData(
                        id!!,
                        editname.text.toString(),
                        editmailid.text.toString(),
                        editpassword.text.toString(),
                        editphoneno.text.toString(),
                        editage.text.toString()
                ).toString()

                val returnIntent = this.intent
                returnIntent.putExtra("Results",i)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()






            }
        }

        }



    }
