package com.example.mvc_yongho.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvc_yongho.databinding.ItemMovieBinding
import com.example.mvc_yongho.model.data.MovieInfo

class MovieAdapter : ListAdapter<MovieInfo, MovieAdapter.MovieViewHolder>(diffUtil) {

    var onKmdbClick: ((MovieInfo) -> Unit)? = null

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieInfo, onKmdbClick: (MovieInfo) -> Unit) {
            binding.movice = movie
            binding.goKmdbDetailButton.setOnClickListener {
                onKmdbClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position)) { movie ->
            onKmdbClick?.invoke(movie)
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MovieInfo>() {
            override fun areItemsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean =
                oldItem.docId == newItem.docId

            override fun areContentsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean =
                oldItem == newItem
        }
    }
}
