package com.moviesapp.ui.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.R
import com.moviesapp.db.MovieDetailsDao
import com.moviesapp.model.MovieDetails
import com.moviesapp.network.base.NetworkResponse
import com.moviesapp.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesDetailedFragment : Fragment(), View.OnClickListener {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieDetailsToStore: MovieDetails
    private lateinit var favButton: Button
    lateinit var listValue: List<MovieDetails>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favButton = view.findViewById(R.id.favButton)
        favButton.setOnClickListener(this)

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        arguments?.let {
            val movieName: String = MoviesDetailedFragmentArgs.fromBundle(it).movieName
            moviesViewModel.getMovieDetails(movieName)
                .observe(viewLifecycleOwner, Observer { movieDetails ->
                    detailProgress.visibility = View.VISIBLE
                    when (movieDetails.status) {
                        NetworkResponse.NetworkStatus.IN_PROGRESS -> {
                            detailProgress.visibility = View.VISIBLE
                        }
                        NetworkResponse.NetworkStatus.SUCCESS -> {
                            detailProgress.visibility = View.GONE
                            if (movieDetails.data != null) {
                                movieDetailsToStore = movieDetails.data
                                updatedFavButton(movieDetails.data)

                                movieTitle.text = movieDetails.data.title
                                directorName.text =
                                    getString(R.string.director).plus(movieDetails.data.director)
                                releasedYear.text =
                                    getString(R.string.year).plus(movieDetails.data.year)
                                moviePlot.text = movieDetails.data.plot

                                movieDetails.data.poster.let {
                                    activity?.applicationContext?.let { context ->
                                        Glide.with(context)
                                            .load(it)
                                            .apply(RequestOptions.circleCropTransform())
                                            .into(posterImage)
                                    }
                                }
                            }
                        }

                        NetworkResponse.NetworkStatus.ERROR -> {
                            detailProgress.visibility = View.GONE
                            directorName.visibility = View.GONE
                            moviePlot.visibility = View.GONE
                            releasedYear.visibility = View.GONE
                            posterImage.visibility = View.GONE
                            movieTitle.text =
                                getString(R.string.error_message).plus(movieDetails.message)
                        }
                    }
                })
        }
    }

    private fun updatedFavButton(movieDetails: MovieDetails) {
        val listValue = moviesViewModel.allFavMovieListOnDemand.filter {
            it.title == movieDetails.title
        }

        listValue.getOrNull(0).let {
            if (it?.title == movieDetails.title) {
                favButton.text = requireActivity().getString(R.string.already_your_favorites)
            } else {
                favButton.text = requireActivity().getString(R.string.add_to_favorites)

            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.favButton) {
            listValue = moviesViewModel.allFavMovieListOnDemand.filter {
                it.title == movieDetailsToStore.title
            }
            listValue.getOrNull(0).let {
                if (it?.title != movieDetailsToStore.title) {
                    insertAsyncTask(moviesViewModel).execute(movieDetailsToStore)
                } else {
                    deleteAsyncTask(moviesViewModel).execute(movieDetailsToStore)
                }
            }
        }
    }

    inner class insertAsyncTask internal constructor(moviesViewModel: MoviesViewModel) :
        AsyncTask<MovieDetails?, Void?, Void?>() {
        private val mAsyncTaskDao: MoviesViewModel = moviesViewModel

        override fun doInBackground(vararg params: MovieDetails?): Void? {
            params[0]?.let { mAsyncTaskDao.insertMovieDetailsToFav(it) }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            favButton.text = requireActivity().getString(R.string.added_to_favourites)
            Toast.makeText(requireActivity(), "Added to Favorites", Toast.LENGTH_SHORT).show()
        }
    }

    inner class deleteAsyncTask internal constructor(moviesViewModel: MoviesViewModel) :
        AsyncTask<MovieDetails?, Void?, Void?>() {
        private val mAsyncTaskDao: MoviesViewModel = moviesViewModel

        override fun doInBackground(vararg params: MovieDetails?): Void? {
            params[0]?.let { mAsyncTaskDao.deleteMovieDetailsFromFav(it) }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            favButton.text = requireActivity().getString(R.string.add_to_favorites)
            Toast.makeText(requireActivity(), "Removed from Favorites", Toast.LENGTH_SHORT).show()
        }
    }
}
