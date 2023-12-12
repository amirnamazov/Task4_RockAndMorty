package com.example.rockandmorty.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rockandmorty.databinding.ItemCharacterBinding
import com.example.rockandmorty.domain.model.character.Result

class CharacterAdapter : PagingDataAdapter<Result, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val resultItem = getItem(position)
        if (resultItem != null) (holder as CharacterViewHolder).bind(resultItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CharacterViewHolder.create(parent)

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultDTO: Result) = with(binding) {
            image.load(resultDTO.image)
            name.text = resultDTO.name
            species.text = resultDTO.species
        }

        companion object {
            fun create(parent: ViewGroup): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCharacterBinding.inflate(inflater, parent, false)
                return CharacterViewHolder(binding)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem == newItem

        }
    }
}