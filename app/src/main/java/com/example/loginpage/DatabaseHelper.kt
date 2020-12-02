package com.example.loginpage

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast


class DatabaseHelper(var context: Context): SQLiteOpenHelper(context, dbname, factory, version)
{
    companion object
    {
        internal val dbname ="userdb"
        internal val factory=null
        internal val version=1

        private val DATABASE_VERSION = 1

        // Database name
        private val DATABASE_NAME = "database_name"

        /*** Database Tables ***/
        /*** Database Tables  */
        /** Events  */ // Event table
        private val TABLE = "event"

        // Event table columns
        private val NAME = "name"
        private val COLUMN_EVENT_EID ="id"

        private val MAIL_ID = "mailid"

        private val PASSWORD = "password"
        private val PHONE_NUMBER = "number"
        private val AGE="age"
        private val GENDER="gender"

    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        Log.e("MyApp", "onCreate invoked")
        // Tables creation queries
        // Tables creation queries


        val CREATE_EVENT_TABLE = ("create table " + TABLE + "(" + COLUMN_EVENT_EID + " integer primary key, "
                + NAME + " text, "
                + MAIL_ID + " text, "
                + PASSWORD + " text," + PHONE_NUMBER + " text," + AGE + " text)")
        Log.e("query", "" + CREATE_EVENT_TABLE)

        // Creating tables

        // Creating tables
        db!!.execSQL(CREATE_EVENT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

db!!.execSQL("DROP TABLE IF EXISTS"+ TABLE)
        onCreate(db)

    }

    fun insertUserData(name: String, mailid: String, password: String, phoneno: String, age: String)
    {
        try {
            Log.e("a", "b")
            val db: SQLiteDatabase=writableDatabase
            Log.e("a", "b")
            val values = ContentValues()
            values.put(NAME, name)
            values.put(MAIL_ID, mailid)
            values.put(PASSWORD, password)
            values.put(PHONE_NUMBER, phoneno)
            values.put(AGE, age)



            val id = db.insert(TABLE, null, values)
            db.close() // Closing database connection


            Log.d(TAG, "New user inserted into sqlite: $id")

            Log.e("", "" + id)
            if (id == (0).toLong()) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }

        }catch (ex: Exception)
        {
            Log.e("", "", ex)
        }

    }
     fun userPresent(mailid: String, password: String): HashMap<String, String>
    {
        val hashMap:HashMap<String, String> = HashMap()
       var id:String=""
        val db=writableDatabase
        try {
            val query = "SELECT * FROM " + TABLE + " WHERE " +
                    MAIL_ID + " = '$mailid' AND " + PASSWORD + " = '$password'"

            val cursor = db.rawQuery(query, null)
            Log.e("a", "${cursor.count} $query")
            if (cursor.moveToFirst()) {
                do {
                   id = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_EID))
                    Log.e("cursor work", "   "+id)
                }

                while (cursor.moveToNext())
            }
            hashMap.put("sucess", "${query != (0).toString()}")
            hashMap.put("id",""+id)
            if (query == (0).toString()) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

            }
        }
        catch (e:Exception)
        {
            Log.e("trycatch","sdf      "+e)
        }


        return hashMap
    }


    fun addData(name: String, mailid: String, password: String, phoneno: String, age: String)
    {
        val db=writableDatabase
        val value=ContentValues()
        value.put(NAME, name)
        value.put(MAIL_ID, mailid)
        value.put(PASSWORD, password)
        value.put(PHONE_NUMBER, phoneno)
        value.put(AGE, age)

        val insert=db.insert(TABLE, null, value)

        if (insert==(0).toLong())
        {
            Toast.makeText(context, "NOT INSERT", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "DATA INSERTED", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(id: String)
    {
        val db=writableDatabase
        val data=db.delete(TABLE, COLUMN_EVENT_EID + "=" + id, null);
        Toast.makeText(context, "Record removedId", Toast.LENGTH_SHORT).show();
        db.close()

    }
    fun updateData(id: String, name: String, mailid: String, password: String, phoneno: String, age: String) : Boolean
    {

        val db=writableDatabase
        val value=ContentValues()
        value.put(NAME, name)
        value.put(MAIL_ID, mailid)
        value.put(PASSWORD, password)
        value.put(PHONE_NUMBER, phoneno)
        value.put(AGE, age)
        //Log.e("data","msg"+data)
        var data=db.update(TABLE, value, "$COLUMN_EVENT_EID=" + id, null)
        if (data == (0).toInt()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

db.close()
        return data != (0).toInt()
    }



    fun readData(): ArrayList<User> {
        val list: ArrayList<User> = ArrayList()
        val db = this.readableDatabase

        val query = "Select * from $TABLE"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = User()
              user.id = result.getString(result.getColumnIndex(COLUMN_EVENT_EID))
                user.name = result.getString(result.getColumnIndex(NAME))
                user.mailid=result.getString(result.getColumnIndex(MAIL_ID))
                user.password=result.getString(result.getColumnIndex(PASSWORD))
                user.phoneno=result.getString(result.getColumnIndex(PHONE_NUMBER))
                user.age=result.getString(result.getColumnIndex(AGE))
                list.add(user)

            }

            while (result.moveToNext())
        }
        result.close();
        db.close();
        //Log.e("error", "" + list)
        return list
    }


    fun getUser(id: String): User {

        val user = User()
        //HashMap<String, String> user = new HashMap<String, String>();
        val selectQuery = "SELECT  * FROM $TABLE WHERE $COLUMN_EVENT_EID = $id"
        //if (email != null) selectQuery += "and " + COLUMN_EMAIL.toString() + " = " + "'" + email.toString() + "'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        Log.e("database","______"+selectQuery)
        // Move to first row
        if (cursor.moveToFirst()) {
            do {

                user.id = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_EID))
                user.name = cursor.getString(cursor.getColumnIndex(NAME))
                user.mailid=cursor.getString(cursor.getColumnIndex(MAIL_ID))
                user.password=cursor.getString(cursor.getColumnIndex(PASSWORD))
                user.phoneno=cursor.getString(cursor.getColumnIndex(PHONE_NUMBER))
                user.age=cursor.getString(cursor.getColumnIndex(AGE))


            }

            while (cursor.moveToNext())
        }
        cursor.close();
        db.close();
        Log.e("error", "" + user)
        return user

    }

}
