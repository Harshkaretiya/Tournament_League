package com.example.tournamentleague.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tournamentleague.Model.FriendInviteModel
import com.example.tournamentleague.R
import com.google.android.material.card.MaterialCardView

class InviteTeamMemberAdapter(var context: Context, var list: MutableList<FriendInviteModel>) : RecyclerView.Adapter<MyView3>()
{
    private val selectedItemsIndices = mutableSetOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView3 {
        val data = LayoutInflater.from(context)
        val view = data.inflate(R.layout.design_invite_team_member_adapter,parent,false)
        return MyView3(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: MyView3, @SuppressLint("RecyclerView") position: Int) {

        holder.name.text = list[position].friendname
        holder.checked.visibility = if (selectedItemsIndices.contains(position)) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            if (selectedItemsIndices.size < 4 || selectedItemsIndices.contains(position)) {
                toggleItemSelection(position)
            } else {
                Toast.makeText(context, "Maximum invite limit reached", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun toggleItemSelection(position: Int) {
        if (selectedItemsIndices.contains(position)) {
            selectedItemsIndices.remove(position)
        } else {
            selectedItemsIndices.add(position)
        }
        notifyDataSetChanged() // Notify adapter that data set has changed
    }
    fun getSelectedItems(): List<FriendInviteModel> {
        return selectedItemsIndices.map { list[it] }
    }
}
class MyView3(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    var name : TextView =itemView.findViewById(R.id.friendName)
    var checked : MaterialCardView =itemView.findViewById(R.id.checked)
}