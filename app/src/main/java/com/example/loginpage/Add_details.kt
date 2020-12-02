package com.example.loginpage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class Add_details : AppCompatActivity()
{
    lateinit var handler: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_alldetails)



        handler = DatabaseHelper(this)

        var addname= findViewById<EditText>(R.id.addname)
        val addmailid=findViewById<EditText>(R.id.addmailid)
        val addpassword=findViewById<EditText>(R.id.addpassword)
        val addphoneno=findViewById<EditText>(R.id.addphoneno)
        val addage=findViewById<EditText>(R.id.addage)
        val add=findViewById<Button>(R.id.addsubmit)
        val img=findViewById<ImageView>(R.id.img)






        add.setOnClickListener {
            if (addname.length() == 0) {
                addname.requestFocus();
                addname.setError("ENTER THE NAME");
            }
            if (addpassword.length() == 0) {
                addpassword.requestFocus();
                addpassword.setError("ENTER THE PASSWORD");
            }
            if (addmailid.length() == 0) {
                addmailid.requestFocus();
                addmailid.setError("ENTER THE MAIL ID");
            }
            if (addage.length() == 0) {
                addage.requestFocus();
                addage.setError("ENTER THE AGE");
            }
            if (addphoneno.length() == 0) {
                addphoneno.requestFocus();
                addphoneno.setError("ENTER THE PHONE NO.");
            }

            if (addname.length() == 0 && addmailid.length() == 0 && addpassword.length() == 0 && addphoneno.length() == 0 && addage.length() == 0) {
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
            else
            {
                    handler.addData(
                        addname.text.toString(),
                        addmailid.text.toString(),
                        addpassword.text.toString(),
                        addphoneno.text.toString(),
                        addage.text.toString()
                    )
                val intent=Intent(this,Navigation::class.java)
                startActivity(intent)

                   Toast.makeText(this, "Sucessfully inserted", Toast.LENGTH_SHORT).show()
            }

        }
    }
}