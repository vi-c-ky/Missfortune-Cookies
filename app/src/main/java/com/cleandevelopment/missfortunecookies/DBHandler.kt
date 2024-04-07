package com.cleandevelopment.missfortunecookies

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.*

val DATABASENAME = "missfortunes_db.db"
val TABLENAME = "missfortunes_table"
val COL_TASK = "Fortune"
val COL_ID = "_ID"
val DB_PATH:String  = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
val DB_NAME:String  = "missfortunes_db.db";


class DBHandler(var context: Context) : SQLiteOpenHelper(
    context, com.cleandevelopment.missfortunecookies.DATABASENAME, null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE IF NOT EXISTS " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TASK + " VARCHAR(256) )"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insterData(data: String){
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(com.cleandevelopment.missfortunecookies.COL_TASK, data)
        val result = database.insert(com.cleandevelopment.missfortunecookies.TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun readData():String{
        val database = this.readableDatabase
        var item:String = ""
        var cursor: Cursor = database.rawQuery("SELECT $COL_TASK FROM $TABLENAME ORDER BY RANDOM() LIMIT "+"1", null);
        if (cursor.moveToFirst()) {
                 item = cursor.getString(cursor.getColumnIndex(com.cleandevelopment.missfortunecookies.COL_TASK))


    }
        
        return item.toString()
    }


    fun setDefaultDataBase(context:Context) {
        try {
            val myInput:InputStream  = context.getAssets().open(DB_NAME);
            // Path to the just created empty db
            val outFileName:String  = DB_PATH + DB_NAME;
            //Open the empty db as the output stream
            val myOutput: OutputStream = FileOutputStream(outFileName);
            //transfer bytes from the inputfile to the outputfile
            myInput.copyTo(myOutput)
            myInput.close()

            myOutput.flush()
            myOutput.close()

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();


        } catch (e: IOException) {
            e.printStackTrace();
        }
}
   fun checkDataBase(context:Context):Boolean {
       var checkDB:SQLiteDatabase?  = null;
           try {

               val myPath:String  = DB_PATH + DB_NAME;

               val file:File  = File(myPath);
               if (file.exists() && !file.isDirectory())
                   checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
           } catch ( e:SQLiteException) {
               // database does't exist yet.
           }

           if (checkDB != null) {
               checkDB.close();

           }

           return if(checkDB != null)  true else false;
       }
}