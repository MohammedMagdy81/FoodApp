package com.example.foodapp.db

import android.content.Context
import androidx.room.*
import com.example.foodapp.Constants
import com.example.foodapp.pojo.Meal


@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealsTypeConverter::class)
abstract class MealsDatabase: RoomDatabase() {

    abstract fun mealDao():MealsDao

    companion object{
        // volatile word means that any change in this instance from any thread will be visible to another threads
        @Volatile
        var instance:MealsDatabase?=null

        // Synchronized means that only one thread can have instance of this room database
        @Synchronized
        fun getInstance(context:Context):MealsDatabase{
            if (instance==null){
                instance=Room.databaseBuilder(context,MealsDatabase::class.java,Constants.DATABASE_NAME)
                        // this function specify what you want to do when you change version of DB and that means
                        // i want rebuild DB but i want to keep data inside database
                .fallbackToDestructiveMigration().
                build()
            }
            return instance!!
        }
    }
}