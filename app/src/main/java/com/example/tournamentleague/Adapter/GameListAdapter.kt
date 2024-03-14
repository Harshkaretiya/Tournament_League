package com.example.tournamentleague.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tournamentleague.Activity.TournamentFolder.TournamentListActivity
import com.example.tournamentleague.Model.GameListModel
import com.example.tournamentleague.R

class GameListAdapter(var context: Context, var list: MutableList<GameListModel>) : RecyclerView.Adapter<MyView>()
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
        holder.gameType.setText(list[position].gametype)

        holder.itemView.setOnClickListener {
            var i = Intent(context, TournamentListActivity::class.java)
            i.putExtra("gameid",list[position].gameid)
            i.putExtra("gamename",list[position].name)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
//        Toast.makeText(context, list[position].name, Toast.LENGTH_SHORT).show()
    }
}
class MyView(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var image : ImageView =itemView.findViewById(R.id.image)
    var name : TextView =itemView.findViewById(R.id.name)
    var gameType : TextView = itemView.findViewById(R.id.gameType)
}