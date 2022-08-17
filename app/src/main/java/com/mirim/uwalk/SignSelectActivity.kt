package com.mirim.uwalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mirim.uwalk.databinding.ActivitySignSelectBinding

class SignSelectActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectSignIn.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
        binding.btnSelectSignUp.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
            finish()
        }

    }
}