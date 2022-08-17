package com.mirim.uwalk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mirim.uwalk.databinding.FragmentRankBinding
import com.mirim.uwalk.model.UserInfo

class RankFragment: Fragment() {
    lateinit var binding: FragmentRankBinding

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var firebaseReference: DatabaseReference

    lateinit var auth: FirebaseAuth

    val list = mutableListOf<UserInfo?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBinding.inflate(inflater, container, false)
        val view = binding.root

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseReference = firebaseDatabase.reference.child("user")

        auth = FirebaseAuth.getInstance()

        binding.toolbar.imageToolbarLogo.setImageResource(R.drawable.icon_logo_gray)
        binding.toolbar.imageToolbarProfile.setImageResource(R.drawable.icon_person_gray)

        binding.toolbar.imageToolbarProfile.setOnClickListener {
            startActivity(Intent(context, MypageActivity::class.java))
        }

        var rank = 1

        val query = firebaseReference.orderByChild("steps")
        query.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val hashMap = snapshot.value as HashMap<String, Any>
                val user = UserInfo(name = hashMap["name"].toString(), steps = hashMap["steps"].toString().toInt())
                list.add(user)
                binding.recyclerRank.layoutManager = LinearLayoutManager(context)
                binding.recyclerRank.setHasFixedSize(false)
                var adapter = RankAdapter(context, list.asReversed())
                binding.recyclerRank.adapter = adapter

                if(hashMap["email"].toString().equals(auth.currentUser?.email)) {
                    binding.txtRankMySteps.text = user.steps.toString()
                    binding.txtRankMyName.text = user.name
                }
                if(User.user!!.steps!! < user.steps!!) {
                    rank++;
                }
                binding.txtRankMyNumber.text = rank.toString()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })



        return view
    }
}