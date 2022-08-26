package com.example.foodapp.fragment.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.foodapp.Constants
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.databinding.FragmentMealBottomSheetBinding
import com.example.foodapp.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null

    lateinit var homeViewModel: HomeViewModel
    lateinit var binding : FragmentMealBottomSheetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel= (activity as MainActivity).homeViewModel
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMealBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { mealId->
            homeViewModel.getMealById(mealId)
        }

        observeBottomSheetLiveData()
        onBottomSheetClickListener()

    }

    private fun onBottomSheetClickListener() {
        binding.bottomSheet.setOnClickListener {
            if (mealName!=null &&mealThumb!=null){
                val intent= Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(Constants.MEAL_ID,mealId)
                    putExtra(Constants.MEAL_NAME,mealName)
                    putExtra(Constants.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName:String?=null
    private var mealThumb:String?=null

    private fun observeBottomSheetLiveData() {
        homeViewModel.observeBottomSheetMealLiveData().observe(viewLifecycleOwner) {meal->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.bottomsheetImage)
            binding.bottomsheetArea.text=meal.strArea
            binding.bottomsheetCategory.text=meal.strCategory
            binding.bottomsheetMealName.text=meal.strMeal

            mealName= meal.strMeal
            mealThumb=meal.strMealThumb
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}