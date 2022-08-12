package com.mirim.uwalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mirim.uwalk.databinding.ActivityMypageBinding

class MypageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
    }
}