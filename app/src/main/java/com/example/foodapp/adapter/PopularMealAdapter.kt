package com.example.foodapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemPopularItemBinding
import com.example.foodapp.pojo.MealsByCategory

class PopularMealAdapter:RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>() {

     lateinit var onItemClick:((MealsByCategory)->Unit)
    private var mealsList=ArrayList<MealsByCategory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMealsList(mealsList : ArrayList<MealsByCategory>){
        this.mealsList=mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        val itemBinding:ItemPopularItemBinding=ItemPopularItemBinding.
        inflate(LayoutInflater.
        from(parent.context),parent,false)
        return PopularMealViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
            .into(holder.binding.itemPopularImageView)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    class PopularMealViewHolder( val binding:ItemPopularItemBinding):RecyclerView.ViewHolder(binding.root) {

    }
}