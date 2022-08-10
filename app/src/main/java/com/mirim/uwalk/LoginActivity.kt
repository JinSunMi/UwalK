package com.mirim.uwalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mirim.uwalk.databinding.ActivityLoginBinding
import com.mirim.uwalk.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_login)
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}