package com.ooolrs.lockward.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLHelper(context: Context?): SQLiteOpenHelper(
    context,
    Constants.DB_NAME,
    null,
    Constants.DB_VERSION

){
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Constants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME)
        onCreate(db)
    }

    fun insertData(passof:String?,username:String?,pass:String?,addedtime:String?,updatedtime:String?):Long{
        val db = writableDatabase
        val values = ContentValues()
        values.put(Constants.C_PASSOF,passof)
        values.put(Constants.C_USERNAME,username)
        values.put(Constants.C_PASS,pass)
        values.put(Constants.C_ADDED_TIMESTAMP,addedtime)
        values.put(Constants.C_UPDATED_TIMESTAMP,updatedtime)

        val id = db.insert(Constants.TABLE_NAME,null,values)
        db.close()
        return id

    }
    fun insertData2(id:String?,passof:String?,username:String?,pass:String?,addedtime:String?,updatedtime:String?){
        val db = writableDatabase
        val values = ContentValues()
        values.put(Constants.C_ID,id)
        values.put(Constants.C_PASSOF,passof)
        values.put(Constants.C_USERNAME,username)
        values.put(Constants.C_PASS,pass)
        values.put(Constants.C_ADDED_TIMESTAMP,addedtime)
        values.put(Constants.C_UPDATED_TIMESTAMP,updatedtime)

        db.insert(Constants.TABLE_NAME,null,values)
        db.close()

    }

    fun getAllRecords(orderBy:String):ArrayList<Model>{
        val recordList = ArrayList<Model>()
        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} ORDER BY $orderBy"
        val db = writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val modelRecord = Model(
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_USERNAME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PASSOF)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PASS)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIMESTAMP))

                    )
                    recordList.add(modelRecord)
                } while (cursor.moveToNext())
            }}
        db.close()

        return recordList
    }

    fun searchData(query:String):ArrayList<Model>{
        val recordList = ArrayList<Model>()
        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} WHERE ${Constants.C_PASSOF} LIKE '%$query%' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do {
                val modelRecord = Model(
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_USERNAME)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PASSOF)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PASS)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIMESTAMP)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIMESTAMP)),

                    )
                recordList.add(modelRecord)
            }while (cursor.moveToNext())
        }
        db.close()
        return recordList
    }

    fun updaterecord(id:String,username:String?,passof:String?,pass:String?,addedtime:String?,updatedTime:String?):Long{
        val db = writableDatabase
        val values = ContentValues()
        values.put(Constants.C_PASSOF,passof)
        values.put(Constants.C_USERNAME,username)
        values.put(Constants.C_PASS,pass)
        values.put(Constants.C_ADDED_TIMESTAMP,addedtime)
        values.put(Constants.C_UPDATED_TIMESTAMP,updatedTime)

        return db.update(Constants.TABLE_NAME,values,"${Constants.C_ID}=?", arrayOf(id)).toLong()

    }

    fun recordCount():Int{
        val countQuery = "SELECT * FROM ${Constants.TABLE_NAME}"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery,  null)
        val count = cursor.count
        db.close()
        return count
    }

    fun deleteRecord(id:String){
        val db = writableDatabase
        db.delete(
            Constants.TABLE_NAME,
            "${Constants.C_ID} = ?",
            arrayOf(id)
        )
        db.close()
    }

    fun deleteallrecords(){
        val db = writableDatabase
        val values = ContentValues()
        db.execSQL ( "DELETE FROM ${Constants.TABLE_NAME}")
        db.insert(Constants.TABLE_NAME,null,values)
        db.close()
    }
}