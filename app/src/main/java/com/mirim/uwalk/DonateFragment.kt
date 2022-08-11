package com.mirim.uwalk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mirim.uwalk.databinding.FragmentDonateBinding

class DonateFragment: Fragment() {
    lateinit var binding: FragmentDonateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonateBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
}