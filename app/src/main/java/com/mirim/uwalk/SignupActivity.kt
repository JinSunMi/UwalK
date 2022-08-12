package com.mirim.uwalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mirim.uwalk.databinding.ActivitySignupBinding
import com.mirim.uwalk.model.UserInfo

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var firebaseReference: DatabaseReference

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.upBtnSignUp.setOnClickListener {
            Toast.makeText(applicationContext, "button clicked", Toast.LENGTH_SHORT).show()
            val email = binding.upEditEmail.text.toString()
            val password = binding.upEditPassword.text.toString()
            // val name = binding.upEditName.text.toString()
            startSignUp(email, password)
        }
    }
    private fun startSignUp(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseReference = firebaseDatabase.reference.child("user")
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                val user = auth.currentUser
                val userEmail = user?.email
                val userId = user!!.uid
                val userName = binding.upEditName

                firebaseReference.push().setValue(userId)
//                firebaseReference.child(userId).child("email").setValue(userEmail)
//                firebaseReference.child(userId).child("name").setValue("namename")

                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}