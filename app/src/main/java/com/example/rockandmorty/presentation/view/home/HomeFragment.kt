package com.example.rockandmorty.presentation.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.rockandmorty.databinding.FragmentHomeBinding
import com.example.rockandmorty.presentation.base.BaseFragment
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getCharacters()
        viewModel.resCharacters.observe(viewLifecycleOwner) {
            if (it is HomeUIState.Success) {
                println("3453453433453     ${it.data.results?.get(0)}")
            }
        }
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

    private fun ShimmerFrameLayout.stop() {
        stopShimmer()
        hideShimmer()
    }

    private fun ShimmerFrameLayout.start() {
        startShimmer()
        showShimmer(true)
    }
}