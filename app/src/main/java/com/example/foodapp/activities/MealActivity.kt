package com.example.foodapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.Constants
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel
import com.example.foodapp.viewModel.MealDetailsViewModel

class MealActivity : AppCompatActivity() {
   lateinit var mealID:String
   lateinit var mealName:String
   lateinit var mealThumb:String
   lateinit var mealYoutubeLink:String
   lateinit var meal:Meal

    lateinit var binding:ActivityMealBinding
    lateinit var mealDetailsViewModel: MealDetailsViewModel
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeViewModel=ViewModelProvider(this)[HomeViewModel::class.java]

        mealDetailsViewModel= ViewModelProvider(this)[MealDetailsViewModel::class.java]

        getMealInformation()
        setInformationToMealActivity()
        loadCase()
        mealDetailsViewModel.getMealDetailsById(mealID)
        observeToMealDetails()
        initViewClickListener()


    }

    private fun initViewClickListener() {
        // this fun to open youtube link of meal
        binding.activityMealImageYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW,Uri.parse(mealYoutubeLink))
            startActivity(intent)
        }
        binding.activityMealFab.setOnClickListener {
            homeViewModel.insertMeal(meal ,applicationContext)
            Toast.makeText(this, "This Meal Saved Successfully ", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeToMealDetails() {
        mealDetailsViewModel.getMealDetailsLiveData().observe(this) {meal->
            onResponseCase()
            this.meal=meal
            mealYoutubeLink=meal.strYoutube!!
            binding.activityMealTvCategory.text="Category: ${meal.strCategory}"
            binding.activityMealTvArea.text="Area: ${meal.strArea}"
            binding.activityMealTvDetails.text= meal.strInstructions
        }
    }

    private fun setInformationToMealActivity() {
        binding.collapsingtoolbar.title= mealName
        Glide.with(this)
            .load(mealThumb)
            .into(binding.activityMealImageDetails)

    }

    private fun getMealInformation() {
        val intent = intent
        mealID = intent.getStringExtra(Constants.MEAL_ID).toString()
        mealName = intent.getStringExtra(Constants.MEAL_NAME).toString()
        mealThumb = intent.getStringExtra(Constants.MEAL_THUMB).toString()
    }

   fun loadCase(){
        binding.activityMealProgressBar.visibility=View.VISIBLE
        binding.activityMealImageYoutube.visibility=View.INVISIBLE
        binding.activityMealTvInstructions.visibility=View.INVISIBLE
        binding.activityMealFab.visibility= View.INVISIBLE
        binding.activityMealTvCategory.visibility=View.INVISIBLE
        binding.activityMealTvArea.visibility=View.INVISIBLE
    }

    fun onResponseCase(){
        binding.activityMealProgressBar.visibility=View.INVISIBLE
        binding.activityMealImageYoutube.visibility=View.VISIBLE
        binding.activityMealTvInstructions.visibility=View.VISIBLE
        binding.activityMealFab.visibility= View.VISIBLE
        binding.activityMealTvCategory.visibility=View.VISIBLE
        binding.activityMealTvArea.visibility=View.VISIBLE
    }
}