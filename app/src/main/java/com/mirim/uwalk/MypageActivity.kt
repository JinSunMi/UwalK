package com.mirim.uwalk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mirim.uwalk.databinding.ActivityMypageBinding

class MypageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMypageBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.toolbar.imageToolbarProfile.visibility = View.GONE
        binding.toolbar.btnBack.visibility = View.VISIBLE
        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }

        binding.txtProfileEmail.text = User.user!!.email
        binding.txtProfileName.text = User.user!!.name
        binding.txtProfileLantern.text = User.user!!.lantern.toString()
        binding.txtProfileStreetlight.text = User.user!!.streetlight.toString()

        binding.btnHowToUse.setOnClickListener {
            startActivity(Intent(applicationContext, StoryActivity::class.java))
        }

        binding.txtLogout.setOnClickListener {
            auth.signOut()
            var intent = Intent(applicationContext, SignSelectActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        User.fetchUser(auth.uid!!)
    }
}