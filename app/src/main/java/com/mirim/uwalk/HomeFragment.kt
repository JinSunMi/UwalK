package com.mirim.uwalk

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mirim.uwalk.databinding.FragmentHomeBinding

class HomeFragment: Fragment(), SensorEventListener {
    lateinit var binding: FragmentHomeBinding
    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toolbar.imageToolbarProfile.setOnClickListener {
            startActivity(Intent(context, MypageActivity::class.java))
        }

        loadData()
        resetSteps()

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager


        return view
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null) {
            Toast.makeText(context, "No sensor detected on this device", Toast.LENGTH_SHORT).show()

        }
        else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)

        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running) {
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            binding.txtCurrentStep.text = "${currentSteps}"
        }
    }

    fun resetSteps() {
        binding.txtCurrentStep.setOnClickListener {
            // This will give a toast message if the user want to reset the steps
            Toast.makeText(context, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        binding.txtCurrentStep.setOnLongClickListener {

            previousTotalSteps = totalSteps

            // When the user will click long tap on the screen,
            // the steps will be reset to 0
            binding.txtCurrentStep.text = 0.toString()

            // This will save the data
            saveData()

            true
        }
    }

    private fun saveData() {
        val sharedPreferences = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)

        val editor = sharedPreferences?.edit()
        editor?.putFloat("key1", previousTotalSteps)
        editor?.apply()
    }

    private fun loadData() {

        // In this function we will retrieve data
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)

        // Log.d is used for debugging purposes
        Log.d("HomeFragment", "$savedNumber")

        previousTotalSteps = savedNumber
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}