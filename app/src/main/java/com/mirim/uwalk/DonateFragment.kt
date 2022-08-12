package com.mirim.uwalk

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
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

        for(i in 0 until 20) {
            binding.gridSun.addView(makeSunView(i))
        }

        binding.toolbar.imageToolbarProfile.setOnClickListener {
            startActivity(Intent(context, MypageActivity::class.java))
        }

        return view
    }
    fun makeSunView(i: Int): View {
        val sun = ImageView(context)
        sun.layoutParams = ViewGroup.LayoutParams((36 * Resources.getSystem().displayMetrics.density + 0.5f).toInt(), (36 * Resources.getSystem().displayMetrics.density + 0.5f).toInt())
        if(i == 9 || i == 19) {
            sun.setImageResource(R.drawable.icon_sun_stroke_main)
        }
        else {
            sun.setImageResource(R.drawable.icon_sun_stroke_gray)
        }
        return sun
    }
}