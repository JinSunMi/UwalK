package com.mirim.uwalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.mirim.uwalk.databinding.ActivitySignSelectBinding

class SignSelectActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignSelectBinding
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSelectSignIn.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
        binding.btnSelectSignUp.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        if(auth?.currentUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            User.fetchUser(auth!!.currentUser!!.uid)
            User.user.email = auth?.currentUser?.email.toString()
            finish()
        }
    }
}