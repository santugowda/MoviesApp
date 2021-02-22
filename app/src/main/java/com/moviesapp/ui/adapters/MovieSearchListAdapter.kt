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

class MovieSearchListAdapter(
    private val favMovieList: ArrayList<MovieDetails>,
    private val onMovieSelected: OnMovieSelected) :
    PagedListAdapter<Search, MovieSearchListAdapter.MoviesListViewHolder>(
        usersDiffCallback
    ) {
    lateinit var context: Context

    interface OnMovieSelected {
        fun onUserItemClick(movieSearch: Search)
    }

    fun addAll(newSearch: List<MovieDetails>) {
        favMovieList.addAll(newSearch)
        notifyItemRangeInserted(favMovieList.size, newSearch.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
        return MoviesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val moviesModel = getItem(position)
        moviesModel?.let { holder.bind(it) }
    }

    inner class MoviesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieSearch: Search) {
            with(itemView) {
                movieName.text = movieSearch.title
                movieYear.text = movieSearch.year
                movieSearch.poster.let {
                    Glide.with(context)
                        .load(it)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgPoster)
                }

//                favMovieList.getOrNull(0).let {
//                    if(it?.title == movieSearch.title) {
//                        favouriteIcon.visibility = View.VISIBLE
//                    } else {
//                        favouriteIcon.visibility = View.GONE
//                    }
//                }


                setOnClickListener {
                    onMovieSelected.onUserItemClick(movieSearch)
                }
            }
        }
    }

    companion object {
        val usersDiffCallback = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(
                oldItem: Search,
                newItem: Search
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Search,
                newItem: Search
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}