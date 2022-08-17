package com.mirim.uwalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mirim.uwalk.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flip.setFlipInterval(5000);
        binding.flip.startFlipping();

        binding.txtSkip.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        binding.txtStart.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        binding.flip.addOnLayoutChangeListener(object: View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                p1: Int,
                p2: Int,
                p3: Int,
                p4: Int,
                p5: Int,
                p6: Int,
                p7: Int,
                p8: Int
            ) {
                if(binding.flip.currentView == binding.flipImage1) {
                    binding.flipCircle1.setBackgroundResource(R.drawable.background_main_circle_width_2dp)
                    binding.flipCircle2.setBackgroundResource(R.drawable.background_solid_gray_circle)
                    binding.flipCircle3.setBackgroundResource(R.drawable.background_solid_gray_circle)
                    binding.flipCircle4.setBackgroundResource(R.drawable.background_solid_gray_circle)
                }
                else if(binding.flip.currentView == binding.flipImage2) {
                    binding.flipCircle2.setBackgroundResource(R.drawable.background_main_circle_width_2dp)
                    binding.flipCircle1.setBackgroundResource(R.drawable.background_solid_gray_circle)
                    binding.flipCircle3.setBackgroundResource(R.drawable.background_solid_gray_circle)
                    binding.flipCircle4.setBackgroundResource(R.drawable.background_solid_gray_circle)
                }
                else if(binding.flip.currentView == binding.flipImage3) {
                    binding.flipCircle3.setBackgroundResource(R.drawable.background_main_circle_width_2dp)
                    binding.flipCircle1.setBackgroundResource(R.drawable.background_solid_gray_circle)
                    binding.flipCircle2.setBackgroundResource(R.drawable.background_solid_gray_circle)
                    binding.flipCircle4.setBackgroundResource(R.drawable.background_solid_gray_circle)
                }
                else {
                    binding.txtStart.visibility = View.VISIBLE
                    binding.flip.isAutoStart = false
                    binding.flipCircle4.setBackgroundResource(R.drawable.background_main_circle_width_2dp)
                    binding.flipCircle1.setBackgroundResource(R.drawable.background_solid_gray_circle)
                    binding.flipCircle2.setBackgroundResource(R.drawable.background_solid_gray_circle)
                    binding.flipCircle3.setBackgroundResource(R.drawable.background_solid_gray_circle)
                }
            }

        })
    }
}