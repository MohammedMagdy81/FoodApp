package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsViewModel:ViewModel() {
    private val mealDetailsLiveData=MutableLiveData<Meal>()

    fun getMealDetailsById(id:String){
        RetrofitInstance.api.getMealDetailsById(id)
            .enqueue(object :Callback<MealList>{
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response.isSuccessful){
                        mealDetailsLiveData.value= response.body()!!.meals[0]
                    }
                    else
                        return
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("onFailure", " ${t.localizedMessage}")
                }
            })
    }

    fun getMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }
}