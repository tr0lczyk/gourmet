package com.example.gourmetapp.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gourmetapp.databinding.FragmentMealDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailFragment : Fragment() {

    private val viewModel: MealDetailViewModel by viewModels()

    private lateinit var _binding: FragmentMealDetailBinding
    private val binding: FragmentMealDetailBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mealDetail.observe(viewLifecycleOwner){
            if(it != null){
                Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
            }
        }
    }
}