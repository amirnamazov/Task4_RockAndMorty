package com.example.rockandmorty.presentation.view.home

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.rockandmorty.R
import com.example.rockandmorty.databinding.FragmentHomeBinding
import com.example.rockandmorty.databinding.ItemSpinnerCharBinding
import com.example.rockandmorty.presentation.base.BaseFragment
import com.example.rockandmorty.presentation.utils.CustomAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private val characterAdapter: CharacterAdapter by lazy { CharacterAdapter() }

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
        viewModel.getResults(
            gender = viewModel.characterParams[0].run { params[selectedPos] },
            status = viewModel.characterParams[1].run { params[selectedPos] },
        ).flowWithLifecycle(lifecycle).collectLatest {
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
                override fun onItemSelected(
                    p: AdapterView<*>?, v: View?, position: Int, id: Long
                ) {
                    if (get(i).selectedPos != position) {
                        get(i).selectedPos = position
                        setupCharacterAdapter()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
}