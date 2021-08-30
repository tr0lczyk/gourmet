package com.example.gourmetapp.ui.news

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gourmetapp.R
import com.example.gourmetapp.data.News
import com.example.gourmetapp.databinding.FragmentNewsListBinding
import com.example.gourmetapp.ui.news.NewsDetailViewModel.Companion.NEWS_DETAIL_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private val viewModel: NewsListViewModel by viewModels()

    private lateinit var _binding: FragmentNewsListBinding
    private val binding: FragmentNewsListBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val isTablet = requireContext().resources.getBoolean(R.bool.isTablet)

        val callback: NewsAdapter.Callback = object : NewsAdapter.Callback {
            override fun clickedNews(news: News) {
                if (isTablet) {
                    val bundle = bundleOf(NEWS_DETAIL_KEY to news.descriptionUrl)
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        add<NewsDetailFragment>(R.id.fragment_detail_container, args = bundle)
                    }
                } else {
                    findNavController().navigate(
                        NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(
                            news.descriptionUrl
                        )
                    )
                }
            }
        }

        val newsAdapter = NewsAdapter(callback)

        binding.newsRecycler.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
        }

        viewModel.news.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshNewsList()
        }

        viewModel.swipeRefreshing.observe(viewLifecycleOwner) {
            if (!it) {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                binding.swipeRefresh.isRefreshing = true
                viewModel.refreshNewsList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}