package com.ooolrs.eoro


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_playmain.*
import java.util.*

class playmain : AppCompatActivity() {
    var dbref: DatabaseReference? = null
    var database: FirebaseDatabase? =null
    private lateinit var ref: FirebaseAuth
    var rnds=0
    var re="Error"
    var wins=0
    var newPoints=1
    var cwin=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playmain)


        database = FirebaseDatabase.getInstance()
        ref = FirebaseAuth.getInstance()
        dbref = database?.reference?.child("profile")

        currentPoint()

        even.setOnClickListener {
            numberGen("even")
        }
        odd.setOnClickListener {
            numberGen("odd")
            }
    }

    private fun currentPoint() {
        dbref?.child(ref.currentUser?.uid!!)?.addValueEventListener(object: ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                curBal.text ="Points : "+snapshot.child("points").value.toString()
                cwin = snapshot.child("eorowinstreak").value.toString().toInt()
                streak.text ="Highest Streak : "+cwin.toString()
                newPoints= snapshot.child("points").value.toString().toInt()
            }
            override fun onCancelled(error: DatabaseError) {
            }})
    }

    private fun numberGen(N:String) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
                rnds = (1..100).random().toString().toInt()
                ranNum.text = rnds.toString()

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                if(rnds%2==0 && N=="even"){
                    stat.text = "Congrats !! Number is Even, Points Added in your Wallet."
                    re = "Win"
                }else if (rnds%2==0 && N=="odd"){
                    stat.text = "Shheshh !! Number is Even"
                    re = "Lose"
                }else if(rnds%2!=0 && N=="even"){
                    stat.text = "Shheshh !! Number is Odd"
                    re = "Lose"
                }else{
                    stat.text = "Congrats !! Number is Odd, Points Added in your Wallet."
                    re = "Win"
                }
                winlose.text = re
                if(re=="Win"){win()}
                if(re=="Lose"){loss()}}, 500)
        }, 1000)
    }

    private fun loss() {
        dbref?.child((ref.currentUser?.uid!!))?.child("points")?.setValue(newPoints-100)
        wins = 0
        cstreak.text = "Current Streak : "+wins.toString()
    }

    private fun win() {
        dbref?.child((ref.currentUser?.uid!!))?.child("points")?.setValue(newPoints+75)
        wins+=1
        if(cwin<wins){
        dbref?.child((ref.currentUser?.uid!!))?.child("eorowinstreak")?.setValue(wins)}
        cstreak.text = "Current Streak : "+wins.toString()
        }

}