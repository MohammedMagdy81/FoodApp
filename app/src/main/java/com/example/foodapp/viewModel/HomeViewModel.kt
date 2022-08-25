package com.example.foodapp.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.foodapp.db.MealsDatabase
import com.example.foodapp.pojo.*
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularItemLiveData=MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData= MutableLiveData<List<Category>>()
    private val favoriteMealsLiveData= MealsDatabase.getInstance(getApplication()).mealDao().getAllMeals()

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

    fun observeFavoriteMealsLiveData():LiveData<List<Meal>>{
        return favoriteMealsLiveData
    }

    fun deleteMeal(meal:Meal, context: Context){
        viewModelScope.launch {
            MealsDatabase.getInstance(context).mealDao().deleteMeal(meal)
        }
    }
    fun insertMeal(meal:Meal, context: Context){
        viewModelScope.launch {
            MealsDatabase.getInstance(context).mealDao().upsertMeal(meal)
        }
    }

}