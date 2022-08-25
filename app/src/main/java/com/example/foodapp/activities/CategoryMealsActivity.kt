package com.example.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.Constants
import com.example.foodapp.adapter.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding:ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryMealsAdapter:CategoryMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryName=intent.getStringExtra(Constants.CATEGORY_NAME)
        categoryMealsViewModel=ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(categoryName!!)
        prepareCategoryMealsRecyclerView()
        categoryMealsViewModel.observeMealsLiveData().observe(this) {
            binding.categoryMealsTvCount.text=categoryName+" : "+it.size.toString()
            categoryMealsAdapter.setMeals(it as ArrayList<MealsByCategory>)

        }


    }

    private fun prepareCategoryMealsRecyclerView() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.categoryMealsRecyclerview.apply {
            layoutManager=GridLayoutManager(this@CategoryMealsActivity,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }
}