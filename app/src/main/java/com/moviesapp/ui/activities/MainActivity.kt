package com.moviesapp.ui.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.appBarView))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent =  Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }
}