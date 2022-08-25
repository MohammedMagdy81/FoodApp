package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.Constants
import com.example.foodapp.R
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.CategoriesAdapter
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.viewModel.HomeViewModel

class CategoriesFragment : Fragment() {
    lateinit var binding:FragmentCategoriesBinding
    lateinit var homeViewModel:HomeViewModel
    lateinit var categoriesAdapter:CategoriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel= (activity as MainActivity).homeViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCategoriesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        homeViewModel.getCategories()
        observeToCategories()
        initClickListener()
    }

    private fun initClickListener() {
        categoriesAdapter.onItemClick= {
            val intent= Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(Constants.CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }

    private fun observeToCategories() {
        homeViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner
        ) {
                categoriesAdapter.setCategories(it as ArrayList<Category>)
        }
    }

    private fun prepareRecyclerView() {
        categoriesAdapter= CategoriesAdapter()
        binding.categoriesRecyclerView.apply {
            layoutManager=GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }

    }

}