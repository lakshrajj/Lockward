package com.ooolrs.lockward

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.ooolrs.lockward.db.Constants
import com.ooolrs.lockward.db.Model
import com.ooolrs.lockward.db.SQLHelper
import com.ooolrs.lockward.db.aes
import kotlinx.android.synthetic.main.activity_add_data.*
import kotlinx.android.synthetic.main.activity_input_key_data.*
import java.io.File
import java.io.FileWriter

class AddData : AppCompatActivity() {

    private lateinit var sqlHelper:SQLHelper
    private lateinit var shared : SharedPreferences
    private lateinit var shared2 : SharedPreferences
    private lateinit var aes : aes
    private var key =""


    public var id:String?=""
    private var passof:String?=""
    private var pass:String?=""
    private var username:String?=""
    private var addedtime:String?=""
    private var updatetime:String?=""

    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)


        shared2= PreferenceManager.getDefaultSharedPreferences(this)


        shared = getSharedPreferences("Test" , Context.MODE_PRIVATE)
        aes= aes()
        key = shared.getString("key","defaultName").toString()
        val intent = intent
        isEditMode = intent.getBooleanExtra("isEditMode",false)
        if(isEditMode){
            adddataTV.setText("Update Password Details.")
            id = intent.getStringExtra("ID")
            username = intent.getStringExtra("USERNAME")
            pass = intent.getStringExtra("PASS")
            passof = intent.getStringExtra("PASSOF")
            addedtime = intent.getStringExtra("ADDEDTIME")
            updatetime = intent.getStringExtra("UPDATETIME")


            PassofET.setText(passof)
            UsernameET.setText(username)
            Pass1ET.setText(aes.decrypt(key,pass))
            Pass2ET.setText(aes.decrypt(key,pass))

        }
        sqlHelper = SQLHelper(this)

        save.setOnClickListener {
            if(TextUtils.isEmpty(PassofET.text.toString())){
                PassofET.setError("Please Enter Title.")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(UsernameET.text.toString())){
                UsernameET.setError("Please Enter Username")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(Pass1ET.text.toString())){
                Pass1ET.setError("Please Enter Password")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(Pass2ET.text.toString())){
                Pass2ET.setError("Please Enter Password.")
                return@setOnClickListener
            }else if(Pass1ET.text.toString()!=Pass2ET.text.toString()){
                Pass2ET.setError("Password Does Not Match.")
                return@setOnClickListener
            }
            inputdata()
        }
    }
    fun exportCSV() {
        HomeActivity().reqPermission()
        val folder = File(Environment.getExternalStorageDirectory().toString()+"/BackUPFiles")

        var isFolderCreated = false
        if (!folder.exists()){ isFolderCreated = folder.mkdir() }

        val csvFilename = "BP"+".csv"

        val fileNameandPath = "$folder/$csvFilename"

        //Saving in csv File
        var recordList = ArrayList<Model>()
        recordList.clear()
        recordList = sqlHelper.getAllRecords("${Constants.C_ADDED_TIMESTAMP} ASC")

        try{
            val fw = FileWriter(fileNameandPath)
            for (i in recordList.indices){
                fw.append(""+recordList[i].id)
                fw.append(",")
                fw.append(""+recordList[i].passof)
                fw.append(",")
                fw.append(""+recordList[i].username)
                fw.append(",")
                fw.append(""+recordList[i].pass)
                fw.append(",")
                fw.append(""+recordList[i].addedtime)
                fw.append(",")
                fw.append(""+recordList[i].updatedTime)
                fw.append("\n")
            }
            fw.flush()
            fw.close()
            Toast.makeText(applicationContext,"Backed Up !!",Toast.LENGTH_LONG).show()
        } catch (e: Exception){
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()
        }
    }

    private fun inputdata() {
        pass = aes.encrypt(Pass1ET.text.toString().trim(),key)
        passof = ""+PassofET.text.toString().trim()
        //pass = ""+Pass1ET.text.toString().trim()
        username = ""+UsernameET.text.toString().trim()

        if(isEditMode){

            val timeStamp = "${System.currentTimeMillis()}"
            sqlHelper.updaterecord("$id","$username","$passof","$pass","$addedtime", timeStamp)
            exportCSV()
            Toast.makeText(this,"Updated .", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))

            if (shared2.getBoolean("autobackup",false)){
                exportCSV()
                Toast.makeText(this,"Backup Updated .", Toast.LENGTH_SHORT).show()
            }
        }else{
            val timestamp = System.currentTimeMillis()
            val id = sqlHelper.insertData(""+passof,""+username,""+pass,""+timestamp,""+timestamp)
            Toast.makeText(this,"Record Added !!", Toast.LENGTH_SHORT).show()

            if (shared2.getBoolean("autobackup",false)){
                exportCSV()
                Toast.makeText(this,"Backup Updated .", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onRestart() {
        super.onRestart()
        startActivity(Intent(this,AuthActivity::class.java))
    }
}