package com.example.foodapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemCategoryItemBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryList

class CategoriesAdapter:RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categoriesList= ArrayList<Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun  setCategories(categoriesList : ArrayList<Category>){
        this.categoriesList=categoriesList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemBinding= ItemCategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoriesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    class CategoriesViewHolder(val binding :ItemCategoryItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.itemCategoryTextview.text=category.strCategory
            Glide.with(binding.root)
                .load(category.strCategoryThumb)
                .into(binding.itemCategoryImage)
        }

    }
}