package com.example.dogceochallenge.presentation.breedimages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dogceochallenge.databinding.BreedImageBinding
import com.squareup.picasso.Picasso

class BreedImagesAdapter : ListAdapter<String, BreedImagesAdapter.ImageViewHolder>(
    ImagesDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class ImageViewHolder(private val binding: BreedImageBinding) :
        RecyclerView.ViewHolder((binding.root)) {

        private val breedImageIv = binding.breedImageIv

        fun bindData(item: String) {
            Picasso.get().load(item).into(breedImageIv)
        }

        companion object {
            fun from(parent: ViewGroup): ImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BreedImageBinding.inflate(layoutInflater, parent, false)
                return ImageViewHolder(binding)
            }
        }
    }
}

open class ImagesDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}




