package com.example.loginpage


import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.internal.ImageRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import java.util.*


class mainlogin: AppCompatActivity()
{

    lateinit var handler: DatabaseHelper
    internal var submit: EditText? = null
   lateinit var sharedPreferences: SharedPreferences
    lateinit var usermailid:String
    lateinit var userpassword:String
    lateinit var mGoogleSignInClient:GoogleSignInClient
    lateinit var data : ArrayList<User>
    var RC_SIGN_IN=123
    lateinit var personEmail:String
    lateinit var callbackManager:CallbackManager
    private val EMAIL = "email"
    lateinit var loginButton:Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         loginButton=findViewById<LoginButton>(R.id.login_button)

        loginButton.setOnClickListener {
          //  callbackManager = CallbackManager.Factory.create();
            callbackManager = CallbackManager.Factory.create()
          //  LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("fields"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    val accessToken: AccessToken = result!!.getAccessToken()
                    Log.d("MainActivity", "Facebook token: ")

                    sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("login", "yes");
                    editor.apply();



                    val request = GraphRequest.newMeRequest(
                        result.accessToken) { me, response ->
                        if (response.error != null) {
// handle error
                        } else {
// get email and id of the user
                            val email = me.optString("email")
                            val id = me.optString("id")
                            val firstName = me.optString("first_name")
                            val lastName = me.optString("last_name")
                            val phoneno=me.optString("mobile_number")
                            val fbimg = "https://graph.facebook.com/$id/picture?width=512&height=512"
                            sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
                            editor.putString("firstname",firstName);
                            editor.putString("lastname",lastName)
                            editor.putString("fbimage",fbimg)
                            editor.putString("phone",phoneno)
                            editor.putString("id",id)
                            editor.putString("email",email)
                            editor.apply()
                            Log.e("Profile","" +firstName)
                            Log.e("Profile","" +fbimg)
                            Log.e("phoneno",""+phoneno)
                            Log.e("email","+++++"+email)
                            Log.e("id","++++++"+id)



                        }
                        startActivity(Intent(applicationContext, Navigation::class.java))
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location,picture")
                    request.parameters = parameters
                    request.executeAsync()


                }

                override fun onCancel() {
                    Log.d("MainActivity", "Facebook onCancel.")
                }

                override fun onError(error: FacebookException?) {
                    Log.d("MainActivity", "Facebook onError.")

                }

            })
            }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
       signInButton.setOnClickListener {
           val signInIntent = mGoogleSignInClient.signInIntent
           startActivityForResult(signInIntent, RC_SIGN_IN)

       }
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
             personEmail = acct.email.toString()
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl
            Log.e("google", "" + personEmail)

            val preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("MAILID", acct.email)
            editor.apply()


        }

        handler = DatabaseHelper(this)
        val actionBar: ActionBar? = supportActionBar

        handler = DatabaseHelper(this)
       


        val submit = findViewById(R.id.btn) as Button
        val mailid = findViewById(R.id.t1) as EditText
        val password = findViewById(R.id.t2) as EditText
       val forgetpassword: TextView=findViewById(R.id.forget_password) as TextView
        val signup =findViewById(R.id.signup) as TextView


        forgetpassword.setOnClickListener {
            Toast.makeText(this, "signup", Toast.LENGTH_LONG).show();
            val i = Intent(this, com.example.loginpage.forget_password::class.java)
            startActivity(i)
        }

        signup.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "signup", Toast.LENGTH_LONG).show();
            val i = Intent(this, com.example.loginpage.signup::class.java)
            startActivity(i)
        })

        submit.setOnClickListener {




            if (mailid.length() == 0) {
                mailid.requestFocus();
                mailid.setError("ENTER THE USER NAME");
            }
            if (password.length() == 0) {
                password.requestFocus();
                password.setError("ENTER THE PASSWORD");
            }

            val hashmap=handler.userPresent(mailid.text.toString(), password.text.toString())

            Log.e("hash", "   " + hashmap)
            if (hashmap.get("sucess") == "true" )
               {

                   val intent = Intent(this, Navigation::class.java)
                    hashmap.get("id")
                   sharedPreferences=getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
                   usermailid = mailid.getText().toString();
                   userpassword=password.getText().toString()
                   val editor:SharedPreferences.Editor=sharedPreferences.edit()
                   editor.putString("MAILID", usermailid)
                   editor.putString("PASSWORD", userpassword)
                   editor.putString("id", "" + hashmap.get("id"))



                   editor.apply()


                   Log.e("shared", "" + sharedPreferences.getString("id", ""))


                   Toast.makeText(this, "sucess", Toast.LENGTH_SHORT).show()
                   startActivity(intent)

               }
                else{
                   Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()

               }




        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)



            val intent=Intent(this, Navigation::class.java)
            sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
            val editor:SharedPreferences.Editor=sharedPreferences.edit()
            editor.putString("login", "yes");
            editor.apply();
            startActivity(intent)
            // Signed in successfully, show authenticated UI.
            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.statusCode)
           // updateUI(null)
        }
    }


    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit!!")
                .setMessage("Do you want to exit ?")
                .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {

                        finishAffinity()
                    }
                })
                .setNegativeButton(android.R.string.cancel) { dialog, which -> dialog.cancel() }

        val dialog = builder.create()
        //Disable outside touch cancellation
        //Disable outside touch cancellation
        dialog.setCanceledOnTouchOutside(false)
        //Disable back button
        //Disable back button
        dialog.setCancelable(false)
        dialog.show()

    }
    /*fun fblogin()
    {


        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {


                override fun onSuccess(result: LoginResult?) {

                            Log.d("MainActivity", "Facebook token: " + result!!.accessToken.token)
                            startActivity(Intent(this@mainlogin, Navigation::class.java))
                            finish()


                }

                override fun onCancel() {
                    TODO("Not yet implemented")
                }

                override fun onError(error: FacebookException?) {
                    TODO("Not yet implemented")
                }

            })
    } */


}


