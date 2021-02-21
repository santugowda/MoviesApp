package com.moviesapp.ui.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
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
import com.moviesapp.model.Search
import com.moviesapp.network.base.NetworkResponse
import com.moviesapp.ui.adapters.EndlessRecyclerViewScrollListener
import com.moviesapp.ui.adapters.MovieSearchEndlessAdapter
import com.moviesapp.utils.MoviesSharedPref
import com.moviesapp.viewmodel.MoviesViewModel
import com.moviesapp.viewmodel.MovieSearchListViewModel
import kotlinx.android.synthetic.main.fragment_movies_list.*


class MoviesSearchListFragment : Fragment(), MovieSearchEndlessAdapter.OnMovieSelected {

    private lateinit var movieSearchListViewModel: MovieSearchListViewModel
    private lateinit var moviesViewModel: MoviesViewModel
    private var searchInputView: EditText? = null
    private var searchContainerView: View? = null
    private var searchStartView: View? = null
    private var appBarView: AppBarLayout? = null
    private var movieSearchEndlessAdapter: MovieSearchEndlessAdapter? = null
    private var currentPage = 1
    private lateinit var moviesSharedPref: MoviesSharedPref


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moviesSearchRecyclerView: RecyclerView = view.findViewById(R.id.movieSearchRecyclerView)

        searchInputView = view.findViewById(R.id.searchInputView)
        searchContainerView = view.findViewById(R.id.searchContainerView)
        searchStartView = view.findViewById(R.id.searchStartView)
        appBarView = view.findViewById(R.id.appBarView)

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        movieSearchEndlessAdapter = MovieSearchEndlessAdapter(arrayListOf(), this)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        moviesSearchRecyclerView.layoutManager = layoutManager
        moviesSearchRecyclerView.adapter = movieSearchEndlessAdapter
        moviesSharedPref = MoviesSharedPref.getInstance(requireActivity())

        initSearchField()
        loadInitialItems(currentPage)

        moviesSearchRecyclerView.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadInitialItems(page)
            }
        })
    }

    private fun initSearchField() {
        searchStartView?.setOnClickListener {
            hideIme()
            moviesSharedPref.setSearchedMovies(searchInputView?.text.toString())
            searchForResults(searchInputView?.text.toString(), currentPage)
        }
        appBarView?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            searchContainerView?.translationY = verticalOffset.toFloat()
        })
    }

    private fun loadInitialItems(page: Int) {
        moviesViewModel.searchMovie(moviesSharedPref.searchedMovie, page)
            .observe(viewLifecycleOwner,
                Observer {
                    when (it.status) {
                        NetworkResponse.NetworkStatus.SUCCESS -> {
                            mainProgress.visibility = View.GONE
                            if (it.data != null) {
                                movieSearchEndlessAdapter?.addAll(it.data.search)
                            }
                        }

                        NetworkResponse.NetworkStatus.IN_PROGRESS -> {
                            mainProgress.visibility = View.VISIBLE
                        }

                        NetworkResponse.NetworkStatus.ERROR -> {

                        }
                    }
                })
    }

    private fun searchForResults(movieSearch: String, page: Int) {
        moviesViewModel.searchMovie(movieSearch, page)
            .observe(viewLifecycleOwner,
                Observer {
                    when (it.status) {
                        NetworkResponse.NetworkStatus.SUCCESS -> {
                            mainProgress.visibility = View.GONE
                            if (it.data != null) {
                                movieSearchEndlessAdapter?.reset()
                                movieSearchEndlessAdapter?.addAll(it.data.search)
                            }
                        }

                        NetworkResponse.NetworkStatus.IN_PROGRESS -> {
                            mainProgress.visibility = View.VISIBLE
                        }

                        NetworkResponse.NetworkStatus.ERROR -> {

                        }
                    }
                })
    }

    private fun hideIme() {
        val inputMethodManager =
            activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchInputView!!.windowToken, 0)
    }

    override fun onUserItemClick(movieSearch: Search) {
        val directions =
            MoviesSearchListFragmentDirections.actionMovieSearchListFragmentToMoviesDetailedFragment(
                movieSearch.title
            )
        findNavController().navigate(directions)
    }
}