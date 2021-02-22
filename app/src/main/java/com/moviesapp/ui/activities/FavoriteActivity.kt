package com.moviesapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.moviesapp.ui.adapters.FavouriteMoviesListAdapter
import com.moviesapp.viewmodel.MoviesViewModel

class FavoriteActivity : AppCompatActivity() {

    private var favMoviesListAdapter: FavouriteMoviesListAdapter? = null
    private lateinit var moviesViewModel: MoviesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        favMoviesListAdapter = FavouriteMoviesListAdapter(this, arrayListOf())

        val moviesSearchRecyclerView: RecyclerView = findViewById(R.id.favMoviesRecyclerView)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesSearchRecyclerView.layoutManager = linearLayoutManager
        moviesSearchRecyclerView.adapter = favMoviesListAdapter
        moviesViewModel.allFavMovies.observe(this, Observer { favList ->
            favMoviesListAdapter?.addAll(favList)
        })
    }
}