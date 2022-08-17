package com.mirim.uwalk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirim.uwalk.model.UserInfo

class RankAdapter(val context: Context?, val list: MutableList<UserInfo?>):
    RecyclerView.Adapter<RankAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtRankNumber = itemView.findViewById<TextView>(R.id.txt_rank_number)
        var imageRankProfile = itemView.findViewById<ImageView>(R.id.image_rank_profile)
        var txtRankName = itemView.findViewById<TextView>(R.id.txt_rank_name)
        var txtRankStep = itemView.findViewById<TextView>(R.id.txt_rank_steps)
        fun bind(user: UserInfo, position: Int) {
            txtRankNumber.text = "${position+1}"
            txtRankName.text = user.name
            txtRankStep.text = user.steps.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rank_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position]!!, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}