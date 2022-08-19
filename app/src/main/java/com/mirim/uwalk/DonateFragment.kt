package com.mirim.uwalk

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mirim.uwalk.databinding.FragmentDonateBinding

class DonateFragment: Fragment() {
    lateinit var binding: FragmentDonateBinding
    var steps = 0

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var firebaseReference: DatabaseReference

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toolbar.imageToolbarProfile.setOnClickListener {
            startActivity(Intent(context, MypageActivity::class.java))
        }

        auth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseReference = firebaseDatabase.getReference().child("user")
//
//        setFragmentResultListener("currentSteps") { requestKey, bundle ->
//            if(bundle.getString("bundleKey")?.toInt()!! != steps) {
//                steps = bundle.getString("bundleKey")?.toInt()!!
//            }
//            binding.txtTotalStep.text = steps.toString()
//
//        }

        val sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        val uid = auth.currentUser?.uid
        firebaseReference.child(uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataMap = snapshot.value as HashMap<String, Any>
                if(context != null && isAdded) {

                    steps = (dataMap.get("steps").toString().toInt() + sharedPreferences.getFloat("key1", 0f).toInt())
                    binding.txtTotalStep.text = steps.toString()

                    if(steps >= 100000) {
                        binding.btnDonateLantern.isEnabled = true
                    }
                    else {
                        binding.btnDonateLantern.isEnabled = false
                    }
                    if(steps >= 200000) {
                        binding.btnDonateStreetlight.isEnabled = true
                    }
                    else {
                        binding.btnDonateStreetlight.isEnabled = false
                    }
                    binding.gridSun.removeAllViews()
                    for(i in 0 until 20) {
                        binding.gridSun.addView(makeSunView(i))
                    }
                    binding.btnDonateLantern.setOnClickListener {
                        val dialog = DonateDialog("solar lantern?", "lantern")
                        dialog.show(requireActivity().supportFragmentManager, "confirm")
                    }
                    binding.btnDonateStreetlight.setOnClickListener {
                        val dialog = DonateDialog("street light?", "streetlight")
                        dialog.show(requireActivity().supportFragmentManager, "confirm")
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("LoginActivity", "fail to get data")
            }

        })





        return view
    }

    fun makeSunView(i: Int): View {
        val sun = ImageView(context)
        // sun.layoutParams = ViewGroup.LayoutParams((36 * Resources.getSystem().displayMetrics.density + 0.5f).toInt(), (36 * Resources.getSystem().displayMetrics.density + 0.5f).toInt())
        if(i < steps/10000) {
            sun.setImageResource(R.drawable.icon_sun_solid_main)
        }
        else if(i == 9 || i == 19) {
            sun.setImageResource(R.drawable.icon_sun_stroke_main)
        }
        else {
            sun.setImageResource(R.drawable.icon_sun_stroke_gray)
        }
        //binding.gridSun.layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        val param = GridLayout.LayoutParams(
            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
        )
        param.width = (36 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        param.height = (36 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        // param.marginEnd = (14 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        param.setMargins(0, 0, 0, (14 * Resources.getSystem().displayMetrics.density + 0.5f).toInt())
        sun.layoutParams = param

        return sun
    }
}