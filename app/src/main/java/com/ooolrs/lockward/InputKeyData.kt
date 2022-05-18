package com.ooolrs.lockward

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_key_data.*

class InputKeyData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_key_data)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        var shared : SharedPreferences = getSharedPreferences("Main" , Context.MODE_PRIVATE)
        val editor = shared.edit()

        saveBtn.setOnClickListener {
            val keyy = keyinput.text.toString()
            val pin = securityPin.text.toString()

            if(TextUtils.isEmpty(keyinput.text.toString())){
                keyinput.setError("Key Input is Empty.")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(securityPin.text.toString())){
                securityPin.setError("Security Pin is Empty.")
                return@setOnClickListener
            }else if(keyinput.text.toString().length>=13){
                keyinput.setError("Key Should be of Maximum 12 Characters.")
                return@setOnClickListener
            }else if(securityPin.text.toString().length!=4){
                securityPin.setError("Securitp Pin Should be of 4 Digit")
                return@setOnClickListener
            }
            editor.putString("key",keyy)
            editor.putInt("USER",1)
            editor.putString("S_PIN",pin)
            editor.apply()

            val intent = Intent(this,AuthActivity::class.java)
            startActivity(intent)
        }
    }
}