package com.mirim.uwalk

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mirim.uwalk.model.UserInfo

object User {
    var user = UserInfo("", "", 0, 0, 0)

    fun fetchUser(uid: String) {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.getReference().child("user")

        firebaseReference.child(uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataMap = snapshot.value as HashMap<String, Any>
                User.user.name = dataMap.get("name").toString()
                User.user.lantern = dataMap.get("lantern").toString().toInt()
                User.user.streetlight = dataMap.get("streetlight")?.toString()?.toInt()
                User.user.steps = dataMap.get("steps")?.toString()?.toInt()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("LoginActivity", "fail to get data")
            }

        })
    }
}