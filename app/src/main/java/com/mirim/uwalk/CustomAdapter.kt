package com.mirim.uwalk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: Context?) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val titles = arrayOf("Potters Fields Park","Fasholer Park","Markfield Park","Jubilee Park","Backton park","Jubilee Country Park","Woodfield Park","Whitehall Gardens","Trinity Square Gardens","London Fields")
    private val details = arrayOf("189 Tooley St, London SE1 2UD","N1C 4AB London","43 Crowland Rd, London N15 6UL","Belvedere Rd, London SE1 7PG","E6 5QJ London","Thornet Wood Rd, London BR5 1DA","Cool Oak Ln, London NW9 7BH","Victoria Embankment, London SW1A 2HE","Tower Hill Station, 38 Trinity Square, London EC3N 4DJ","London Fields West Side, London E8 3EU")
    private val km = arrayOf("3km","3km","3km","3km","3km","3km","3km","3km","3km","3km")
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_layout, parent, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return titles.size
    }
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.parkTitle.text = titles[position]
        holder.parkDetail.text = details[position]
        holder.parkKm.text = km[position]
    }
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val parkTitle: TextView
        val parkDetail: TextView
        val parkKm : TextView

        init {
            parkTitle = itemView.findViewById(R.id.park_title)
            parkDetail = itemView.findViewById(R.id.park_detail)
            parkKm = itemView.findViewById(R.id.park_km)

            itemView.setOnClickListener {
                val position: Int = adapterPosition

                Toast.makeText(itemView.context, "you clicked on ${titles[position]}",Toast.LENGTH_LONG).show()
            }
        }
    }
}