package com.mirim.uwalk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AlarmReceiver: BroadcastReceiver() {

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var firebaseReference: DatabaseReference

    lateinit var auth: FirebaseAuth

    override fun onReceive(p0: Context?, p1: Intent?) {

        auth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseReference = firebaseDatabase.getReference().child("user")

        var steps = p1?.getIntExtra("steps", 0)
        firebaseReference.child(auth.currentUser?.uid!!).child("steps").runTransaction(object: Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val data = mutableData.getValue(Int::class.java)
                    ?: return Transaction.success(mutableData)
                mutableData.value = data + steps!!
                return Transaction.success(mutableData)
            }

            override fun onComplete(
                databaseError: DatabaseError?,
                b: Boolean,
                dataSnapshot: DataSnapshot?
            ) {
            }
        })
    }
}