package com.example.gourmetapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gourmetapp.R
import com.example.gourmetapp.data.News
import com.example.gourmetapp.databinding.ItemNewsListBinding

class NewsAdapter(private val callback: Callback) : ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    interface Callback {
        fun clickedNews(news: News)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding,newsViewHolderListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    private val newsViewHolderListener : NewsViewHolder.NewsViewHolderListener
    get() = object :NewsViewHolder.NewsViewHolderListener{
        override fun clickedNews(news: News) {
            callback.clickedNews(news)
        }

    }

    class NewsViewHolder(private val binding: ItemNewsListBinding,private val listener: NewsViewHolderListener) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentNews: News

        interface NewsViewHolderListener{
            fun clickedNews(news: News)
        }

        fun bindTo(news: News) {
            currentNews = news
            binding.parentView.setOnClickListener { onClick() }
            binding.newsDate.text = news.getModificationDateFormatted()
            binding.newsTitle.text = news.title
            binding.newsDescription.text = news.descriptionOnly
            Glide.with(binding.newsImage)
                    .load(news.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.newsImage)
        }

        private fun onClick() {
            listener.clickedNews(currentNews)
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News) =
                oldItem.orderId == newItem.orderId

        override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
    }
}