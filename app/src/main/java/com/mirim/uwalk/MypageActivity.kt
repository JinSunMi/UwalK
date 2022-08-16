package com.mirim.uwalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mirim.uwalk.databinding.ActivityMypageBinding

class MypageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.imageToolbarProfile.visibility = View.GONE
        binding.toolbar.btnBack.visibility = View.VISIBLE
        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }

        binding.txtProfileEmail.text = User.user.email
        binding.txtProfileName.text = User.user.name
        binding.txtProfileLantern.text = User.user.lantern.toString()
        binding.txtProfileStreetlight.text = User.user.streetlight.toString()
    }
}