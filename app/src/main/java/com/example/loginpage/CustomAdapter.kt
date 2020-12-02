package com.example.loginpage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView

lateinit var handler: DatabaseHelper
class CustomAdapter ( val userList :ArrayList<User>,var context: Context, var activity: Navigation) : RecyclerView.Adapter<CustomAdapter.ViewHolder>()
{


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
        var name1  = itemView.findViewById<TextView>(R.id.name1)

        var mailid1: TextView = itemView.findViewById(R.id.mailid1)

        var password1: TextView = itemView.findViewById(R.id.password1)

        var phoneno1: TextView = itemView.findViewById(R.id.phoneno1)
        var ages =itemView.findViewById<TextView>(R.id.age1)
        var image=itemView.findViewById<ImageView>(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val  v=LayoutInflater.from(parent.context).inflate(R.layout.list_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {

        handler = DatabaseHelper(context)
        val worker : User = userList[position]
        holder.itemView.getContext()
       holder.name1.text=worker.name
        holder.mailid1.text=worker.mailid
        holder.password1.text=worker.password
        holder.phoneno1.text=worker.phoneno
        holder.ages.text=worker.age
        holder.image.setOnClickListener {

            val popupMenu: PopupMenu = PopupMenu(context, holder.image)
            popupMenu.menuInflater.inflate(R.menu.popupmenu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit -> {
                        Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, edit_details::class.java)
                        val extras = Bundle()
                        extras.putString("id", "" + worker.id)
                        extras.putString("name", "" +worker.name)
                        extras.putString("mailid", "" +worker.mailid)
                        extras.putString("password", "" + worker.password)
                        extras.putString("phoneno", "" + worker.phoneno)
                        extras.putString("age", "" + worker.age)
                        intent.putExtras(extras)

                       activity.startActivityForResult(intent, 12345);


                    }
                    //Toast.makeText(itemView.context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()


                    R.id.delete -> {
                        Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        handler = DatabaseHelper(context)
                        handler.deleteData(worker.id!!)

                        userList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,userList.size)

                        if (userList.isEmpty())
                        {
                            activity.hide.visibility=View.VISIBLE
                            activity.recyclerView.visibility=View.GONE
                        }
                        else
                        {
                            activity.hide.visibility=View.GONE
                            activity.recyclerView.visibility=View.VISIBLE
                        }




                    }


                }
                true
            })
            popupMenu.show()

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}