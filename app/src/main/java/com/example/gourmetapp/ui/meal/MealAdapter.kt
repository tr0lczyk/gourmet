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

class MealAdapter(private val callback: Callback) : ListAdapter<Meal, MealAdapter.MealViewHolder>(MealDiffCallback()) {

    interface Callback {
        fun clickedMeal(meal: Meal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding,mealViewHolderListener)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    private val mealViewHolderListener : MealViewHolder.MealViewHolderListener
    get() = object :MealViewHolder.MealViewHolderListener{
        override fun clickedMeal(meal: Meal) {
            callback.clickedMeal(meal)
        }

    }

    class MealViewHolder(private val binding: ItemMealListBinding,private val listener: MealViewHolderListener) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentMeal: Meal

        interface MealViewHolderListener{
            fun clickedMeal(meal: Meal)
        }

        fun bindTo(meal: Meal) {
            currentMeal = meal
            binding.parentView.setOnClickListener { onClick() }
            binding.mealDate.text = meal.getModificationDateFormatted()
            binding.mealTitle.text = meal.title
            binding.mealDescription.text = meal.descriptionOnly
            Glide.with(binding.mealImage)
                    .load(meal.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.mealImage)
        }

        private fun onClick() {
            listener.clickedMeal(currentMeal)
        }
    }

    class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
                oldItem.orderId == newItem.orderId

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal) = oldItem == newItem
    }
}