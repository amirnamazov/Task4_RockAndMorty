package com.example.rockandmorty.presentation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rockandmorty.R
import com.example.rockandmorty.databinding.ItemCharacterBinding
import com.example.rockandmorty.domain.model.character.Result

class CharacterAdapter(private val itemClick: ItemClick) :
    PagingDataAdapter<Result, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val resultItem = getItem(position)
        if (resultItem != null) (holder as CharacterViewHolder).bind(resultItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CharacterViewHolder.create(parent, itemClick)

    private class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
        private val itemClick: ItemClick
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) = with(binding) {
            image.load(result.image)
            name.text = result.name
            species.text = result.species
            when (result.status) {
                "Alive" -> icon.load(R.drawable.ic_alive)
                "Dead" -> icon.load(R.drawable.ic_dead)
                else -> icon.load(R.drawable.ic_unknown)
            }
            image.setOnClickListener {
                itemClick.onItemClick(it, result)
            }
        }

        companion object {
            fun create(parent: ViewGroup, itemClick: ItemClick): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCharacterBinding.inflate(inflater, parent, false)
                return CharacterViewHolder(binding, itemClick)
            }
        }
    }

    fun interface ItemClick {
        fun onItemClick(imageView: View, result: Result)
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