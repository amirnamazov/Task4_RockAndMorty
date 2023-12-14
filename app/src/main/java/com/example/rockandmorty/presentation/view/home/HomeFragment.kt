package com.example.rockandmorty.presentation.view.home

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.rockandmorty.R
import com.example.rockandmorty.databinding.FragmentHomeBinding
import com.example.rockandmorty.databinding.ItemSpinnerCharBinding
import com.example.rockandmorty.domain.model.character.Result
import com.example.rockandmorty.presentation.base.BaseFragment
import com.example.rockandmorty.presentation.utils.CustomAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    CharacterAdapter.ItemClick {

    private val viewModel: HomeViewModel by viewModels()
    private val characterAdapter: CharacterAdapter by lazy { CharacterAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvCharacters.adapter = characterAdapter

        initCharacterParams()
        setupCharacterAdapter()
        setupRvFilterAdapter()
        setupSearchText()
    }

    private fun setupSearchText() {
        binding.etSearch.doAfterTextChanged { viewModel.searchText = it.toString() }

        viewModel.liveDataSearch.observe(viewLifecycleOwner) { setupCharacterAdapter() }
    }

    private fun initCharacterParams() {
        viewModel.characterParams = resources.run {
            listOf(
                CharacterParams(getStringArray(R.array.gender).toList()),
                CharacterParams(getStringArray(R.array.status).toList()),
            )
        }
    }

    private fun setupCharacterAdapter() = lifecycleScope.launch {
        viewModel.getResults().collectLatest {
            characterAdapter.submitData(it)
        }
    }

    private fun setupRvFilterAdapter() = with(viewModel.characterParams) {
        binding.rvFilter.adapter = CustomAdapter(ItemSpinnerCharBinding::inflate, size) { b, i ->
            val arrayAdapter = ArrayAdapter(
                requireContext(), R.layout.spinner_char, get(i).spinnerList
            )

            arrayAdapter.setDropDownViewResource(R.layout.spinner_char_dropdown)

            b.spinnerCharacter.setupSpinner(arrayAdapter, i)
        }
    }

    private fun AppCompatSpinner.setupSpinner(arrayAdapter: ArrayAdapter<String>, i: Int) =
        with(viewModel.characterParams) {
            adapter = arrayAdapter

            setSelection(get(i).selectedPos)

            setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) v.performClick()
                return@setOnTouchListener true
            }

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p: AdapterView<*>?, v: View?, position: Int, id: Long) {
                    if (get(i).selectedPos != position) {
                        get(i).selectedPos = position
                        setupCharacterAdapter()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

    override fun onItemClick(imageView: View, result: Result) {
        val action = HomeFragmentDirections.fromHomeFragToDetailsFrag("mane")
        val extras = FragmentNavigatorExtras(imageView to "image_char")

        findNavController().navigate(action, extras)
    }
}