package com.mirim.uwalk

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mirim.uwalk.databinding.DialogDonateBinding


class DonateDialog(var text: String, var type: String): DialogFragment() {
    lateinit var binding: DialogDonateBinding

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var firebaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(context)
            binding = DialogDonateBinding.inflate(layoutInflater)

            firebaseDatabase = FirebaseDatabase.getInstance()
            firebaseReference = firebaseDatabase.getReference().child("user")

            auth = FirebaseAuth.getInstance()

            var uid = auth.currentUser?.uid

            var currentCount = 0

            binding.txtDonateConfirmTitle.text = text

            binding.btnDonateConfirmYes.setOnClickListener {
                firebaseReference.child(uid!!).child(type).runTransaction(object : Transaction.Handler {
                    override fun doTransaction(mutableData: MutableData): Transaction.Result {
                        val data = mutableData.getValue(Int::class.java)
                            ?: return Transaction.success(mutableData)
                        mutableData.value = data + 1
                        return Transaction.success(mutableData)
                    }

                    override fun onComplete(
                        databaseError: DatabaseError?,
                        b: Boolean,
                        dataSnapshot: DataSnapshot?
                    ) {
                    }
                })
                firebaseReference.child(uid!!).child("steps").runTransaction(object: Transaction.Handler {
                    override fun doTransaction(mutableData: MutableData): Transaction.Result {
                        val data = mutableData.getValue(Int::class.java)
                            ?: return Transaction.success(mutableData)
                        if(type.equals("lantern")) {
                            mutableData.value = data - 100000
                        }
                        else {
                            mutableData.value = data - 200000
                        }
                        return Transaction.success(mutableData)
                    }

                    override fun onComplete(
                        databaseError: DatabaseError?,
                        b: Boolean,
                        dataSnapshot: DataSnapshot?
                    ) {
                    }
                })
                dialog?.dismiss()
            }

            binding.btnDonateConfirmNo.setOnClickListener {
                dialog?.dismiss()
            }

            builder.setView(binding.root)
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}