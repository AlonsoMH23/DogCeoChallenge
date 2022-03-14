package com.example.dogceochallenge.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dogceochallenge.databinding.BreedItemBinding
import java.util.*

class BreedsAdapter(private var listener: BreedsItemListener) :
    ListAdapter<String, BreedsAdapter.TextViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bindData(getItem(position), listener)
    }

    class TextViewHolder(binding: BreedItemBinding) : RecyclerView.ViewHolder((binding.root)) {

        private val breedNameTv = binding.breedNameTv

        fun bindData(item: String, listener: BreedsItemListener) {
            breedNameTv.text = item.toUpperCase(Locale.getDefault())

            breedNameTv.setOnClickListener {
                listener.selectedBreed(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BreedItemBinding.inflate(layoutInflater, parent, false)
                return TextViewHolder(binding)
            }
        }
    }

}

open class DiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}


