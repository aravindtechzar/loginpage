package com.example.loginpage



import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class Navigation : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    lateinit var handler: DatabaseHelper

    lateinit var drawer : DrawerLayout
    lateinit var navigationview:NavigationView
    lateinit var adapter :CustomAdapter
    lateinit var  recyclerView: RecyclerView
    lateinit var add:FloatingActionButton
    lateinit var hide:TextView
    lateinit var preferences: SharedPreferences
    lateinit var mGoogleSignInClient: GoogleSignInClient
    var users = ArrayList<User>()
   // @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigationactivity)

       val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           .requestEmail()
           .build()
       mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

       handler = DatabaseHelper(this)

        hide=findViewById(R.id.hide)

        add=findViewById<FloatingActionButton>(R.id.add_data)

        add.setOnClickListener {
            Toast.makeText(this, "add_details", Toast.LENGTH_SHORT).show()

            var intent=Intent(this, Add_details::class.java)
            startActivity(intent)

        }



        setSupportActionBar(findViewById(R.id.tool))
        navigationview=findViewById(R.id.navi)
      navigationview.setNavigationItemSelectedListener(this)

        recyclerView= findViewById<RecyclerView>(R.id.recycle)
        recyclerView.layoutManager =   LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        users= handler.readData()
        if (users.isEmpty())
        {
            hide.visibility=View.VISIBLE
            recyclerView.visibility=View.GONE
        }
        else
        {
            hide.visibility=View.GONE
            recyclerView.visibility=View.VISIBLE
        }

       adapter = CustomAdapter(users, this, this@Navigation)

        recyclerView.adapter = adapter

        drawer=findViewById<DrawerLayout>(R.id.drawablelayout)
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 12345)
        {
            val result = data!!.getStringExtra("Results")
            Log.e("abcdf", "result   " + result)
            if (result=="true")
            {
                recyclerView= findViewById<RecyclerView>(R.id.recycle)
                recyclerView.layoutManager =   LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
                );
                users= handler.readData()
                adapter = CustomAdapter(users, this, this@Navigation)

                recyclerView.adapter = adapter

                Log.e("last", "datasss   " + users)
            }
            if (result=="false")
            {
                Toast.makeText(this, "nochanges", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit!!")
                    .setMessage("Do you want to exit ?")
                    .setPositiveButton(
                        android.R.string.ok,
                        object : DialogInterface.OnClickListener {
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



    }





    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {


        when(item.itemId)
        {

            R.id.gallery -> {
                val intent = Intent(this, user_view::class.java)
                startActivity(intent)
                add.visibility = View.VISIBLE

            }
            R.id.notification -> {

                signOut()
            }

        }
        return true
    }
    private fun signOut() {

        add.visibility = View.INVISIBLE
        val preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
        finish()
        Log.e("shared", "" + preferences!!.getString("MAILID", ""))
        val loginActivity = Intent(applicationContext, mainlogin::class.java)
        loginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginActivity)
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {


            })

        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback {
                AccessToken.setCurrentAccessToken(null)
                LoginManager.getInstance().logOut()

                finish()
            }).executeAsync()
        }
    }


}