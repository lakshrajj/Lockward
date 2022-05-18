package com.ooolrs.lockward

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    var pressedTime: Long = 0

    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get()=
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyuser("Auth Error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyuser("Auth Success !")
                    startActivity(Intent(this@AuthActivity,HomeActivity::class.java))
                }
            }

    private fun notifyuser(s: String) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val shared : SharedPreferences = getSharedPreferences("Main" , Context.MODE_PRIVATE)

        var shared2:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val ss = shared2.getBoolean("bio",false)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        if (shared.getInt("USER",0)==0){
            val intent = Intent(this,InputKeyData::class.java)
            startActivity(intent)
        }

        enterPin.setOnClickListener {
            if (shared.getString("S_PIN","tree")==inputpin.text.toString()){
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }else{
                inputpin.setText("")
                inputpin.clearFocus()
                Toast.makeText(this,"Wrong Pin",Toast.LENGTH_SHORT).show()
            }
        }

        checkBiometricSupport()
        bioBtn.setOnClickListener{
            if(ss){
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Unlock Passwords.")
                .setSubtitle("Auth is Required")
                .setDescription("This app uses Fingerprint Protection to keep data secure. Please Unlock with fingerPrint.")
                .setNegativeButton("Cancel",this.mainExecutor, DialogInterface.OnClickListener{
                        dialog, which -> notifyuser("Auth Cancel")
                }).build()

            biometricPrompt.authenticate(getCancellationSignal(),mainExecutor,authenticationCallback)
        }else{notifyuser("Finger Print Security is Disabled.")}
        }}

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener { notifyuser("Auth Cancelled by User.") }
        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport():Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure){
            notifyuser("FingerPrint Auth has not been enabled in settings")
            return false
        }

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            notifyuser("FingerPrint Auth Permission is not enabled")
            return false
        }
        return  if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        }else true
    }
    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }
}