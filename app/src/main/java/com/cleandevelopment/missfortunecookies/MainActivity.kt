package com.cleandevelopment.missfortunecookies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = DBHandler(this)
        db.onCreate(db.writableDatabase)
        db.setDefaultDataBase(this)
        val missfortuneText: TextView = findViewById(R.id.fortune)
        val missfortuneBTN: Button = findViewById(R.id.fortuneButton)

        missfortuneBTN.setOnClickListener(){
            var item:CharSequence = db.readData()
            missfortuneText.text = item.toString()
        }


    }
}
