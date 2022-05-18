package com.ooolrs.lockward.db

import android.app.AlertDialog
import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.ooolrs.lockward.AddData
import com.ooolrs.lockward.HomeActivity
import com.ooolrs.lockward.PassView
import com.ooolrs.lockward.R
import java.security.AccessController.getContext


class RVAdapter(internal var context: Context,recordList: ArrayList<Model>?): RecyclerView.Adapter<RVAdapter.HolderRecord>() {

    private var recordList: ArrayList<Model>? = null
    lateinit var sqliteHelper: SQLHelper
    var shared: SharedPreferences? = null
    lateinit var aes: aes


    init {
        this.recordList = recordList
        sqliteHelper = SQLHelper(context)

        aes = aes()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRecord {
        return HolderRecord(
            LayoutInflater.from(context).inflate(R.layout.row_records, parent, false)
        )

    }

    override fun onBindViewHolder(holder: HolderRecord, position: Int) {
        var shared2: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val ss = shared2.getBoolean("listviewencrption", false)
        shared = context.getSharedPreferences("Test", Context.MODE_PRIVATE)
        val key = shared?.getString("key", "defaultName")


        val model = recordList!!.get(position)

        val id = model.id
        val username = model.username
        val passof = model.passof
        val pass = model.pass
        val addedTime = model.addedtime
        val updatedTime = model.updatedTime
        var cpy = ""

        if (ss == true) {
            holder.passTV.text = aes.decrypt(key.toString(), pass)
            cpy = aes.decrypt(key.toString(), pass).toString()
        } else {
            holder.passTV.text = pass
            cpy = pass
        }
        holder.passofTV.text = passof

        holder.usernameTV.text = username


        holder.itemView.setOnClickListener {
            val intent = Intent(context, PassView::class.java)
            shared?.edit()?.putBoolean("Flow",true)?.apply()
            intent.putExtra("RECORD_ID",id)
            context!!.startActivity(intent)
        }

        holder.CopyBtn.setOnClickListener {
            val cp = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", cpy)
            cp.setPrimaryClip(clipData)

            Toast.makeText(context, "Password copied", Toast.LENGTH_SHORT).show()


        }


        holder.moreBtn.setOnClickListener {
            showMoreOptions(
                position,
                id,
                username,
                passof,
                pass,
                addedTime,
                updatedTime
            )
        }
    }

    private fun showMoreOptions(
        position: Int,
        id: String,
        username: String,
        passof: String,
        pass: String,
        addedTime: String,
        updatedTime: String
    ) {
        val option = arrayOf("Edit", "Delete")
        val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
        dialog.setItems(option) { dialog, which ->
            if (which == 0) {
                val intent = Intent(context, AddData::class.java)
                intent.putExtra("ID", id)
                intent.putExtra("PASSOF", passof)
                intent.putExtra("PASS", pass)
                intent.putExtra("USERNAME", username)
                intent.putExtra("ADDEDTIME", addedTime)
                intent.putExtra("UPDATETIME", updatedTime)
                intent.putExtra("isEditMode", true)

                shared?.edit()?.putBoolean("Flow",true)?.apply()
                context.startActivity(intent)
            } else {
                sqliteHelper.deleteRecord(id)
                (context as HomeActivity).onResume();
            }
        }
        dialog.show()
    }

    override fun getItemCount(): Int {
        return recordList!!.size
    }

    inner class HolderRecord(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var usernameTV: TextView = itemView.findViewById(R.id.UsernameTV)
        var passofTV: TextView = itemView.findViewById(R.id.PassofTV)
        var passTV: TextView = itemView.findViewById(R.id.PasswordTV)
        var moreBtn: ImageButton = itemView.findViewById(R.id.moreBtn)
        var CopyBtn : ImageButton = itemView.findViewById(R.id.CopyBtn)
    }
}
