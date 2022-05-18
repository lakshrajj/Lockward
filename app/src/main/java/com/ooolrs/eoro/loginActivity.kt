package com.ooolrs.eoro

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class loginActivity : AppCompatActivity() {
    private lateinit var ref: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ref = FirebaseAuth.getInstance()

        var user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        else{
            login()
        }

        register()
    }

    private fun login() {
        LoginBtn.setOnClickListener {
           // val date = Calendar.getInstance()
          //  val timeInSecs = date.timeInMillis
          //  val afterAdding10Mins = Date(timeInSecs + 10 * 60 * 1000)
          //  println("After adding 10 mins : $afterAdding10Mins")
            if (Patterns.EMAIL_ADDRESS.matcher(UserET.text.toString().trim()).matches()){

            } else{
            UserET.setError("Enter Correct Email")
            return@setOnClickListener
            }

            if(TextUtils.isEmpty(UserET.text.toString().trim())){
                UserET.setError("Please Enter Email")
                return@setOnClickListener

            } else if(TextUtils.isEmpty(PassET.text.toString().trim())){
                PassET.setError("Please Enter Password! Duh !")
                return@setOnClickListener
            }

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("loading, please wait")
            progressDialog.show()
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);

            var user = UserET.text.toString().trim()
            var pass = PassET.text.toString().trim()

            if(TextUtils.isEmpty(PassET.text.toString())){
                return@setOnClickListener
            }else if(TextUtils.isEmpty(UserET.text.toString())){
                return@setOnClickListener
            }

            ref.signInWithEmailAndPassword(user,pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Login Failed, Try Again !!", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun register() {
        regBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}