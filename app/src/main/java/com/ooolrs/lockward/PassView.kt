package com.ooolrs.lockward

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.ooolrs.lockward.db.Constants
import com.ooolrs.lockward.db.RVAdapter
import com.ooolrs.lockward.db.SQLHelper
import com.ooolrs.lockward.db.aes
import kotlinx.android.synthetic.main.activity_pass_view.*
import java.util.*

class PassView : AppCompatActivity() {

    private lateinit var sqlHelper: SQLHelper
    private var recordID: String? = null
    lateinit var shared : SharedPreferences
    lateinit var shared2 : SharedPreferences
    lateinit var aes : aes
    var cpy = ""
    var id = ""

    var username = ""
    var passof = ""
    var pass = ""
    var addedtime = ""
    var updatedtime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_view)

        shared = getSharedPreferences("Test" , Context.MODE_PRIVATE)
        shared2= PreferenceManager.getDefaultSharedPreferences(this)
        aes=aes()

        sqlHelper = SQLHelper(this)

        val intent = intent
        recordID = intent.getStringExtra("RECORD_ID")

        showDetails()

        CopyBtn2.setOnClickListener{
            val cp = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", cpy)
            cp.setPrimaryClip(clipData)
            Toast.makeText(this, "Password copied", Toast.LENGTH_SHORT).show()
        }

        editBtn.setOnClickListener {
            val intent = Intent(this, AddData::class.java)
            intent.putExtra("ID", id)
            intent.putExtra("PASSOF", passof)
            intent.putExtra("PASS", pass)
            intent.putExtra("USERNAME", username)
            intent.putExtra("ADDEDTIME", addedtime)
            intent.putExtra("UPDATETIME", updatedtime)
            intent.putExtra("isEditMode", true)
            startActivity(intent)
        }

        DelBtn.setOnClickListener {
            sqlHelper.deleteRecord(id)
            Toast.makeText(this, "Password Deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
        }
        screenshotperm()

    }

    private fun showDetails() {
        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} WHERE ${Constants.C_ID} LIKE '%$recordID%'  "
        val db = sqlHelper.writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do {
                 id =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ID))
                username =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_USERNAME))
                passof =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PASSOF))
                pass =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PASS))
                addedtime =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIMESTAMP))
                updatedtime =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIMESTAMP))

                val calendar1 = Calendar.getInstance(Locale.getDefault())
                calendar1.timeInMillis = addedtime.toLong()
                val timeadded = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar1)


                val calendar2 = Calendar.getInstance(Locale.getDefault())
                calendar2.timeInMillis = updatedtime.toLong()
                val timeupdate = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar2)

                val key = shared.getString("key","defaultName")

                PASSOFTV.text = passof
                PASSTV.text = aes.decrypt(key.toString(),pass)
                cpy = aes.decrypt(key.toString(),pass).toString()
                USERNAMETV.text = username
                ADDEDTIMETV.text = timeadded
                UPDATEDTIMETV.text = timeupdate

            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

    }
    private fun screenshotperm() {
        val ss = shared2.getBoolean("screenshot",true)

        if (ss==false){
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    override fun onRestart() {
        super.onRestart()
        startActivity(Intent(this,AuthActivity::class.java))
    }
}