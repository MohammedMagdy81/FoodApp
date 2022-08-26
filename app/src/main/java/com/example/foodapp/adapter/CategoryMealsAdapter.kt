package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemMealsBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealsByCategory

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealsList= ArrayList<MealsByCategory>()
   lateinit var onItemClick:((MealsByCategory)->Unit)

    fun setMeals(mealsList :  ArrayList<MealsByCategory>){
        this.mealsList=mealsList
        notifyDataSetChanged()
    }

    class CategoryMealsViewHolder(val binding :ItemMealsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(mealsByCategory: MealsByCategory) {
             binding.itemMealsText.text=mealsByCategory.strMeal
             Glide.with(binding.root)
                .load(mealsByCategory.strMealThumb)
                .into(binding.itemMealsImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        val itemBinding=ItemMealsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryMealsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        holder.bind(mealsList[position])
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
      return mealsList.size
    }
}