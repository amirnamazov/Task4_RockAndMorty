package com.example.rockandmorty.presentation.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.rockandmorty.R
import com.example.rockandmorty.databinding.FragmentHomeBinding
import com.example.rockandmorty.presentation.base.BaseFragment
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    private val characterAdapter by lazy { CharacterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        setupRvFilterAdapter()

        binding.rvCharacters.adapter = characterAdapter

        lifecycleScope.launch {
            viewModel.results.flowWithLifecycle(lifecycle).collectLatest {
                characterAdapter.submitData(it)
            }
        }

//        viewModel.getCharacters(1)
//        viewModel.resCharacters.observe(viewLifecycleOwner) {
//            if (it is HomeUIState.Success) {
//                println("3453453433453     ${it.data.results?.get(0)}")
//            }
//        }
    }

//    private fun LiveData<HomeUIState>
//            .observe(sfl: ShimmerFrameLayout, setAdapter: (List<Article>?, Int) -> Unit) =
//        observe(viewLifecycleOwner) { state ->
//            when(state) {
//                HomeUIState.Loading -> sfl.start()
//                else -> sfl.stop()
//            }
//            when (state) {
//                is HomeUIState.Loading -> setAdapter(null, 10)
//                is HomeUIState.Success -> setAdapter(state.data, state.data.size)
//                is HomeUIState.Error, is HomeUIState.ConnectionError -> {
//                    (requireActivity() as MainActivity).showDialog(state.message!!)
//                    setAdapter(null, 0)
//                }
//                is HomeUIState.Empty -> setAdapter(null, 0)
//            }
//        }

//    private fun RecyclerView.setAdapter(
//        inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
//        list: List<Article>?, count: Int
//    ) {
//        adapter = CustomAdapter(inflate, list?.size ?: count) { b, i ->
//            if (!list.isNullOrEmpty()) {
//                if (b is ItemHeadlineBinding) b.article = list[i]
//                if (b is ItemNewsBinding) b.article = list[i]
//                b.onItemClickListener(list[i])
//            }
//        }
//    }
//
//    private fun ViewBinding.onItemClickListener(article: Article) = root.setOnClickListener {
//        startActivity(
//            Intent(requireContext(), DetailsActivity :: class.java).apply {
//                putExtra("ARTICLE_MODEL", ArticleModel(article))
//            }
//        )
//    }

//    private fun setupRvFilterAdapter() {
//        val list = charParams()
//
//        binding.rvFilter.adapter =
//            CustomAdapter(ItemSpinnerCharBinding::inflate, list.size) { b, i ->
//                val arrayAdapter = ArrayAdapter(
//                    requireContext(),
//                    R.layout.spinner_char,
//                    list[i].spinnerList
//                )
//
//                arrayAdapter.setDropDownViewResource(R.layout.spinner_char_dropdown)
//
//                b.spinnerCharacter.apply {
//                    adapter = arrayAdapter
//
//                    setSelection(list[i].selectedPos)
//
//                    setOnTouchListener { v, event ->
//                        if (event.action == MotionEvent.ACTION_UP) v.performClick()
//                        return@setOnTouchListener true
//                    }
//
//                    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                        override fun onItemSelected(
//                            p: AdapterView<*>?, v: View?,
//                            position: Int, id: Long
//                        ) {
//                            if (list[i].selectedPos != position) {
//                                list[i].selectedPos = position
//                                viewModel.getCharacters(
//                                    1,
//                                    gender = list[0].params[list[0].selectedPos],
//                                    status = list[1].params[list[1].selectedPos],
//                                )
//                            }
//                        }
//
//                        override fun onNothingSelected(parent: AdapterView<*>?) {}
//                    }
//                }
//            }
//    }

    private fun charParams(): List<CharacterParams> = resources.run {
        listOf(
            CharacterParams(getStringArray(R.array.gender).toList()),
            CharacterParams(getStringArray(R.array.status).toList()),
        )
    }

    private fun ShimmerFrameLayout.stop() {
        stopShimmer()
        hideShimmer()
    }

    private fun ShimmerFrameLayout.start() {
        startShimmer()
        showShimmer(true)
    }
}