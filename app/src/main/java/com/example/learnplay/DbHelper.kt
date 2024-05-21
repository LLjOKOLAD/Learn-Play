package com.example.learnplay

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.learnplay.dataClasses.User

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


    fun addNewUser(name:String, email: String, pass: String){
        val values = ContentValues()
        values.put("login", name)
        values.put("email",email)
        values.put("pass",pass)
        values.put("name",name)
        values.put("log_st","True")
        values.put("multiplier",1f)
        values.put("experience",0)
        values.put("character", "")

        val db =this.writableDatabase
        db.insert("users",null, values)

        db.close()
    }



    fun addUser(email:String, pass: String){
        val db = this.writableDatabase

        val cursor = db.query("users", null, "email = ?", arrayOf(email), null, null, null)
        if (cursor.moveToFirst()) {
            val values = ContentValues()
            values.put("pass", pass)
            db.update("users", values, "email = ?", arrayOf(email))
        } else {
            val values = ContentValues()
            values.put("login", email)
            values.put("email", email)
            values.put("pass", pass)
            values.put("log_st", "True")
            values.put("name", "Не указан")
            values.put("multiplier", 1f)
            values.put("experience", 0)
            values.put("character", "")

            db.insert("users", null, values)
        }

        cursor.close()
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

        val whereClause = "email = ?" // условие для обновления пользователя по логину
        val whereArgs = arrayOf(user.email) // значение логина для условия

        db.update("users", values, whereClause, whereArgs)

        db.close()
    }


    fun getUser(email: String, pass: String) : Boolean{
        val db = this.readableDatabase
        val selection = "email = ? AND pass = ?"
        val selectionArgs = arrayOf(email, pass)
        val cursor = db.query("users", null, selection, selectionArgs, null, null, null)
        val result = cursor.moveToFirst()
        cursor.close()
        db.close()
        return result
    }

    fun LogUser(email: String, log_st: String){
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put("log_st", log_st)
        }
        val selection = "email = ?"
        val selectionArgs = arrayOf(email)
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
        Log.d("DbHelper",user.toString())
        cursor.close()
        db.close()
        return user

    }



}