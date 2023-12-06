package com.example.tournamentleague.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tournamentleague.Model.HomePageListModel
import com.example.tournamentleague.R

class HomePageListAdapter(var context: Context, var list: MutableList<HomePageListModel>) : RecyclerView.Adapter<MyView>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val data = LayoutInflater.from(context)
        val view = data.inflate(R.layout.desing_popular_tournament,parent,false)
        return MyView(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: MyView, @SuppressLint("RecyclerView") position: Int) {

        if (list[position].image == "bgmi")
        {
            holder.image.setImageResource(R.drawable.bgmi)
        }

        holder.name.setText(list[position].name)
        holder.desc.setText(list[position].desc)
//        Toast.makeText(context, list[position].name, Toast.LENGTH_SHORT).show()
    }
}
class MyView(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var image : ImageView =itemView.findViewById(R.id.image)
    var name : TextView =itemView.findViewById(R.id.name)
    var desc : TextView = itemView.findViewById(R.id.desc)
}