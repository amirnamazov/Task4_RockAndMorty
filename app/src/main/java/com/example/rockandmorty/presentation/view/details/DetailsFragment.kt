package com.example.rockandmorty.presentation.view.details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.rockandmorty.databinding.FragmentDetailsBinding
import com.example.rockandmorty.presentation.base.BaseFragment

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvName.text = args.name
    }
}