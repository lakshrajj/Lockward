package com.ooolrs.lockward.db

object Constants {
    const val DB_NAME = "OUR_DB"
    const val DB_VERSION = 1
    const val TABLE_NAME = "Details"

    const val C_ID = "ID"
    const val C_PASSOF = "PASSOF"
    const val C_PASS = "PASS"
    const val C_USERNAME = "USERNAME"

    const val C_ADDED_TIMESTAMP = "ADDED_TIME"
    const val C_UPDATED_TIMESTAMP = "UPDATED_TIME"

    const val CREATE_TABLE = (
            "CREATE TABLE $TABLE_NAME(" +
                    " $C_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " $C_PASSOF TEXT," +
                    " $C_PASS TEXT," +
                    " $C_USERNAME TEXT," +
                    " $C_ADDED_TIMESTAMP TEXT," +
                    " $C_UPDATED_TIMESTAMP TEXT);")

}