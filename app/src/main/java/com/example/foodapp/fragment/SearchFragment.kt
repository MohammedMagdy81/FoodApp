package com.example.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.MealsAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding
    lateinit var homeViewModel:HomeViewModel
    private lateinit var searchAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel= (activity as MainActivity).homeViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()

        observeSearchQueryLiveData()

        var searchJob:Job?=null
        binding.edSearchBox.addTextChangedListener {
            searchJob?.cancel()
            searchJob= lifecycleScope.launch {
                delay(500)
                homeViewModel.searchMeals(it.toString())
            }
        }
    }

    private fun observeSearchQueryLiveData() {
        homeViewModel.observeSearchMealsLiveData().observe(viewLifecycleOwner) {
            searchAdapter.differ.submitList(it)
        }
    }

    private fun prepareRecyclerView() {
        searchAdapter= MealsAdapter()
        binding.searchMealsRv.apply {
            layoutManager=GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
            adapter=searchAdapter
        }
    }

}