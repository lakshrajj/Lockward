package com.ooolrs.eoro

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var ref: FirebaseAuth
    var dbref: DatabaseReference? = null
    var database: FirebaseDatabase? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        ref = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbref = database?.reference?.child("profile")

        regiterNewUser()
    }

    private fun regiterNewUser() {
        newregBtn.setOnClickListener {

            if(TextUtils.isEmpty(regName.text.toString())){
                regName.setError("Please Enter Name")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(regEmail.text.toString())){
                regEmail.setError("Please Enter Email")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(regPass.text.toString())){
                regPass.setError("Please Enter Password")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(regPass2.text.toString())){
                regPass2.setError("Please Enter Password")
                return@setOnClickListener
            }else if(regPass2.text.toString()!=regPass.text.toString()){
                regPass2.setError("Password Do Not Match !!")
                return@setOnClickListener
            }
            if (Patterns.EMAIL_ADDRESS.matcher(regEmail.text.toString().trim()).matches()){ }
            else{
                regEmail.setError("Enter Correct Email")
                return@setOnClickListener }

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("loading, please wait")
            progressDialog.show()
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);

            ref.createUserWithEmailAndPassword(regEmail.text.toString(), regPass.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val newUser = dbref?.child((ref.currentUser?.uid!!))

                        newUser?.child("name")?.setValue(regName.text.toString())
                        newUser?.child("email")?.setValue(regEmail.text.toString())
                        newUser?.child("points")?.setValue(1000)
                        newUser?.child("eorowinstreak")?.setValue(0)

                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show()
                        progressDialog.dismiss()

                        ref.signOut()

                        val intent = Intent(this, loginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Registration Failed, Try Again !!", Toast.LENGTH_LONG)
                            .show()
                        progressDialog.dismiss()
                    }
                }
        }
    }
}