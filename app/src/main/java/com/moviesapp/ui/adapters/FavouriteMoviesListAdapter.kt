package com.moviesapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.R
import com.moviesapp.model.MovieDetails
import com.moviesapp.model.MovieSearch
import com.moviesapp.model.Search
import kotlinx.android.synthetic.main.movie_list_item.view.*

class FavouriteMoviesListAdapter(
    private val context: Context,
    private val favMovieList: ArrayList<MovieDetails>) : RecyclerView.Adapter<FavouriteMoviesListAdapter.FavouriteMoviesListViewHolder>() {

    fun addAll(newSearch: List<MovieDetails>) {
        favMovieList.addAll(newSearch)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteMoviesListViewHolder {
        return FavouriteMoviesListViewHolder(
            (LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false))
        )
    }

    override fun onBindViewHolder(holder: FavouriteMoviesListViewHolder, position: Int) {
        holder.bind(favMovieList[position])
    }

    override fun getItemCount(): Int {
        return favMovieList.size
    }

    inner class FavouriteMoviesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieDetails: MovieDetails) {
            with(itemView) {
                movieName.text = movieDetails.title
                movieYear.text = movieDetails.year
                movieDetails.poster.let {
                    Glide.with(context)
                        .load(it)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgPoster)
                }
            }
        }
    }

}