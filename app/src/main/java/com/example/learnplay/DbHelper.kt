package com.example.learnplay

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import android.util.Log

class DbHelper(val context: Context,val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context,"app",factory,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, email TEXT, pass TEXT, log_st TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun deleteDatabase(context: Context): Boolean {
        val deleted = context.deleteDatabase("users")
        if (!deleted) {
            Log.e("DbHelper", "Failed to delete database")
        }
        return deleted
    }



    fun addUser(user: User){
        val values = ContentValues()
        values.put("login",user.login)
        values.put("email",user.email)
        values.put("pass",user.pass)
        values.put("log_st","True")

        val db =this.writableDatabase
        db.insert("users",null, values)

        db.close()
    }

    fun getUser(login: String, pass: String) : Boolean{
        val db = this.readableDatabase
        val selection = "login = ? AND pass = ?"
        val selectionArgs = arrayOf(login, pass)
        val cursor = db.query("users", null, selection, selectionArgs, null, null, null)
        val result = cursor.moveToFirst()
        cursor.close()
        db.close()
        return result
    }

    fun LogUser(login: String, log_st: String){
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put("log_st", log_st)
        }
        val selection = "login = ?"
        val selectionArgs = arrayOf(login)
        db.update("users", cv, selection, selectionArgs)
        db.close()

    }


    fun getLogUser() : User?{
        val db = this.readableDatabase
        val cursor = db.query("users", null, "log_st = ?", arrayOf("True"), null, null, null)
        val user: User? = if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndexOrThrow("login")
            val name: String = cursor.getString(nameIndex)
            val nameIndex1 = cursor.getColumnIndexOrThrow("email")
            val email: String = cursor.getString(nameIndex1)
            val nameIndex2 = cursor.getColumnIndexOrThrow("pass")
            val pass: String = cursor.getString(nameIndex2)
            User(name, email, pass, "True")
        } else {
            null
        }
        cursor.close()
        db.close()
        return user

    }



}