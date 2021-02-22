package com.moviesapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.google.android.material.appbar.AppBarLayout
import com.moviesapp.model.MovieDetails
import com.moviesapp.model.MovieSearch
import com.moviesapp.model.Search
import com.moviesapp.ui.adapters.MovieSearchListAdapter
import com.moviesapp.utils.MoviesSharedPref
import com.moviesapp.viewmodel.MovieSearchListViewModel
import com.moviesapp.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies_list.*


class MoviesListFragment : Fragment(), MovieSearchListAdapter.OnMovieSelected {

    private lateinit var movieSearchListViewModel: MovieSearchListViewModel
    private lateinit var moviesViewModel: MoviesViewModel
    private var searchInputView: EditText? = null
    private var searchContainerView: View? = null
    private var searchStartView: View? = null
    private var appBarView: AppBarLayout? = null
    private var moviesSearchListAdapter: MovieSearchListAdapter? = null
    private lateinit var moviesSharedPref: MoviesSharedPref
    private var favMovieList : ArrayList<MovieDetails> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieSearchListViewModel = ViewModelProvider(this).get(MovieSearchListViewModel::class.java)
        searchInputView = view.findViewById(R.id.searchInputView)
        searchContainerView = view.findViewById(R.id.searchContainerView)
        searchStartView = view.findViewById(R.id.searchStartView)
        appBarView = view.findViewById(R.id.appBarView)
        moviesSharedPref = MoviesSharedPref.getInstance(requireActivity())
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
//        moviesViewModel.allFavMovies.observe(requireActivity(), Observer { favList ->
//            favMovieList = favList as ArrayList<MovieDetails>
//        })

        favMovieList = moviesViewModel.allFavMovieListOnDemand as ArrayList<MovieDetails>
        moviesSearchListAdapter = MovieSearchListAdapter(favMovieList,this )

        val moviesSearchRecyclerView: RecyclerView = view.findViewById(R.id.movieSearchRecyclerView)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        moviesSearchRecyclerView.layoutManager = linearLayoutManager
        moviesSearchRecyclerView.adapter = moviesSearchListAdapter
        initSearchField()

        searchForResults(moviesSharedPref.searchedMovie)
    }

    private fun searchForResults(queryFilter: String) {
        movieSearchListViewModel.movieQuery.value = queryFilter
        movieSearchListViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            mainProgress.visibility = View.GONE
            moviesSearchListAdapter?.submitList(it)
        })
    }

    private fun initSearchField() {
        searchStartView?.setOnClickListener {
            hideIme()
            moviesSharedPref.setSearchedMovies(searchInputView?.text.toString())
            moviesSharedPref.newSearchQuery(true)
            searchForResults(searchInputView?.text.toString())
        }
        appBarView?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            searchContainerView?.translationY = verticalOffset.toFloat()
        })
    }

    private fun hideIme() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchInputView!!.windowToken, 0)
    }

    override fun onUserItemClick(movieSearch: Search) {
        val directions =
            MoviesListFragmentDirections.actionMovieSearchListFragmentToMoviesDetailedFragment(
                movieSearch.title
            )
        findNavController().navigate(directions)
    }

}