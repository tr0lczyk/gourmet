package com.example.gourmetapp.ui.meal

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gourmetapp.R
import com.example.gourmetapp.data.Meal
import com.example.gourmetapp.databinding.FragmentMealListBinding
import com.example.gourmetapp.ui.meal.MealDetailViewModel.Companion.MEAL_DETAIL_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealListFragment : Fragment() {

    private val viewModel: MealListViewModel by viewModels()

    private lateinit var _binding: FragmentMealListBinding
    private val binding: FragmentMealListBinding get() = _binding

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

        val isTablet = requireContext().resources.getBoolean(R.bool.isTablet)

        val callback: MealAdapter.Callback = object : MealAdapter.Callback {
            override fun clickedMeal(meal: Meal) {
                if (isTablet) {
                    val bundle = bundleOf(MEAL_DETAIL_KEY to meal.descriptionUrl)
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        add<MealDetailFragment>(R.id.fragment_detail_container, args = bundle)
                    }
                } else {
                    findNavController().navigate(
                        MealListFragmentDirections.actionMealListFragmentToMealDetailFragment(
                            meal.descriptionUrl
                        )
                    )
                }
            }
        }

        val mealAdapter = MealAdapter(callback)

        binding.mealRecycler.apply {
            adapter = mealAdapter
            setHasFixedSize(true)
        }

        viewModel.meals.observe(viewLifecycleOwner) {
            mealAdapter.submitList(it)
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