package com.example.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.pojo.Meal

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal:Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT*FROM Meals")
    fun getAllMeals():LiveData<List<Meal>>
}