package com.moviesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.R
import com.moviesapp.network.base.NetworkResponse
import com.moviesapp.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*


class MoviesDetailedFragment : Fragment() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        arguments?.let {
            val movieName: String = MoviesDetailedFragmentArgs.fromBundle(it).movieName
            movieDetailsViewModel.getMovieDetails(movieName)
                .observe(viewLifecycleOwner, Observer { movieDetails ->
                    detailProgress.visibility = View.VISIBLE
                    when (movieDetails.status) {
                        NetworkResponse.NetworkStatus.IN_PROGRESS -> {
                            detailProgress.visibility = View.VISIBLE
                        }

                        NetworkResponse.NetworkStatus.SUCCESS -> {
                            detailProgress.visibility = View.GONE
                            if (movieDetails.data != null) {
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
                            directorName.visibility = View.GONE
                            moviePlot.visibility = View.GONE
                            releasedYear.visibility = View.GONE
                            posterImage.visibility = View.GONE
                            movieTitle.text = getString(R.string.error_message).plus(movieDetails.message)
                        }
                    }
                })
        }
    }
}