package com.mirim.uwalk

import com.mirim.uwalk.R
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mirim.uwalk.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(binding.frame.id, HomeFragment()).commit()
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_home -> {
                    supportFragmentManager.beginTransaction().replace(binding.frame.id, HomeFragment()).commit()
                    true
                }
                R.id.page_donate -> {
                    supportFragmentManager.beginTransaction().replace(binding.frame.id, DonateFragment()).commit()
                    true
                }
                R.id.page_rank -> {
                    supportFragmentManager.beginTransaction().replace(binding.frame.id, RankFragment()).commit()
                    true
                }
                R.id.page_record -> {
                    supportFragmentManager.beginTransaction().replace(binding.frame.id, RecordFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}