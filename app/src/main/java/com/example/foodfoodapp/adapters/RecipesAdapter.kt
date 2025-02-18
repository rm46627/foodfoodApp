package com.example.foodfoodapp.adapters

import android.R
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfoodapp.databinding.RecipesRowLayoutBinding
import com.example.foodfoodapp.model.FoodRecipe
import com.example.foodfoodapp.model.Result
import com.example.foodfoodapp.util.RecipesDiffUtil

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(result: Result){
                binding.result = result
                binding.executePendingBindings()
            }

            companion object {
                fun from(parent: ViewGroup): MyViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                    return MyViewHolder(binding)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        clickAnimationForCard(holder)

        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    private fun clickAnimationForCard(holder: MyViewHolder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val attrs = intArrayOf(R.attr.selectableItemBackgroundBorderless)
            val typedArray = holder.itemView.context.obtainStyledAttributes(attrs)
            val selectableItemBackground = typedArray.getResourceId(0, 0)
            typedArray.recycle()
            holder.itemView.isClickable = true
            holder.itemView.isFocusable = true
            holder.itemView.foreground =
                holder.itemView.context.getDrawable(selectableItemBackground)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}