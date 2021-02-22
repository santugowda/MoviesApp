package com.moviesapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.moviesapp.R
import com.moviesapp.ui.activities.FavoriteActivity

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