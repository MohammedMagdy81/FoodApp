package com.example.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.MealsAdapter
import com.example.foodapp.databinding.FragmentFavoritesBinding
import com.example.foodapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var homeViewModel:HomeViewModel
    private lateinit var favoriteAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteAdapter= MealsAdapter()
        homeViewModel= (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentFavoritesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeFavoriteLiveData()

        // this callback is to make swipe to delete item and show snackbar
        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position= viewHolder.adapterPosition
                homeViewModel.deleteMeal(favoriteAdapter.differ.currentList[position],requireContext())
                Snackbar.make(requireView(),"Meal Deleted !" ,Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        homeViewModel.insertMeal(
                            // TODO this is error in this line
                            favoriteAdapter.differ.currentList[position],
                           requireContext()
                        )
                    }.show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favoriteRecyclerView)
    }


    private fun prepareRecyclerView() {
        binding.favoriteRecyclerView.apply {
            layoutManager= GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
            adapter=favoriteAdapter
        }
    }

    private fun observeFavoriteLiveData() {
        homeViewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner) {
            favoriteAdapter.differ.submitList(it)
        }
    }

}