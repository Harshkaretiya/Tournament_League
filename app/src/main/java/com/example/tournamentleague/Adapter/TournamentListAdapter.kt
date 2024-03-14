package com.example.tournamentleague.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ContentInfoCompat.Flags
import androidx.recyclerview.widget.RecyclerView
import com.example.tournamentleague.Activity.TournamentFolder.TournamentViewActivity
import com.example.tournamentleague.Model.TournamentListModel
import com.example.tournamentleague.R

class TournamentListAdapter(var context: Context, var list: MutableList<TournamentListModel>) : RecyclerView.Adapter<MyView2>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView2 {
        val data = LayoutInflater.from(context)
        val view = data.inflate(R.layout.design_tournament_list,parent,false)
        return MyView2(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyView2, position: Int) {
        holder.dateTime.setText(list[position].datetime)
        holder.totalPrice.setText(list[position].totalprice.toString())
        holder.remainingEntries.setText(list[position].currententries.toString())
        holder.maxEntries.setText(list[position].maxentries.toString())
        holder.gameType.setText(list[position].gametype)

        var setentryfee = list[position].entryfee
        if(setentryfee == 0)
            holder.entryFee.setText("Free")
        else
            holder.entryFee.setText(setentryfee.toString())

        holder.itemView.setOnClickListener {
            var i = Intent(context,TournamentViewActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("tournamentid",list[position].tournamentid)
            context.startActivity(i)
        }
    }
}
class MyView2(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var dateTime:TextView = itemView.findViewById(R.id.dateTime)
    var totalPrice:TextView = itemView.findViewById(R.id.totalPrice)
    var remainingEntries:TextView = itemView.findViewById(R.id.remainingEntries)
    var maxEntries:TextView = itemView.findViewById(R.id.maxEntries)
    var gameType:TextView = itemView.findViewById(R.id.gameType)
    var entryFee:TextView = itemView.findViewById(R.id.entryFee)
}