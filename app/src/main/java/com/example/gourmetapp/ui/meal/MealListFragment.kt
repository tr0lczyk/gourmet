package com.example.gourmetapp.ui.meal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gourmetapp.R
import com.example.gourmetapp.databinding.FragmentMealListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealListFragment : Fragment(R.layout.fragment_meal_list) {

  private val viewModel: MealListViewModel by viewModels()
  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    val binding = FragmentMealListBinding.bind(view)
    viewModel.meals.observe(viewLifecycleOwner){
      binding.mainText.text = it[0].description
    }
  }
}