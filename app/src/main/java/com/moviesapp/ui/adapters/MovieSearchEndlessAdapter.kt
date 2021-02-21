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
import com.moviesapp.model.MovieSearch
import com.moviesapp.model.Search
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieSearchEndlessAdapter(private val searchResultList: ArrayList<Search>,
                                private val onMovieSelected: OnMovieSelected)
    : RecyclerView.Adapter<MovieSearchEndlessAdapter.MoviesListViewHolder>(){

    private val ITEM = 0
    private val LOADING = 1
    private var isLoadingAdded = false

    interface OnMovieSelected {
        fun onUserItemClick(movieSearch: Search)
    }

    fun addAll(newSearch: List<Search>) {
        searchResultList.addAll(newSearch);
        notifyItemRangeInserted(searchResultList.size, newSearch.size);
    }

    fun reset() {
        searchResultList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var viewHolder: MoviesListViewHolder? = null
        when (viewType) {
            ITEM -> {
                viewHolder = getViewHolder(parent, layoutInflater)
            }

            LOADING -> {
                val v2: View = layoutInflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = MoviesListViewHolder(v2)
            }
        }

        return viewHolder!!
    }

    private fun getViewHolder(parent: ViewGroup, inflater: LayoutInflater): MoviesListViewHolder {
        val viewHolder: MoviesListViewHolder
        val view = inflater.inflate(R.layout.movie_list_item, parent, false)
        viewHolder = MoviesListViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                holder.bind(searchResultList[position])
            }
        }
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

                setOnClickListener {
                    onMovieSelected.onUserItemClick(movieSearch)
                }
            }
        }
    }

}