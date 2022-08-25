package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.*
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularItemLiveData=MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData= MutableLiveData<List<Category>>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful){
                    randomMealLiveData.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("FAILURE", "onFailure: ${t.localizedMessage} ")
            }

        })

    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun getPopularItem(category:String){
        RetrofitInstance.api.getPopularItems(category)
            .enqueue(object :Callback<MealsByCategoryList>{
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    popularItemLiveData.value= response.body()?.meals
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun observePopularItemLiveData():LiveData<List<MealsByCategory>>{
        return popularItemLiveData
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               if (response.isSuccessful){
                  categoriesLiveData.value = response.body()!!.categories
               }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("onFailure", "onFailure: ${t.localizedMessage}")
            }
        })
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>>{
        return categoriesLiveData
    }
}