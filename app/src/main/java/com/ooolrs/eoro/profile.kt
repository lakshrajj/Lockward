package com.ooolrs.eoro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Time
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*
import kotlin.concurrent.timerTask

class profile : AppCompatActivity() {

    lateinit var ref: FirebaseAuth
    var dbref: DatabaseReference?=null
    var database: FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ref = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbref = database?.reference?.child("profile")
        val newUser = dbref?.child((ref.currentUser?.uid!!))

        loadprofile()
        signout()

    }

    private fun signout() {
        Psignout.setOnClickListener {
            Toast.makeText(this, "Singed Out !!", Toast.LENGTH_LONG).show()

            ref.signOut()

            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
    }



    private fun loadprofile() {
        val user = ref.currentUser
        val CUser= dbref?.child(user?.uid!!)

        CUser?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                PName.text = "Name : "+snapshot.child("name").value.toString()
                PEmail.text = "Email : "+snapshot.child("email").value.toString()
                PPoint.text ="Balance : "+ snapshot.child("points").value.toString()+" Points"
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}