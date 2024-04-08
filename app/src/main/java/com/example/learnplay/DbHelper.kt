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
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, email TEXT, pass TEXT, log_st TEXT, name TEXT, multiplier REAL, experience INT, character TEXT )"
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
        values.put("name",user.name)
        values.put("log_st",user.log_st)
        values.put("multiplier",user.multiplier)
        values.put("experience",user.experience)
        values.put("character",user.character)

        val db =this.writableDatabase
        db.insert("users",null, values)

        db.close()
    }

    fun updateUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("email", user.email)
        values.put("pass", user.pass)
        values.put("name", user.name)
        values.put("log_st", user.log_st)
        values.put("multiplier", user.multiplier)
        values.put("experience", user.experience)
        values.put("character", user.character)

        val whereClause = "login = ?" // условие для обновления пользователя по логину
        val whereArgs = arrayOf(user.login) // значение логина для условия

        db.update("users", values, whereClause, whereArgs)

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
            val login: String = cursor.getString(nameIndex)
            val nameIndex1 = cursor.getColumnIndexOrThrow("email")
            val email: String = cursor.getString(nameIndex1)
            val nameIndex2 = cursor.getColumnIndexOrThrow("pass")
            val pass: String = cursor.getString(nameIndex2)
            val nameIndex3 = cursor.getColumnIndexOrThrow("name")
            val name: String = cursor.getString(nameIndex3)
            val nameIndex4 = cursor.getColumnIndexOrThrow("multiplier")
            val multiplier: Float = cursor.getFloat(nameIndex4)
            val nameIndex5 = cursor.getColumnIndexOrThrow("experience")
            val experience: Int = cursor.getInt(nameIndex5)
            val nameIndex6 = cursor.getColumnIndexOrThrow("character")
            val character: String = cursor.getString(nameIndex6)
            User(login, email, pass, name, "True", multiplier, experience, character)
        } else {
            null
        }
        cursor.close()
        db.close()
        return user

    }



}