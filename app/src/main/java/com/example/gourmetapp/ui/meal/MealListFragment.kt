package com.example.gourmetapp.ui.meal

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gourmetapp.R
import com.example.gourmetapp.data.Meal
import com.example.gourmetapp.databinding.FragmentMealListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealListFragment : Fragment() {

    private val viewModel: MealListViewModel by viewModels()

    private lateinit var _binding: FragmentMealListBinding
    private val binding: FragmentMealListBinding get() = _binding

    private val callback : MealAdapter.Callback = object: MealAdapter.Callback{
        override fun clickedMeal(meal: Meal) {
            findNavController().navigate(MealListFragmentDirections.actionMealListFragmentToMealDetailFragment(meal.descriptionUrl))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MealAdapter(callback)
        binding.mealRecycler.apply {
            this.adapter = adapter
            setHasFixedSize(true)
        }
        viewModel.meals.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshMealList()
        }
        viewModel.swipeRefreshing.observe(viewLifecycleOwner) {
            if (!it) {
                binding.swipeRefresh.isRefreshing = false
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.meal_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                binding.swipeRefresh.isRefreshing = true
                viewModel.refreshMealList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}