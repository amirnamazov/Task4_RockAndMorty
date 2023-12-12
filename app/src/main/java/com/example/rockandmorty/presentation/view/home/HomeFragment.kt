package com.example.rockandmorty.presentation.view.home

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    private val characterAdapter by lazy { CharacterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvCharacters.adapter = characterAdapter
        setupCharacterAdapter()
        setupRvFilterAdapter()
    }

    private fun setupCharacterAdapter(gender: String = "", status: String = "") =
        lifecycleScope.launch {
            viewModel.getResults(gender, status).flowWithLifecycle(lifecycle).collectLatest {
                characterAdapter.submitData(it)
            }
        }

    private fun setupRvFilterAdapter() {
        val list = charParams()

        binding.rvFilter.adapter =
            CustomAdapter(ItemSpinnerCharBinding::inflate, list.size) { b, i ->
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.spinner_char,
                    list[i].spinnerList
                )

                arrayAdapter.setDropDownViewResource(R.layout.spinner_char_dropdown)

                b.spinnerCharacter.apply {
                    adapter = arrayAdapter

                    setSelection(list[i].selectedPos)

                    setOnTouchListener { v, event ->
                        if (event.action == MotionEvent.ACTION_UP) v.performClick()
                        return@setOnTouchListener true
                    }

                    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p: AdapterView<*>?, v: View?,
                            position: Int, id: Long
                        ) {
                            if (list[i].selectedPos != position) {
                                list[i].selectedPos = position
                                setupCharacterAdapter(
                                    gender = list[0].params[list[0].selectedPos],
                                    status = list[1].params[list[1].selectedPos],
                                )
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                }
            }
    }

    private fun charParams(): List<CharacterParams> = resources.run {
        listOf(
            CharacterParams(getStringArray(R.array.gender).toList()),
            CharacterParams(getStringArray(R.array.status).toList()),
        )
    }
}