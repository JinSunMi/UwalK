package com.mirim.uwalk

import android.content.Context
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirim.uwalk.model.Park
import kotlin.math.roundToLong


class CustomAdapter(private val context: Context, private val list: MutableList<Park>, val lat: Double, val lon: Double) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_layout, parent, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.parkTitle.text = list[position].name
        holder.parkDetail.text = list[position].address

        val currentLocation = Location("A")
        currentLocation.latitude = lat
        currentLocation.longitude = lon

        val destinationLocation = Location("B")
        destinationLocation.latitude = list[position].lat
        destinationLocation.longitude = list[position].lon

        val distance: Float = currentLocation.distanceTo(destinationLocation)
        val distanceStr = String.format("%.2f", distance/1000)
        holder.parkKm.text = distanceStr + "Km"
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

            }
        }
    }
}