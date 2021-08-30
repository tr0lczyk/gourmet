package com.example.gourmetapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gourmetapp.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private val viewModel: NewsDetailViewModel by viewModels()

    private lateinit var _binding: FragmentNewsDetailBinding
    private val binding: FragmentNewsDetailBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsDetail.observe(viewLifecycleOwner) {
            if (it != null) {
                setupWebView(it)
            }
        }
    }

    private fun setupWebView(url: String) {
        binding.newsWeb.apply {

            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                domStorageEnabled = true
                setSupportMultipleWindows(true)
            }

            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    requireActivity().window.setTitle(title)
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    binding.newsWebIndicator.progress = newProgress
                    if (newProgress == 100) {
                        binding.newsWebIndicator.visibility = View.GONE
                    }
                }
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return false
                }
            }
        }
        binding.newsWeb.loadUrl(url)
    }
}