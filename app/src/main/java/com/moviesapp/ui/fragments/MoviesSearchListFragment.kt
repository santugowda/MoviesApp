package com.moviesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.moviesapp.model.Search
import com.moviesapp.ui.adapters.MovieSearchListAdapter
import com.moviesapp.viewmodel.MovieSearchListViewModel
import kotlinx.android.synthetic.main.fragment_movies_list.*


class MoviesSearchListFragment: Fragment(), MovieSearchListAdapter.OnMovieSelected {

    private lateinit var movieSearchListViewModel: MovieSearchListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieSearchListViewModel = ViewModelProvider(this).get(MovieSearchListViewModel::class.java)

        val moviesSearchListAdapter = MovieSearchListAdapter(this)
        val moviesSearchRecyclerView : RecyclerView = view.findViewById(R.id.movieSearchRecyclerView)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        moviesSearchRecyclerView.layoutManager = layoutManager
        moviesSearchRecyclerView.adapter = moviesSearchListAdapter
        movieSearchListViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            mainProgress.visibility = View.GONE
            moviesSearchListAdapter.submitList(it)
        })

    }

    override fun onUserItemClick(movieSearch: Search) {
        val directions = MoviesSearchListFragmentDirections.actionMovieSearchListFragmentToMoviesDetailedFragment(movieSearch.title)
        findNavController().navigate(directions)
    }

}