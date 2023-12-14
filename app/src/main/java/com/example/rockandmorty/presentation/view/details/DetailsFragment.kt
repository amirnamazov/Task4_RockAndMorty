package com.example.rockandmorty.presentation.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rockandmorty.databinding.FragmentDetailsBinding
import com.example.rockandmorty.presentation.base.BaseFragment

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val args: DetailsFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupActionBar()
        binding.apply {
            tvName.text = args.result.name
            image.load(args.result.image)
            tvGender.text = "Gender: ${args.result.gender}"
            tvStatus.text = "Status: ${args.result.status}"
            tvOrigin.text = "Origin: ${args.result.originName}"
            tvType.text = "Type: ${args.result.type}"
            tvSpecies.text = "Species: ${args.result.species}"
        }
    }

    private fun setupActionBar() = with(requireActivity() as AppCompatActivity) {
        setSupportActionBar(binding.toolBar)
        title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}