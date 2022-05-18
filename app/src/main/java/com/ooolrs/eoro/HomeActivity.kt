package com.ooolrs.eoro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_playmain.*

class HomeActivity : AppCompatActivity() {
    var dbref: DatabaseReference? = null
    var database: FirebaseDatabase? =null
    private lateinit var ref: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ref = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbref = database?.reference?.child("profile")

        play()
        addpoints()
        rules()
        profile()
        pointshow()

    }

    private fun pointshow() {
        dbref?.child(ref.currentUser?.uid!!)?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pointView.text ="Balance : "+snapshot.child("points").value.toString()+" Points"
            }
            override fun onCancelled(error: DatabaseError) {
            }})

    }

    private fun play() {
        platBtn.setOnClickListener {
            val intent = Intent(this, playmain::class.java)
            startActivity(intent)
        }
    }

    private fun addpoints() {
        addBtn.setOnClickListener {
            val intent = Intent(this, Pointgain::class.java)
            startActivity(intent)
        }
    }

    private fun rules() {
        ruleBtn.setOnClickListener {
            Toast.makeText(this, "Rules Will be Added Soon !", Toast.LENGTH_LONG).show()
        }
    }

    private fun profile() {
        profileBtn.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }
    }
}