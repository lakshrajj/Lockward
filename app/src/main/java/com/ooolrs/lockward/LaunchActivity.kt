package com.ooolrs.lockward


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val shared2:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val ss = shared2.getBoolean("darkmode",true)
        if(shared2.getBoolean("tmode",true)){

        if (ss){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }}else{AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val shared : SharedPreferences = getSharedPreferences("Main" , Context.MODE_PRIVATE)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val animDrawable = launchxml.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(3000)
        animDrawable.start()

        val anim = AnimationUtils.loadAnimation(this,R.anim.stripe_anim)
        stripe.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed({
            if (shared.getInt("USER",0)==0){
                val intent = Intent(this,InputKeyData::class.java)
                startActivity(intent)
            }else if (shared.getInt("USER",0)==1){
                val intent = Intent(this,AuthActivity::class.java)
                startActivity(intent)
            }
        }, 1500)
    }
}