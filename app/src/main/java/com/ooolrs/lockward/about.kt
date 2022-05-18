package com.ooolrs.lockward

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class about : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    override fun onRestart() {
        super.onRestart()
        startActivity(Intent(this,AuthActivity::class.java))
    }
}