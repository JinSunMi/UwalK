package com.mirim.uwalk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mirim.uwalk.databinding.FragmentRecordBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecordFragment: Fragment() {
    lateinit var binding: FragmentRecordBinding

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        val view = binding.root

        layoutManager = LinearLayoutManager(context)

        binding.recyclerView.layoutManager = layoutManager

        adapter= CustomAdapter(context)

        binding.recyclerView.adapter = adapter
        return view
    }
}