package com.ooolrs.lockward

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.ooolrs.lockward.db.Constants
import com.ooolrs.lockward.db.Model
import com.ooolrs.lockward.db.RVAdapter
import com.ooolrs.lockward.db.SQLHelper
import com.opencsv.CSVReader
import kotlinx.android.synthetic.main.activity_home.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class HomeActivity : AppCompatActivity() {

    lateinit var sqlHelper: SQLHelper
    var pressedTime: Long = 0
    lateinit var toggle: ActionBarDrawerToggle

    private val NEWEST_FIRST = "${Constants.C_ADDED_TIMESTAMP} DESC"
    private val OLDEST_FIRST = "${Constants.C_ADDED_TIMESTAMP} ASC"
    private val TITLE_ASC = "${Constants.C_PASSOF} ASC"
    private val TITLE_DESC = "${Constants.C_PASSOF} DESC"
    private var recentorder = NEWEST_FIRST


    public var s=""

    //-------For Storage Permission
    private val STORAGE_REQUEST_CODE_EXPORT = 1
    private val STORAGE_REQUEST_CODE_IMPORT = 2
    private val STORAGE_MAIN = 3
    private lateinit var storagePermission:Array<String>
    private lateinit var storagePermission2:Array<String>
    var shared: SharedPreferences? = null
    var shared2: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("")

        if(!checkStroagePer()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                reqPermission()
            }else {
                reqPermissionIMP()
            }
        }

        toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        shared2 = getSharedPreferences("Test" , Context.MODE_PRIVATE)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            storagePermission2 = arrayOf(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }
        shared= PreferenceManager.getDefaultSharedPreferences(this)
        sqlHelper = SQLHelper(this)
        loadData(NEWEST_FIRST)

        addBtn.setOnClickListener {
            val intent = Intent(this,AddData::class.java)
            intent.putExtra("isEditMode",false)
            shared2?.edit()?.putBoolean("Flow",true)?.apply()
            startActivity(Intent(this,AddData::class.java))
        }
        screenshotperm()

        nav.setNavigationItemSelectedListener {
            if(it.itemId==R.id.nav_backup){
                    exportCSV()
            }else if(it.itemId==R.id.nav_Restore){
                    importCSV()
                loadData(OLDEST_FIRST)
            }else if (it.itemId==R.id.nav_Settings){
                shared2?.edit()?.putBoolean("Flow",true)?.apply()
                startActivity(Intent(this,com.ooolrs.lockward.Settings::class.java))
                drawer.close()
            }else if(it.itemId==R.id.nav_about){

                shared2?.edit()?.putBoolean("Flow",true)?.apply()
                startActivity(Intent(this,about::class.java))
                drawer.close()
            }else if(it.itemId==R.id.help){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/lakshrajj/Lockward"))
                startActivity(browserIntent)
            }

        true}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    searchRecords(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    searchRecords(query)
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(toggle.onOptionsItemSelected(item)){
            return true
        }else if(id==R.id.action_sort){
            sortDialog()

        }else if(id==R.id.action_deleteall){
            sqlHelper.deleteallrecords()
            onResume()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun screenshotperm() {
        val ss = shared?.getBoolean("screenshot",true)

        if (ss==false){
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
    private fun checkStroagePer():Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager()
        } else {
            return ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == (PackageManager.PERMISSION_GRANTED)
        }
    }
    public fun reqPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
            val uri = Uri.fromParts("package", this.packageName, null)
            intent.data = uri
            startActivity(intent)
        }
    }

    private fun loadData(orderby: String) {
        recentorder = orderby
        val adapterRecord = RVAdapter(this,sqlHelper.getAllRecords(orderby))
        listData.adapter = adapterRecord
    }
    private fun searchRecords(query:String) {
        val adapterRecord = RVAdapter(this,sqlHelper.searchData(query))
        listData.adapter = adapterRecord
    }
    public override fun onResume() {
        super.onResume()
        loadData(recentorder)
        screenshotperm()
    }

    private fun reqPermissionIMP(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE_IMPORT)
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1
        )
    }
    private fun reqPermissionEXP(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE_EXPORT)
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 1
        )
    }
    fun exportCSV() {
        val folder = File(Environment.getExternalStorageDirectory().toString()+"/BackUPFiles")

        var isFolderCreated = false
        if (!folder.exists()){ isFolderCreated = folder.mkdir() }

        val csvFilename = "BP"+".csv"

        val fileNameandPath = "$folder/$csvFilename"

        //Saving in csv File
        var recordList = ArrayList<Model>()
        recordList.clear()
        recordList = sqlHelper.getAllRecords(OLDEST_FIRST)

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
    private fun importCSV() {
        val fileNameandPath = Environment.getExternalStorageDirectory().toString()+"/BackUPFiles/BP.csv"

        val csvFile = File(fileNameandPath)
        if(csvFile.exists()){
            try {
                val csvReader = CSVReader(FileReader(csvFile.absolutePath))
                var nextLine : Array<String>
                while (csvReader.readNext ().also { nextLine = it } !=null){
                    val id = nextLine[0]
                    val passof = nextLine[1]
                    val username = nextLine[2]
                    val pass = nextLine[3]
                    val addedtime = nextLine[4]
                    val updatedtime = nextLine[5]

                    val timestamp = System.currentTimeMillis()
                    sqlHelper.insertData2(
                        ""+id,
                        ""+passof,
                        ""+username,
                        ""+pass,
                        ""+timestamp,
                        ""+timestamp)
                }
            }catch(e: Exception){
                Toast.makeText(applicationContext,e.message, Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(applicationContext,"Back up Not Found !!", Toast.LENGTH_LONG).show()
        }

    }

    private fun sortDialog() {
        val options= arrayOf("Title Ascending", "Title Descending", "Newest", "Oldest")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Sort By")
            .setItems(options){_,which ->
                if(which==0){
                    loadData(TITLE_ASC)
                }else if(which==1){
                    loadData(TITLE_DESC)
                }
                else if(which==2){
                    loadData(NEWEST_FIRST)
                }
                else if(which==3){
                    loadData(OLDEST_FIRST)

                }
            }.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            STORAGE_REQUEST_CODE_EXPORT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    exportCSV()
                } else {
                    Toast.makeText(applicationContext,"Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
            STORAGE_REQUEST_CODE_IMPORT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    importCSV()
                } else {
                    Toast.makeText(applicationContext,"Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
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

    override fun onRestart() {
        super.onRestart()
        val d =shared2?.getBoolean("Flow",false)
        Log.d(TAG, "onRestart: $d")
        if (d==false){
            startActivity(Intent(this,AuthActivity::class.java))
        }else{
            shared2?.edit()?.putBoolean("Flow",false)?.apply()
        }
    }
}