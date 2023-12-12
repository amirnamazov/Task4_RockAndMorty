package com.example.rockandmorty.presentation.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rockandmorty.data.data_source.remote.dto.character.ResultDTO
import com.example.rockandmorty.databinding.ItemCharacterBinding

class CharacterAdapter : PagingDataAdapter<ResultDTO, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val resultItem = getItem(position)
        if (resultItem != null) (holder as CharacterViewHolder).bind(resultItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CharacterViewHolder.create(parent)

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceType")
        fun bind(resultDTO: ResultDTO) {
            binding.image.load(resultDTO.image)
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
        private val COMPARATOR = object : DiffUtil.ItemCallback<ResultDTO>() {
            override fun areItemsTheSame(oldItem: ResultDTO, newItem: ResultDTO): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ResultDTO, newItem: ResultDTO): Boolean =
                oldItem == newItem

        }
    }
}