package com.example.gourmetapp.ui.meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gourmetapp.R
import com.example.gourmetapp.data.Meal
import com.example.gourmetapp.databinding.ItemMealListBinding

class MealAdapter : ListAdapter<Meal, MealAdapter.MealViewHolder>(MealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class MealViewHolder(private val binding: ItemMealListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(meal: Meal) {
            binding.mealDate.text = meal.getModificationDateFormatted()
            binding.mealTitle.text = meal.title
            binding.mealDescription.text = meal.descriptionOnly
            Glide.with(binding.mealImage)
                    .load(meal.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.mealImage)
        }
    }

    class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
                oldItem.orderId == newItem.orderId

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal) = oldItem == newItem
    }
}