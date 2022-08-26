package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemMealsBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealsByCategory

class MealsAdapter :RecyclerView.Adapter<MealsAdapter.FavoriteMealsViewHolder>(){

    lateinit var onItemClick:((Meal)->Unit)

    var diffUtil= object :DiffUtil.ItemCallback<Meal>(){

        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }
    }


    var differ=AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealsViewHolder {
        val itemBinding=ItemMealsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteMealsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteMealsViewHolder, position: Int) {
        val meal= differ.currentList[position]
        holder.binding.itemMealsText.text=meal.strMeal
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.itemMealsImage)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class FavoriteMealsViewHolder(val binding:ItemMealsBinding):RecyclerView.ViewHolder(binding.root){

    }
}