package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.Constants
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.adapter.CategoriesAdapter
import com.example.foodapp.adapter.PopularMealAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeBinding:FragmentHomeBinding
    private lateinit var homeViewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    lateinit var popularMealAdapter:PopularMealAdapter
    lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel= (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding= FragmentHomeBinding.inflate(inflater,container,false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        prepareCategoriesRecyclerView()

        initClickListener()



        homeViewModel.getRandomMeal()
        observeRandomViewModel()

        homeViewModel.getPopularItem("Seafood")
        observePopularItem()

        homeViewModel.getCategories()
        observeCategories()

    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter= CategoriesAdapter()
        homeBinding.recViewCategoryItem.apply {
            layoutManager=GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun observeCategories() {
        homeViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategories(categories as ArrayList<Category>)
        }
    }

    private fun observePopularItem() {
        homeViewModel.observePopularItemLiveData().observe(viewLifecycleOwner) {
            popularMealAdapter.setMealsList(it as ArrayList<MealsByCategory>)
        }
    }

    private fun prepareRecyclerView() {
        popularMealAdapter= PopularMealAdapter()
        homeBinding.recViewPopularItem.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularMealAdapter
        }
    }

    private fun initClickListener() {
        homeBinding.cardRandomMeal.setOnClickListener {
            val intent= Intent(activity, MealActivity::class.java)
            sendDataToMealActivity(intent)
        }
        popularMealAdapter.onItemClick={meal->
            val intent=Intent(activity, MealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID,meal.idMeal)
            intent.putExtra(Constants.MEAL_NAME,meal.strMeal)
            intent.putExtra(Constants.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }

        categoriesAdapter.onItemClick={category->
            val intent= Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(Constants.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun sendDataToMealActivity(intent: Intent) {
        intent.putExtra(Constants.MEAL_ID,randomMeal.idMeal)
        intent.putExtra(Constants.MEAL_NAME,randomMeal.strMeal)
        intent.putExtra(Constants.MEAL_THUMB,randomMeal.strMealThumb)
        startActivity(intent)
    }

    private fun observeRandomViewModel() {
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner) { randomMeal ->
            Glide.with(this@HomeFragment)
                .load(randomMeal.strMealThumb)
                .into(homeBinding.imageRandomMeal)

            this.randomMeal=randomMeal

        }
    }

}